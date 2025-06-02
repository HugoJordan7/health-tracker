package com.example.healthtracker.feature.login;

import com.example.healthtracker.common.base.BasePresenter;
import com.example.healthtracker.common.base.BaseView;
import com.example.healthtracker.domain.model.User;

public interface Login {
    interface View extends BaseView {
        void onLoginSuccess(User user);
    }

    interface Presenter extends BasePresenter {
        void loginUser(String email, String password);
    }
}
