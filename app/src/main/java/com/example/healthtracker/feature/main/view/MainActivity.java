package com.example.healthtracker.feature.main.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.healthtracker.R;
import com.example.healthtracker.common.util.OnClickListener;
import com.example.healthtracker.feature.calc.view.CalcActivity;
import com.example.healthtracker.feature.login.view.LoginActivity;
import com.example.healthtracker.feature.medication_routine.view.MedicationRoutineActivity;
import com.example.healthtracker.feature.references.view.ReferencesActivity;
import com.google.android.material.button.MaterialButton;

import java.util.LinkedList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private final List<MainItem> listItems = new LinkedList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageButton infoButton = findViewById(R.id.info_button);
        infoButton.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, ReferencesActivity.class);
            startActivity(intent);
        });

        ImageButton logoutButton = findViewById(R.id.logout_button);
        logoutButton.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        });

        listItems.add(new MainItem(R.string.imc, R.drawable.conditions, () -> {
            navigateToCalcScreen(getString(R.string.imc));
        }));
        listItems.add(new MainItem(R.string.tmb, R.drawable.fire, () -> {
            navigateToCalcScreen(getString(R.string.tmb));
        }));
        listItems.add(new MainItem(R.string.bpm, R.drawable.heart_rate, () -> {
            navigateToCalcScreen(getString(R.string.bpm));
        }));
        listItems.add(new MainItem(R.string.water, R.drawable.water, () -> {
            navigateToCalcScreen(getString(R.string.water));
        }));
        listItems.add(new MainItem(R.string.medications, R.drawable.ic_pill, () -> {
            Intent intent = new Intent(MainActivity.this, MedicationRoutineActivity.class);
            startActivity(intent);
        }));
//        listItems.add(new MainItem(R.string.food_scanner, R.drawable.ic_food, () -> {
//            Toast.makeText(this, "Indo para a tela do scanner de alimentos...", Toast.LENGTH_SHORT).show();
//        }));

        MainAdapter adapter = new MainAdapter(listItems);
        RecyclerView recyclerView = findViewById(R.id.main_rv);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new GridLayoutManager(MainActivity.this, 2));
    }

    private void navigateToCalcScreen(String calcType) {
        Intent intent = new Intent(MainActivity.this, CalcActivity.class);
        intent.putExtra("calcType", calcType);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.menu.main_menu){
            Intent intent = new Intent(MainActivity.this, ReferencesActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}
