package com.example.healthtracker.feature.listCalc.view;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.healthtracker.R;
import com.example.healthtracker.di.DependencyInjector;
import com.example.healthtracker.feature.listCalc.ListCalc;
import com.example.healthtracker.feature.listCalc.data.repository.ListCalcRepository;
import com.example.healthtracker.feature.listCalc.presentation.ListCalcPresenter;
import com.example.healthtracker.model.Calc;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

public class ListCalcActivity extends AppCompatActivity implements ListCalc.View {

    private ListCalc.Presenter presenter;
    private ListCalcAdapter adapter;
    private String type;
    private LineChart lineChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_calc);

        type = getIntent().getExtras() != null ? getIntent().getExtras().getString("type") : null;

        if (type == null) {
            throw new RuntimeException("The type is not specified");
        }

        lineChart = findViewById(R.id.chart_list_calc);
        adapter = new ListCalcAdapter(this);
        RecyclerView rvListCalc = findViewById(R.id.rv_list_calc);
        rvListCalc.setAdapter(adapter);
        rvListCalc.setLayoutManager(new LinearLayoutManager(this));

        ImageButton arrowBackButton = findViewById(R.id.arrow_refs_history);
        arrowBackButton.setOnClickListener(view -> finish());

        ImageButton clearHistoryButton = findViewById(R.id.delete_history_button);
        clearHistoryButton.setOnClickListener(view -> {
            new AlertDialog.Builder(this)
                    .setTitle(R.string.dialog_title_delete_history)
                    .setPositiveButton(R.string.yes, (dialog, which) -> {
                        if (!adapter.list.isEmpty()){
                            presenter.clearRegisters(type);
                        }
                    })
                    .setNegativeButton(R.string.back, null)
                    .create()
                    .show();
        });

        ListCalcRepository repository = DependencyInjector.getListCalcRepository();
        presenter = new ListCalcPresenter(this, repository);

        presenter.getAllRegisters(type);
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void displayAllRegisters(List<Calc> list) {
        adapter.list = list;
        adapter.notifyDataSetChanged();
        setupChart(list);
    }

    private void setupChart(List<Calc> list) {
        if (list == null || list.isEmpty()) {
            lineChart.setVisibility(View.GONE);
            return;
        }

        lineChart.setVisibility(View.VISIBLE);
        List<Entry> entries = new ArrayList<>();

        for (int i = 0; i < list.size(); i++) {
            entries.add(new Entry(i, (float) list.get(i).getRes()));
        }

        LineDataSet dataSet = new LineDataSet(entries, type);
        dataSet.setColor(Color.RED);
        dataSet.setValueTextColor(Color.BLACK);
        dataSet.setCircleColor(Color.RED);
        dataSet.setLineWidth(2f);
        dataSet.setCircleRadius(4f);
        dataSet.setDrawCircleHole(false);
        dataSet.setValueTextSize(10f);
        dataSet.setDrawFilled(true);
        dataSet.setFillColor(Color.RED);
        dataSet.setFillAlpha(50);

        LineData lineData = new LineData(dataSet);
        lineChart.setData(lineData);

        // Configurações do Eixo X
        XAxis xAxis = lineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setGranularity(1f);
        xAxis.setValueFormatter(new ValueFormatter() {
            private final SimpleDateFormat mFormat = new SimpleDateFormat("dd/MM", Locale.getDefault());

            @Override
            public String getFormattedValue(float value) {
                int index = (int) value;
                if (index >= 0 && index < list.size()) {
                    return mFormat.format(list.get(index).getCreatedDate());
                }
                return "";
            }
        });

        lineChart.getDescription().setEnabled(false);
        lineChart.getLegend().setEnabled(true);
        lineChart.invalidate(); // refresh
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
