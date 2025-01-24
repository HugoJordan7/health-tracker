package com.example.healthtracker.feature.tmb.presentation;

import android.os.Handler;
import android.os.Looper;

import androidx.annotation.NonNull;

import com.example.healthtracker.common.base.RequestCallback;
import com.example.healthtracker.feature.tmb.Tmb;
import com.example.healthtracker.feature.tmb.data.repository.TmbRepository;
import com.example.healthtracker.model.CalcDao;

public class TmbPresenter implements Tmb.Presenter {

    private Tmb.View view;
    private TmbRepository repository;

    public TmbPresenter(Tmb.View view, TmbRepository repository) {
        this.view = view;
        this.repository = repository;
    }

    @Override
    public void registerTmbValue(double tmb, CalcDao dao) {
        repository.registerTmbValue(tmb, dao, new RequestCallback<Boolean>() {
            @Override
            public void onSuccess(Boolean data) {
                view.onRegisterTmbValue();
            }
            @Override
            public void onFailure(String message) {
                view.displayFailure(message != null ? message : "Unknown error");
            }
        });
    }

    private void runOnMainTread(Runnable action){
        new Handler(Looper.getMainLooper()).post(action);
    }

    @Override
    public boolean validate(@NonNull String height, @NonNull String weight, @NonNull String age) {
        return (!height.isEmpty() && !height.startsWith("0") &&
                !weight.isEmpty() && !weight.startsWith("0") &&
                !age.isEmpty() && !age.startsWith("0"));
    }

    @Override
    public double calculateTmb(boolean isMan, int height, int weight, int age) {
        if (isMan) {
            return 66 + (13.7 * weight) + (5 * height) - (6.8 * age);
        } else {
            return 655 + (9.6 * weight) + (1.8 * height) - (4.7 * age);
        }
    }

    @Override
    public double tmbAdaptedForLifestyle(String currentLifeStyle, double tmb, String[] arrayLifestyle) {
        if (currentLifeStyle.equals(arrayLifestyle[0])) {
            return tmb * 1.2;
        } else if (currentLifeStyle.equals(arrayLifestyle[1])) {
            return tmb * 1.375;
        } else if (currentLifeStyle.equals(arrayLifestyle[2])) {
            return tmb * 1.55;
        } else if (currentLifeStyle.equals(arrayLifestyle[3])) {
            return tmb * 1.725;
        } else if (currentLifeStyle.equals(arrayLifestyle[4])) {
            return tmb * 1.9;
        } else {
            return 0.0;
        }
    }

    @Override
    public void onDestroy() {
        view = null;
    }
}
