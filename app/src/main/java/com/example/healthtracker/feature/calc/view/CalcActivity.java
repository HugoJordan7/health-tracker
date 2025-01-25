package com.example.healthtracker.feature.calc.view;

import android.os.Bundle;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;

import com.example.healthtracker.R;
import com.example.healthtracker.feature.heartRate.view.HeartRateFragment;
import com.example.healthtracker.feature.imc.view.ImcFragment;
import com.example.healthtracker.feature.tmb.view.TmbFragment;

public class CalcActivity extends AppCompatActivity {

    private Fragment currentFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calc);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        String calcType = getIntent().getStringExtra("calcType");
        if (calcType == null) return;

        if (calcType.equals(getString(R.string.imc))) {
            currentFragment = new ImcFragment();
        } else if (calcType.equals(getString(R.string.tmb))) {
            currentFragment = new TmbFragment();
        } else if (calcType.equals(getString(R.string.bpm))) {
            currentFragment = new HeartRateFragment();
        } else if (calcType.equals(getString(R.string.water))) {
            currentFragment = new WaterFragment();
        }

        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_container_calc, currentFragment)
                .commitNow();

        setupHeaderActions();
    }

    private void setupHeaderActions(){
        HeaderActionListener headerActionListener = getHeaderActionListener();

        ImageButton arrowBackButton = findViewById(R.id.arrow_refs_header);
        arrowBackButton.setOnClickListener(v -> {
            finish();
        });

        ImageButton historyButton = findViewById(R.id.historic_refs_header);
        historyButton.setOnClickListener(v -> {
            headerActionListener.onClickInHistoryButton();
        });
    }

    private @NonNull HeaderActionListener getHeaderActionListener(){
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.fragment_container_calc);
        if (fragment instanceof HeaderActionListener) {
            return (HeaderActionListener) fragment;
        }
        throw new IllegalArgumentException("HeaderActionListener not found!");
    }
}