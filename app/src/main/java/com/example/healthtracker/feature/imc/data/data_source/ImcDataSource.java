package com.example.healthtracker.feature.imc.data.data_source;

import com.example.healthtracker.common.base.RequestCallback;
import com.example.healthtracker.model.CalcDao;

public interface ImcDataSource {
    void registerImcValue(double imc, CalcDao dao, RequestCallback<Boolean> callback);
}
