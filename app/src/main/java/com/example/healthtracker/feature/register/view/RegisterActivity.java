package com.example.healthtracker.feature.register.view;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.healthtracker.R;
import com.example.healthtracker.common.util.TextWatcher;
import com.example.healthtracker.databinding.ActivityRegisterBinding;
import com.example.healthtracker.di.DependencyInjector;
import com.example.healthtracker.domain.exception.EmailAlreadyExistException;
import com.example.healthtracker.domain.model.User;
import com.example.healthtracker.feature.login.view.LoginActivity;
import com.example.healthtracker.feature.main.view.MainActivity;
import com.example.healthtracker.feature.register.Register;
import com.example.healthtracker.feature.register.presentation.RegisterPresenter;
import com.google.android.material.textfield.TextInputEditText;

public class RegisterActivity extends AppCompatActivity implements Register.View {

    private ActivityRegisterBinding binding;

    private Register.Presenter presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        presenter = new RegisterPresenter(this, DependencyInjector.getRegisterRepository());

        cleanErrorsOfEditText(binding.registerNameEditText);
        cleanErrorsOfEditText(binding.registerEmailEditText);
        cleanErrorsOfEditText(binding.registerPasswordEditText);
        cleanErrorsOfEditText(binding.registerConfirmPasswordEditText);

        binding.registerButton.setActivated(false);
        binding.registerButton.setOnClickListener(view -> {
            checkIfTextIsEmpty(binding.registerNameEditText);
            checkIfTextIsEmpty(binding.registerEmailEditText);
            checkIfTextIsEmpty(binding.registerPasswordEditText);
            checkIfTextIsEmpty(binding.registerConfirmPasswordEditText);

            checkLengthOfPassword();
            checkIfPasswordIsEqualsOnConfirm();

            if (!existErrorsInFields()) {
                String name = binding.registerNameEditText.getText().toString();
                String email = binding.registerEmailEditText.getText().toString();
                String password = binding.registerPasswordEditText.getText().toString();
                User user = new User(name, email, password);
                presenter.registerUser(user);
            }
        });

        binding.registerGoToLogin.setOnClickListener(view -> {
            Intent intent = new Intent(this, LoginActivity.class);
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
        Editable password = binding.registerPasswordEditText.getText();
        if(password == null || password.toString().length() < 8) {
            binding.registerPasswordEditText.setError(getString(R.string.password_very_short));
        }
    }

    private void checkIfPasswordIsEqualsOnConfirm() {
        Editable password = binding.registerPasswordEditText.getText();
        Editable confirmPassword = binding.registerConfirmPasswordEditText.getText();
        if(password == null || confirmPassword == null || !password.toString().equals(confirmPassword.toString())) {
            binding.registerConfirmPasswordEditText.setError(getString(R.string.confirm_password_not_equals));
        }
    }

    private void checkIfTextIsEmpty(TextInputEditText text) {
        boolean textIsEmpty = (text.getText() == null || text.getText().toString().isBlank());
        if (textIsEmpty) text.setError(getString(R.string.empty_field));
    }

    private boolean existErrorsInFields() {
        return binding.registerNameEditText.getError() != null ||
                binding.registerEmailEditText.getError() != null ||
                binding.registerPasswordEditText.getError() != null ||
                binding.registerConfirmPasswordEditText.getError() != null;
    }

    @Override
    public void onRegisterSuccess(User user) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        Toast.makeText(this, "Usuário registrado com sucesso", Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public void displayFailure(String message) {
        if (message.equals(EmailAlreadyExistException.message)) {
            binding.registerEmailEditText.setError(message);
        } else {
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        }
    }

}