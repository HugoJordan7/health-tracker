package com.example.healthtracker.feature.listCalc.data.repository;

import com.example.healthtracker.common.base.RequestCallback;
import com.example.healthtracker.domain.service.HealthTrackerService;
import com.example.healthtracker.feature.listCalc.data.data_source.ListCalcDataSource;
import com.example.healthtracker.model.Calc;
import com.example.healthtracker.model.CalcDao;

import java.util.List;

public class ListCalcRepositoryImpl implements ListCalcRepository {

    private final HealthTrackerService healthTrackerService;

    public ListCalcRepositoryImpl(HealthTrackerService healthTrackerService) {
        this.healthTrackerService = healthTrackerService;
    }

    @Override
    public void getAllRegisters(String type, RequestCallback<List<Calc>> callback) {
        List<Calc> calcs = healthTrackerService.getAllCalcs(type);
        callback.onSuccess(calcs);
    }

    @Override
    public void clearRegisters(String type, RequestCallback<Boolean> callback) {
        callback.onSuccess(healthTrackerService.clearCalcs(type));
    }
}
