package com.example.healthtracker.feature.tmb.data.repository;

import com.example.healthtracker.common.base.RequestCallback;
import com.example.healthtracker.feature.tmb.data.data_source.TmbDataSource;
import com.example.healthtracker.model.CalcDao;

public class TmbRepositoryImpl implements TmbRepository {

    private final TmbDataSource dataSource;

    public TmbRepositoryImpl(TmbDataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void registerTmbValue(double tmb, CalcDao dao, RequestCallback<Boolean> callback) {
        dataSource.registerTmbValue(tmb, dao, callback);
    }
}
