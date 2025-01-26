package com.example.healthtracker.feature.water.view;

import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AlertDialog;

import com.example.healthtracker.R;
import com.example.healthtracker.common.base.BaseFragment;
import com.example.healthtracker.feature.water.Water;
import com.example.healthtracker.feature.water.presentation.WaterPresenter;

public class WaterFragment extends BaseFragment<Water.Presenter> implements Water.View {

    public WaterFragment() {
        super(R.layout.activity_water);
    }

    @Override
    public Water.Presenter setPresenter() {
        return new WaterPresenter(this);
    }

    @Override
    public void setViews(@NonNull View view) {
        EditText editWeight = view.findViewById(R.id.water_weight);
        EditText editAge = view.findViewById(R.id.water_age);
        EditText editQuantity = view.findViewById(R.id.water_quantity);
        Button button = view.findViewById(R.id.water_button);

        AutoCompleteTextView autoExercise = view.findViewById(R.id.auto_exercise);
        String[] arrayExercise = getResources().getStringArray(R.array.exercise_frequency);
        autoExercise.setText(arrayExercise[0]);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireActivity(), android.R.layout.simple_list_item_1, arrayExercise);
        autoExercise.setAdapter(adapter);

        button.setOnClickListener(v -> {

            if (!presenter.validate(editWeight.getText().toString(), editAge.getText().toString())) {
                displayFailure(getString(R.string.toast_invalid_info));
                return;
            }

            int weight = Integer.parseInt(editWeight.getText().toString());
            int age = Integer.parseInt(editAge.getText().toString());
            double quantity = Double.parseDouble(editQuantity.getText().toString());
            int idealQuantityWater = presenter.calculateIdealQuantityWater(age, weight) +
                    presenter.quantityByExercise(autoExercise.getText().toString(), arrayExercise);
            double idealQuantityWaterL = idealQuantityWater / 1000.0;

            @StringRes
            int titleType = (quantity > idealQuantityWater) ?
                    R.string.dialog_water_title_above :
                    R.string.dialog_water_title_below;

            new AlertDialog.Builder(requireContext())
                    .setTitle(titleType)
                    .setMessage(getString(R.string.dialog_water_message, idealQuantityWater, idealQuantityWaterL))
                    .setPositiveButton(R.string.ok, (dialog, which) -> {})
                    .create()
                    .show();
        });
    }
}
