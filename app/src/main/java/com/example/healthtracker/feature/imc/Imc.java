package com.example.healthtracker.feature.imc;

import androidx.annotation.StringRes;

import com.example.healthtracker.common.base.BasePresenter;
import com.example.healthtracker.common.base.BaseView;
import com.example.healthtracker.model.CalcDao;

public interface Imc {

    interface View extends BaseView {
        void onRegisterImcValue();
    }

    interface Presenter extends BasePresenter {
        void registerImcValue(double imc, CalcDao dao);
        boolean validate(String height, String weight);
        double calculateImc(double height, double weight);
        @StringRes
        int getImcSituation(double imc);
    }

}
