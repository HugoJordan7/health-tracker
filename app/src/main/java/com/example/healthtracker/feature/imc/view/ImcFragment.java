package com.example.healthtracker.feature.imc.view;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

import com.example.healthtracker.App;
import com.example.healthtracker.R;
import com.example.healthtracker.common.base.BaseFragment;
import com.example.healthtracker.di.DependencyInjector;
import com.example.healthtracker.feature.calc.view.HeaderActionListener;
import com.example.healthtracker.feature.imc.Imc;
import com.example.healthtracker.feature.imc.data.repository.ImcRepository;
import com.example.healthtracker.feature.imc.presentation.ImcPresenter;
import com.example.healthtracker.feature.listCalc.view.ListCalcActivity;
import com.example.healthtracker.model.CalcDao;

public class ImcFragment extends BaseFragment<Imc.Presenter> implements Imc.View, HeaderActionListener {

    @Override
    protected int setLayoutId() {
        return R.layout.fragment_imc;
    }

    @Override
    public Imc.Presenter setPresenter() {
        ImcRepository repository = DependencyInjector.getImcRepository();
        return new ImcPresenter(this, repository);
    }

    @Override
    public void setViews(@NonNull View view) {
        EditText editHeight = view.findViewById(R.id.imc_height);
        EditText editWeight = view.findViewById(R.id.imc_weight);
        Button buttonResult = view.findViewById(R.id.imc_button);

        App app = (App) requireActivity().getApplication();
        CalcDao dao = app.db.calcDao();

        buttonResult.setOnClickListener(v -> {

            if(!presenter.validate(editHeight.getText().toString(), editWeight.getText().toString())){
                displayFailure(getString(R.string.toast_invalid_info));
                return;
            }

            double height = Double.parseDouble(editHeight.getText().toString());
            double weight = Double.parseDouble(editWeight.getText().toString());
            double imcResult = presenter.calculateImc(height, weight);

            new AlertDialog.Builder(requireContext())
                    .setTitle(getString(R.string.dialog_imc_title, imcResult))
                    .setMessage(presenter.getImcSituation(imcResult))
                    .setPositiveButton(R.string.ok, (dialogInterface, i) -> {

                    })
                    .setNegativeButton(R.string.save, (dialogInterface, i) -> {
                        presenter.registerImcValue(imcResult, dao);
                    })
                    .show();
        });
    }


    @Override
    public void onRegisterImcValue() {
        Intent intent = new Intent(requireActivity(), ListCalcActivity.class).putExtra("type","imc");
        startActivity(intent);
    }

    @Override
    public void onClickInHistoryButton() {
        onRegisterImcValue();
    }
}
