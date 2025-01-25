package com.example.healthtracker.feature.heartRate.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.example.healthtracker.App;
import com.example.healthtracker.R;
import com.example.healthtracker.di.DependencyInjector;
import com.example.healthtracker.feature.calc.view.HeaderActionListener;
import com.example.healthtracker.feature.heartRate.HeartRate;
import com.example.healthtracker.feature.heartRate.data.repository.HeartRateRepository;
import com.example.healthtracker.feature.heartRate.presentation.HeartRatePresenter;
import com.example.healthtracker.feature.listCalc.view.ListCalcActivity;
import com.example.healthtracker.model.CalcDao;

import kotlin.Pair;

public class HeartRateFragment extends Fragment implements HeartRate.View, HeaderActionListener {

    private HeartRate.Presenter presenter;
    private EditText editHeartRate;
    private EditText editAge;
    private RadioButton radioMasculine;
    private Button button;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_heart_rate, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        HeartRateRepository repository = DependencyInjector.getHeartRateRepository();
        presenter = new HeartRatePresenter(this, repository);

        editHeartRate = view.findViewById(R.id.hr_edit_heart_rate);
        editAge = view.findViewById(R.id.hr_age);
        radioMasculine = view.findViewById(R.id.hr_radio_masculine);
        button = view.findViewById(R.id.hr_button);
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
                    .setPositiveButton(R.string.ok, (dialogInterface, i) -> {

                    })
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
    public void displayFailure(String message) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDestroyView() {
        presenter.onDestroy();
        super.onDestroyView();
    }

    @Override
    public void onClickInHistoryButton() {
        onRegisterHeartRate();
    }
}
