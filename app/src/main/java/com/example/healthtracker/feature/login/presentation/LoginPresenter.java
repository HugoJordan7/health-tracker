package com.example.healthtracker.feature.login.presentation;

import com.example.healthtracker.domain.model.User;
import com.example.healthtracker.feature.login.Login;
import com.example.healthtracker.feature.login.data.repository.LoginRepository;

public class LoginPresenter implements Login.Presenter{

    private Login.View view;
    private LoginRepository repository;

    public LoginPresenter(Login.View view, LoginRepository repository) {
        this.view = view;
        this.repository = repository;
    }

    @Override
    public void loginUser(String email, String password) {
        try {
            User user = repository.loginUser(email, password);
            view.onLoginSuccess(user);
        } catch (Exception e){
            view.displayFailure(e.getMessage());
        }
    }

    @Override
    public void onDestroy() {
        view = null;
    }
}
