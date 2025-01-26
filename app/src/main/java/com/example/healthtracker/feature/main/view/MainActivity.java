package com.example.healthtracker.feature.main.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.healthtracker.R;
import com.example.healthtracker.feature.calc.view.CalcActivity;
import com.example.healthtracker.feature.references.view.ReferencesActivity;

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

        listItems.add(new MainItem(0, R.string.imc, R.drawable.conditions));
        listItems.add(new MainItem(1, R.string.tmb, R.drawable.fire));
        listItems.add(new MainItem(2, R.string.bpm, R.drawable.heart_rate));
        listItems.add(new MainItem(3, R.string.water, R.drawable.water));

        MainAdapter adapter = new MainAdapter(listItems, id -> {
            String destinationClassTag = getString(R.string.imc);
            if (id == 1) {
                destinationClassTag = getString(R.string.tmb);
            } else if (id == 2) {
                destinationClassTag = getString(R.string.bpm);
            } else if (id == 3) {
                destinationClassTag = getString(R.string.water);
            }
            Intent intent = new Intent(MainActivity.this, CalcActivity.class);
            intent.putExtra("calcType", destinationClassTag);
            startActivity(intent);
        });

        RecyclerView recyclerView = findViewById(R.id.main_rv);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new GridLayoutManager(MainActivity.this, 2));
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
