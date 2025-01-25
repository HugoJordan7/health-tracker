package com.example.healthtracker.feature.imc.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.example.healthtracker.App;
import com.example.healthtracker.R;
import com.example.healthtracker.di.DependencyInjector;
import com.example.healthtracker.feature.calc.view.HeaderActionListener;
import com.example.healthtracker.feature.imc.Imc;
import com.example.healthtracker.feature.imc.data.repository.ImcRepository;
import com.example.healthtracker.feature.imc.presentation.ImcPresenter;
import com.example.healthtracker.feature.listCalc.view.ListCalcActivity;
import com.example.healthtracker.model.CalcDao;

public class ImcFragment extends Fragment implements Imc.View, HeaderActionListener {

    private Imc.Presenter presenter;

    private EditText editHeight;
    private EditText editWeight;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_imc, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        editHeight = view.findViewById(R.id.imc_height);
        editWeight = view.findViewById(R.id.imc_weight);
        Button buttonResult = view.findViewById(R.id.imc_button);

        ImcRepository repository = DependencyInjector.getImcRepository();
        presenter = new ImcPresenter(this, repository);

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
    public void displayFailure(String message) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRegisterImcValue() {
        Intent intent = new Intent(requireActivity(), ListCalcActivity.class).putExtra("type","imc");
        startActivity(intent);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        presenter.onDestroy();
    }

    @Override
    public void onClickInHistoryButton() {
        onRegisterImcValue();
    }
}
