package com.example.healthtracker.feature.heartRate.view;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

import com.example.healthtracker.App;
import com.example.healthtracker.R;
import com.example.healthtracker.common.base.BaseFragment;
import com.example.healthtracker.di.DependencyInjector;
import com.example.healthtracker.feature.calc.view.HeaderActionListener;
import com.example.healthtracker.feature.heartRate.HeartRate;
import com.example.healthtracker.feature.heartRate.data.repository.HeartRateRepository;
import com.example.healthtracker.feature.heartRate.presentation.HeartRatePresenter;
import com.example.healthtracker.feature.listCalc.view.ListCalcActivity;
import com.example.healthtracker.model.CalcDao;

import kotlin.Pair;

public class HeartRateFragment extends BaseFragment<HeartRate.Presenter> implements HeartRate.View, HeaderActionListener {

    @Override
    protected int setLayoutId() {
        return R.layout.fragment_heart_rate;
    }

    @Override
    protected HeartRate.Presenter setPresenter() {
        HeartRateRepository repository = DependencyInjector.getHeartRateRepository();
        return new HeartRatePresenter(this, repository);
    }

    @Override
    protected void setViews(@NonNull View view) {
        EditText editHeartRate = view.findViewById(R.id.hr_edit_heart_rate);
        EditText editAge = view.findViewById(R.id.hr_age);
        RadioButton radioMasculine = view.findViewById(R.id.hr_radio_masculine);
        Button button = view.findViewById(R.id.hr_button);
        radioMasculine.setChecked(true);

        App app = (App) requireActivity().getApplication();
        CalcDao dao = app.db.calcDao();

        button.setOnClickListener(v -> {
            if(!presenter.validate(editAge.getText().toString(),editHeartRate.getText().toString())){
                displayFailure(getString(R.string.toast_invalid_info));
                return;
            }

            int age = Integer.parseInt(editAge.getText().toString());
            int bpm = Integer.parseInt(editHeartRate.getText().toString());
            if (age < 18) {
                displayFailure(getString(R.string.toast_bellow_age));
                return;
            }

            Pair<Integer, Pair<Integer, Integer>> heartRateSituation = presenter.getClassificationHeartRate(radioMasculine.isChecked(), bpm, age);
            String hrClassification = getString(heartRateSituation.getFirst());
            int firstHrValueRange = heartRateSituation.getSecond().getFirst();
            int secondHrValueRange = heartRateSituation.getSecond().getSecond();

            String currentHrRange;
            if (secondHrValueRange == 999) {
                currentHrRange = getString(R.string.current_hear_rate_range_bad, firstHrValueRange);
            } else if (firstHrValueRange == 0) {
                currentHrRange = getString(R.string.current_hear_rate_range_bellow_normal, secondHrValueRange + 1);
            } else {
                currentHrRange = getString(R.string.current_hear_rate_range, firstHrValueRange, secondHrValueRange);
            }

            String stringSex = radioMasculine.isChecked() ? getString(R.string.men) : getString(R.string.women);

            new AlertDialog.Builder(requireContext())
                    .setTitle(getString(R.string.dialog_title_bpm, hrClassification))
                    .setMessage(getString(R.string.dialog_message_bpm, hrClassification, stringSex, age, currentHrRange))
                    .setPositiveButton(R.string.ok, (dialogInterface, i) -> {})
                    .setNegativeButton(R.string.save, (dialogInterface, i) -> {
                        presenter.registerHeartRateValue((double) bpm, hrClassification, dao);
                    })
                    .create()
                    .show();
        });
    }

    @Override
    public void onRegisterHeartRate() {
        Intent intent = new Intent(requireActivity(), ListCalcActivity.class).putExtra("type", "bpm");
        startActivity(intent);
    }

    @Override
    public void onClickInHistoryButton() {
        onRegisterHeartRate();
    }
}