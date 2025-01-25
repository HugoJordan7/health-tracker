package com.example.healthtracker.feature.tmb.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.healthtracker.App;
import com.example.healthtracker.R;
import com.example.healthtracker.di.DependencyInjector;
import com.example.healthtracker.feature.calc.view.HeaderActionListener;
import com.example.healthtracker.feature.listCalc.view.ListCalcActivity;
import com.example.healthtracker.feature.tmb.Tmb;
import com.example.healthtracker.feature.tmb.data.repository.TmbRepository;
import com.example.healthtracker.feature.tmb.presentation.TmbPresenter;
import com.example.healthtracker.model.CalcDao;

public class TmbFragment extends Fragment implements Tmb.View, HeaderActionListener {

    private Tmb.Presenter presenter;
    private EditText editHeight;
    private EditText editWeight;
    private EditText editAge;
    private Button buttonResult;
    private AutoCompleteTextView autoLifestyle;
    private RadioButton radioButtonMasculine;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_tmb, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TmbRepository repository = DependencyInjector.getTmbRepository();
        presenter = new TmbPresenter(this, repository);

        editHeight = view.findViewById(R.id.tmb_height);
        editWeight = view.findViewById(R.id.tmb_weight);
        editAge = view.findViewById(R.id.tmb_age);
        buttonResult = view.findViewById(R.id.tmb_button);
        autoLifestyle = view.findViewById(R.id.auto_lifestyle);
        radioButtonMasculine = view.findViewById(R.id.radio_button_masculine_tmb);
        radioButtonMasculine.setChecked(true);

        App app = (App) requireActivity().getApplication();
        CalcDao dao = app.db.calcDao();

        String[] items = getResources().getStringArray(R.array.lifestye_tmb);
        autoLifestyle.setText(items[0]);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireActivity(), android.R.layout.simple_list_item_1, items);
        autoLifestyle.setAdapter(adapter);

        buttonResult.setOnClickListener(v -> {
            if (!presenter.validate(editHeight.getText().toString(), editWeight.getText().toString(), editAge.getText().toString())) {
                displayFailure(getString(R.string.toast_invalid_info));
                return;
            }

            int height = Integer.parseInt(editHeight.getText().toString());
            int weight = Integer.parseInt(editWeight.getText().toString());
            int age = Integer.parseInt(editAge.getText().toString());
            double tmb = presenter.calculateTmb(radioButtonMasculine.isChecked(), height, weight, age);
            double tmbAdapted = presenter.tmbAdaptedForLifestyle(autoLifestyle.getText().toString(), tmb, items);

            new AlertDialog.Builder(requireContext())
                    .setTitle(getString(R.string.dialog_tmb_title, tmbAdapted))
                    .setPositiveButton(R.string.ok, null)
                    .setNegativeButton(R.string.save, (dialog, which) -> presenter.registerTmbValue(tmbAdapted, dao))
                    .create()
                    .show();
        });
    }

    @Override
    public void onRegisterTmbValue() {
        startActivity(new Intent(requireActivity(), ListCalcActivity.class).putExtra("type", "tmb"));
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
        onRegisterTmbValue();
    }
}
