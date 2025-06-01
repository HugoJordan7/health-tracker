package com.example.healthtracker.feature.heartRate.data.repository;

import com.example.healthtracker.common.base.RequestCallback;
import com.example.healthtracker.common.util.Constants;
import com.example.healthtracker.domain.service.HealthTrackerService;
import com.example.healthtracker.feature.heartRate.data.data_source.HeartRateDataSource;
import com.example.healthtracker.model.Calc;
import com.example.healthtracker.model.CalcDao;

public class HeartRateRepositoryImpl implements HeartRateRepository {

    private final HealthTrackerService healthTrackerService;

    public HeartRateRepositoryImpl(HealthTrackerService healthTrackerService) {
        this.healthTrackerService = healthTrackerService;
    }

    @Override
    public void registerHeartRateValue(double bpm, String hrClassification, RequestCallback<Boolean> callback) {
        Calc calc = new Calc(Constants.BPM, bpm, hrClassification);
        healthTrackerService.insertCalc(calc);
        callback.onSuccess(true);
    }

}
