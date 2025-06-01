package com.example.healthtracker.feature.heartRate.data.repository;

import com.example.healthtracker.common.base.RequestCallback;
import com.example.healthtracker.model.CalcDao;

public interface HeartRateRepository {
    void registerHeartRateValue(double bpm, String hrClassification, RequestCallback<Boolean> callback);
}
