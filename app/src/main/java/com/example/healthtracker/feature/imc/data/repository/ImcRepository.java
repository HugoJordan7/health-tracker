package com.example.healthtracker.feature.imc.data.repository;

import com.example.healthtracker.common.base.RequestCallback;
import com.example.healthtracker.model.CalcDao;

public interface ImcRepository {
    void registerImcValue(double imc, RequestCallback<Boolean> callback);
}
