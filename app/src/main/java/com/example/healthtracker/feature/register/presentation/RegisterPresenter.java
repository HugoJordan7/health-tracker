package com.example.healthtracker.feature.register.presentation;

import com.example.healthtracker.domain.model.User;
import com.example.healthtracker.feature.register.Register;
import com.example.healthtracker.feature.register.data.repository.RegisterRepository;

public class RegisterPresenter implements Register.Presenter {

    private Register.View view;
    private RegisterRepository repository;

    public RegisterPresenter(Register.View view, RegisterRepository repository) {
        this.view = view;
        this.repository = repository;
    }

    @Override
    public void registerUser(User user) {
        try {
            repository.registerUser(user);
            view.onRegisterSuccess(user);
        } catch (Exception e) {
            view.displayFailure(e.getMessage());
        }
    }

    @Override
    public void onDestroy() {
        view = null;
    }

}
