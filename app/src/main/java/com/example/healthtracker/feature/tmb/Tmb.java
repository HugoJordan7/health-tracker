package com.example.healthtracker.feature.tmb;

import com.example.healthtracker.common.base.BasePresenter;
import com.example.healthtracker.common.base.BaseView;
import com.example.healthtracker.model.CalcDao;

public interface Tmb {

    interface View extends BaseView {
        void onRegisterTmbValue();
    }

    interface Presenter extends BasePresenter {
        void registerTmbValue(double tmb, CalcDao dao);
        boolean validate(String height, String weight, String age);
        double calculateTmb(boolean isMan, int height, int weight, int age);
        double tmbAdaptedForLifestyle(String currentLifeStyle, double tmb, String[] arrayLifestyle);
    }
}
