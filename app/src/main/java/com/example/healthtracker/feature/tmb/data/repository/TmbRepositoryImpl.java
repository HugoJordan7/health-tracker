package com.example.healthtracker.feature.tmb.data.repository;

import com.example.healthtracker.common.base.RequestCallback;
import com.example.healthtracker.common.util.Constants;
import com.example.healthtracker.domain.service.HealthTrackerService;
import com.example.healthtracker.feature.tmb.data.data_source.TmbDataSource;
import com.example.healthtracker.model.Calc;
import com.example.healthtracker.model.CalcDao;

public class TmbRepositoryImpl implements TmbRepository {

    private final HealthTrackerService healthTrackerService;

    public TmbRepositoryImpl(HealthTrackerService healthTrackerService) {
        this.healthTrackerService = healthTrackerService;
    }

    @Override
    public void registerTmbValue(double tmb, RequestCallback<Boolean> callback) {
        try {
            Calc calc = new Calc(Constants.TMB, tmb, null);
            healthTrackerService.insertCalc(calc);
            callback.onSuccess(true);
        } catch (Exception e) {
            callback.onFailure(e.getMessage());
        }
    }
}
