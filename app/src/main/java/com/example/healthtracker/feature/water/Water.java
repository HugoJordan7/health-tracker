package com.example.healthtracker.feature.water;

import com.example.healthtracker.common.base.BasePresenter;
import com.example.healthtracker.common.base.BaseView;

public interface Water {

    interface View extends BaseView { }

    interface Presenter extends BasePresenter {
        boolean validate(String weight, String age);
        int calculateIdealQuantityWater(int age, int weight);
        int quantityByExercise(String exerciseFrequency, String[] array);
    }
}
