package com.example.healthtracker.feature.tmb.data.repository;

import com.example.healthtracker.common.base.RequestCallback;
import com.example.healthtracker.model.CalcDao;

public interface TmbRepository {
    void registerTmbValue(double tmb, CalcDao dao, RequestCallback<Boolean> callback);
}
