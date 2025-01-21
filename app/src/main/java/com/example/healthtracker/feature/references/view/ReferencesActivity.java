package com.example.healthtracker.feature.references.view;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.healthtracker.R;

public class ReferencesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_references);

        ImageButton arrowBack = findViewById(R.id.arrow_refs);
        arrowBack.setOnClickListener(view -> {
            finish();
        });
    }
}
