package com.example.healthtracker.feature.login.view;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.healthtracker.R;
import com.example.healthtracker.common.util.TextWatcher;
import com.example.healthtracker.databinding.ActivityLoginBinding;
import com.example.healthtracker.feature.register.view.RegisterActivity;
import com.google.android.material.textfield.TextInputEditText;

public class LoginActivity extends AppCompatActivity{

    private ActivityLoginBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        cleanErrorsOfEditText(binding.loginEmailEditText);
        cleanErrorsOfEditText(binding.loginPasswordEditText);

        binding.loginButton.setActivated(false);
        binding.loginButton.setOnClickListener(view -> {
            checkIfTextIsEmpty(binding.loginEmailEditText);
            checkIfTextIsEmpty(binding.loginPasswordEditText);

            checkLengthOfPassword();

            if (!existErrorsInFields()) {
                Toast.makeText(this, "Fazendo chamada API", Toast.LENGTH_SHORT).show();
            }
        });

        binding.loginGoToRegister.setOnClickListener(view -> {
            Intent intent = new Intent(this, RegisterActivity.class);
            startActivity(intent);
            finish();
        });
    }

    private void cleanErrorsOfEditText(TextInputEditText editText) {
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                editText.setError(null);
            }
        });
    }

    private void checkLengthOfPassword() {
        Editable password = binding.loginPasswordEditText.getText();
        if(password == null || password.toString().length() < 8) {
            binding.loginPasswordEditText.setError(getString(R.string.password_very_short));
        }
    }

    private void checkIfTextIsEmpty(TextInputEditText text) {
        boolean textIsEmpty = (text.getText() == null || text.getText().toString().isBlank());
        if (textIsEmpty) text.setError(getString(R.string.empty_field));
    }

    private boolean existErrorsInFields() {
        return binding.loginEmailEditText.getError() != null || binding.loginPasswordEditText.getError() != null;
    }

}