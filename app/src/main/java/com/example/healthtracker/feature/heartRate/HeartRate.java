package com.example.healthtracker.feature.heartRate;

import com.example.healthtracker.common.base.BasePresenter;
import com.example.healthtracker.common.base.BaseView;
import com.example.healthtracker.model.CalcDao;

import kotlin.Pair;

public interface HeartRate {

    interface View extends BaseView {
        void onRegisterHeartRate();
    }

    interface Presenter extends BasePresenter {
        void registerHeartRateValue(double bpm, String hrClassification, CalcDao dao);
        boolean validate(String age, String bpm);
        Pair<Integer, Pair<Integer, Integer>> getClassificationHeartRate(boolean isMan, int bpm, int age);
    }

}
