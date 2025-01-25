package com.example.healthtracker.feature.water.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.healthtracker.R;
import com.example.healthtracker.feature.water.Water;
import com.example.healthtracker.feature.water.presentation.WaterPresenter;

public class WaterFragment extends Fragment implements Water.View {

    private Water.Presenter presenter;
    private EditText editWeight;
    private EditText editAge;
    private EditText editQuantity;
    private AutoCompleteTextView autoExercise;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_water, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        presenter = new WaterPresenter(this);

        editWeight = view.findViewById(R.id.water_weight);
        editAge = view.findViewById(R.id.water_age);
        editQuantity = view.findViewById(R.id.water_quantity);
        Button button = view.findViewById(R.id.water_button);

        autoExercise = view.findViewById(R.id.auto_exercise);
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

    @Override
    public void displayFailure(String message) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDestroyView() {
        presenter.onDestroy();
        super.onDestroyView();
    }
}
