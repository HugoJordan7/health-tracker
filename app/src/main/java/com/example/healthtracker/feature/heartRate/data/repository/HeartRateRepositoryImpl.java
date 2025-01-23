package com.example.healthtracker.feature.heartRate.data.repository;

import com.example.healthtracker.common.base.RequestCallback;
import com.example.healthtracker.feature.heartRate.data.data_source.HeartRateDataSource;
import com.example.healthtracker.model.CalcDao;

public class HeartRateRepositoryImpl implements HeartRateRepository {

    private final HeartRateDataSource dataSource;

    public HeartRateRepositoryImpl(HeartRateDataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void registerHeartRateValue(double bpm, String hrClassification, CalcDao dao, RequestCallback<Boolean> callback) {
        dataSource.registerHeartRateValue(bpm, hrClassification, dao, callback);
    }

}
