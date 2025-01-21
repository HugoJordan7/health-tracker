package com.example.healthtracker.feature.imc.view;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.healthtracker.App;
import com.example.healthtracker.R;
import com.example.healthtracker.di.DependencyInjector;
import com.example.healthtracker.feature.imc.Imc;
import com.example.healthtracker.feature.imc.data.repository.ImcRepository;
import com.example.healthtracker.feature.imc.presentation.ImcPresenter;
import com.example.healthtracker.feature.listCalc.view.ListCalcActivity;
import com.example.healthtracker.model.CalcDao;

public class ImcActivity extends AppCompatActivity implements Imc.View {

    private Imc.Presenter presenter;

    private EditText editHeight;
    private EditText editWeight;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imc);

        editHeight = findViewById(R.id.imc_height);
        editWeight = findViewById(R.id.imc_weight);
        Button buttonResult = findViewById(R.id.imc_button);

        ImageButton arrowBackButton = findViewById(R.id.arrow_refs_imc);
        arrowBackButton.setOnClickListener(view -> {
                finish();
        });

        ImageButton historyButton = findViewById(R.id.historic_refs_imc);
        historyButton.setOnClickListener(view -> {
            onRegisterImcValue();
        });

        ImcRepository repository = DependencyInjector.getImcRepository();
        presenter = new ImcPresenter(ImcActivity.this, repository);

        App app = (App) getApplication();
        CalcDao dao = app.db.calcDao();

        buttonResult.setOnClickListener(view -> {

            if(!presenter.validate(editHeight.getText().toString(), editWeight.getText().toString())){
                displayFailure(getString(R.string.toast_invalid_info));
                return;
            }

            double height = Double.parseDouble(editHeight.getText().toString());
            double weight = Double.parseDouble(editWeight.getText().toString());
            double imcResult = presenter.calculateImc(height, weight);

            new AlertDialog.Builder(ImcActivity.this)
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
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRegisterImcValue() {
        Intent intent = new Intent(ImcActivity.this, ListCalcActivity.class).putExtra("type","imc");
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.menu_item_search){
            onRegisterImcValue();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
    }
}
