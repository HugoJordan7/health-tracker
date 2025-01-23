package com.example.healthtracker.feature.heartRate.data.data_source;

import com.example.healthtracker.common.base.RequestCallback;
import com.example.healthtracker.model.CalcDao;

public interface HeartRateDataSource {
    public void registerHeartRateValue(
            double bpm,
            String hrClassification,
            CalcDao dao,
            RequestCallback<Boolean> callback
    );
}
