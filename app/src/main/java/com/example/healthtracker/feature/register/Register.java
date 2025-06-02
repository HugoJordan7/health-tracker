package com.example.healthtracker.feature.register;

import com.example.healthtracker.common.base.BasePresenter;
import com.example.healthtracker.common.base.BaseView;
import com.example.healthtracker.domain.model.User;

public interface Register {

    interface View extends BaseView {
        void onRegisterSuccess(User user);
    }

    interface Presenter extends BasePresenter {
        void registerUser(User user);
    }

}
