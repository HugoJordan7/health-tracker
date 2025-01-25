package com.example.healthtracker.feature.water.view;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.example.healthtracker.R;
import com.example.healthtracker.feature.water.Water;
import com.example.healthtracker.feature.water.presentation.WaterPresenter;

public class WaterActivity extends AppCompatActivity implements Water.View {

    private Water.Presenter presenter;
    private EditText editWeight;
    private EditText editAge;
    private EditText editQuantity;
    private AutoCompleteTextView autoExercise;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_water);

        presenter = new WaterPresenter(this);

        editWeight = findViewById(R.id.water_weight);
        editAge = findViewById(R.id.water_age);
        editQuantity = findViewById(R.id.water_quantity);
        Button button = findViewById(R.id.water_button);

//        ImageButton arrowBackButton = findViewById(R.id.arrow_refs);
//        arrowBackButton.setOnClickListener(v -> finish());

        autoExercise = findViewById(R.id.auto_exercise);
        String[] arrayExercise = getResources().getStringArray(R.array.exercise_frequency);
        autoExercise.setText(arrayExercise[0]);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, arrayExercise);
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

            new AlertDialog.Builder(this)
                    .setTitle(titleType)
                    .setMessage(getString(R.string.dialog_water_message, idealQuantityWater, idealQuantityWaterL))
                    .setPositiveButton(R.string.ok, (dialog, which) -> {})
                    .create()
                    .show();
        });
    }

    @Override
    public void displayFailure(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        presenter.onDestroy();
        super.onDestroy();
    }
}
