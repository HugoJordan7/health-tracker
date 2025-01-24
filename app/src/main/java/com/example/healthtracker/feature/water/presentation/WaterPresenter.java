package com.example.healthtracker.feature.water.presentation;

import androidx.annotation.NonNull;

import com.example.healthtracker.feature.water.Water;

public class WaterPresenter implements Water.Presenter {

    private Water.View view;

    public WaterPresenter(Water.View view) {
        this.view = view;
    }

    @Override
    public boolean validate(@NonNull String weight, @NonNull String age) {
        return !weight.isEmpty() && !weight.startsWith("0") && !age.isEmpty() && !age.startsWith("0");
    }

    @Override
    public int calculateIdealQuantityWater(int age, int weight) {
        if (age >= 0 && age <= 17) {
            return 40 * weight;
        } else if (age >= 18 && age <= 55) {
            return 35 * weight;
        } else if (age >= 56 && age <= 65) {
            return 30 * weight;
        } else {
            return 25 * weight;
        }
    }

    @Override
    public int quantityByExercise(String exerciseFrequency, String[] array) {
        if (exerciseFrequency.equals(array[0])) {
            return 0;
        } else if (exerciseFrequency.equals(array[1])) {
            return 325;
        } else if (exerciseFrequency.equals(array[2])) {
            return 750;
        } else if (exerciseFrequency.equals(array[3])) {
            return 1075;
        } else if (exerciseFrequency.equals(array[4])) {
            return 1500;
        } else {
            return 2250;
        }
    }

    @Override
    public void onDestroy() {
        view = null;
    }

}
