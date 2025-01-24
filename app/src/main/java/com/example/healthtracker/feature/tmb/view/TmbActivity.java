package com.example.healthtracker.feature.tmb.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.*;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.example.healthtracker.App;
import com.example.healthtracker.R;
import com.example.healthtracker.di.DependencyInjector;
import com.example.healthtracker.feature.listCalc.view.ListCalcActivity;
import com.example.healthtracker.feature.tmb.Tmb;
import com.example.healthtracker.feature.tmb.data.repository.TmbRepository;
import com.example.healthtracker.feature.tmb.presentation.TmbPresenter;
import com.example.healthtracker.model.CalcDao;

public class TmbActivity extends AppCompatActivity implements Tmb.View {

    private Tmb.Presenter presenter;
    private EditText editHeight;
    private EditText editWeight;
    private EditText editAge;
    private Button buttonResult;
    private AutoCompleteTextView autoLifestyle;
    private RadioButton radioButtonMasculine;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tmb);

        TmbRepository repository = DependencyInjector.getTmbRepository();
        presenter = new TmbPresenter(this, repository);

        editHeight = findViewById(R.id.tmb_height);
        editWeight = findViewById(R.id.tmb_weight);
        editAge = findViewById(R.id.tmb_age);
        buttonResult = findViewById(R.id.tmb_button);
        autoLifestyle = findViewById(R.id.auto_lifestyle);
        radioButtonMasculine = findViewById(R.id.radio_button_masculine_tmb);
        radioButtonMasculine.setChecked(true);

        App app = (App) getApplication();
        CalcDao dao = app.db.calcDao();

        ImageButton arrowBackButton = findViewById(R.id.arrow_refs_tmb);
        arrowBackButton.setOnClickListener(v -> finish());

        ImageButton historyButton = findViewById(R.id.historic_refs_tmb);
        historyButton.setOnClickListener(v -> onRegisterTmbValue());

        String[] items = getResources().getStringArray(R.array.lifestye_tmb);
        autoLifestyle.setText(items[0]);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, items);
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

            new AlertDialog.Builder(this)
                    .setTitle(getString(R.string.dialog_tmb_title, tmbAdapted))
                    .setPositiveButton(R.string.ok, null)
                    .setNegativeButton(R.string.save, (dialog, which) -> presenter.registerTmbValue(tmbAdapted, dao))
                    .create()
                    .show();
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_item_search) {
            onRegisterTmbValue();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onRegisterTmbValue() {
        startActivity(new Intent(this, ListCalcActivity.class).putExtra("type", "tmb"));
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
