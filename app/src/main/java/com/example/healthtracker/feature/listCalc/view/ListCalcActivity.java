package com.example.healthtracker.feature.listCalc.view;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.healthtracker.App;
import com.example.healthtracker.R;
import com.example.healthtracker.feature.listCalc.ListCalc;
import com.example.healthtracker.feature.listCalc.presentation.ListCalcPresenter;
import com.example.healthtracker.model.Calc;
import com.example.healthtracker.model.CalcDao;

import java.util.LinkedList;
import java.util.List;

public class ListCalcActivity extends AppCompatActivity implements ListCalc.View {

    private ListCalc.Presenter presenter;
    private ListCalcAdapter adapter;
    private String type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_calc);

        presenter = new ListCalcPresenter(this);
        App app = (App) getApplication();
        CalcDao dao = app.db.calcDao();
        type = getIntent().getExtras() != null ? getIntent().getExtras().getString("type") : null;

        if (type == null) {
            throw new RuntimeException("The type is not specified");
        }

        presenter.getAllRegisters(dao, type);

        ImageButton arrowBackButton = findViewById(R.id.arrow_refs_history);
        arrowBackButton.setOnClickListener(view -> finish());

        ImageButton clearHistoryButton = findViewById(R.id.delete_history_button);
        clearHistoryButton.setOnClickListener(view -> {
            new AlertDialog.Builder(this)
                    .setTitle(R.string.dialog_title_delete_history)
                    .setPositiveButton(R.string.yes, (dialog, which) ->
                            presenter.clearRegisters(adapter.list, dao, type))
                    .setNegativeButton(R.string.back, null)
                    .create()
                    .show();
        });

        adapter = new ListCalcAdapter(this);
        RecyclerView rvListCalc = findViewById(R.id.rv_list_calc);
        rvListCalc.setAdapter(adapter);
        rvListCalc.setLayoutManager(new LinearLayoutManager(this));
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void displayAllRegisters(List<Calc> list) {
        adapter.list = list;
        adapter.notifyDataSetChanged();
    }

    @Override
    public void displayFailure(String message) {
        Toast.makeText(this, R.string.toast_delete_history_error, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onDeleteRegisters() {
        Toast.makeText(this, R.string.toast_delete_history, Toast.LENGTH_LONG).show();
        displayAllRegisters(new LinkedList<Calc>());
        finish();
    }

    @Override
    protected void onDestroy() {
        presenter.onDestroy();
        super.onDestroy();
    }
}
