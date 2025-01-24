package com.example.healthtracker.feature.tmb.data.data_source;

import com.example.healthtracker.common.base.RequestCallback;
import com.example.healthtracker.model.CalcDao;

public interface TmbDataSource {
    void registerTmbValue(double tmb, CalcDao dao, RequestCallback<Boolean> callback);
}
