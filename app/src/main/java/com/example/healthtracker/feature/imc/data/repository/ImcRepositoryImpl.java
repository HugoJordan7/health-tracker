package com.example.healthtracker.feature.imc.data.repository;

import com.example.healthtracker.common.base.RequestCallback;
import com.example.healthtracker.common.util.Constants;
import com.example.healthtracker.domain.service.HealthTrackerService;
import com.example.healthtracker.feature.imc.data.data_source.ImcDataSource;
import com.example.healthtracker.model.Calc;
import com.example.healthtracker.model.CalcDao;

public class ImcRepositoryImpl implements ImcRepository {

    private final HealthTrackerService healthTrackerService;

    public ImcRepositoryImpl(HealthTrackerService healthTrackerService){
        this.healthTrackerService = healthTrackerService;
    }

    @Override
    public void registerImcValue(double imc, String situation, RequestCallback<Boolean> callback) {
        Calc calc = new Calc(Constants.IMC, imc, situation);
        healthTrackerService.insertCalc(calc);
        callback.onSuccess(true);
    }
}
