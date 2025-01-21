package com.example.healthtracker.feature.imc.data.repository;

import com.example.healthtracker.common.base.RequestCallback;
import com.example.healthtracker.feature.imc.data.data_source.ImcDataSource;
import com.example.healthtracker.model.CalcDao;

public class ImcRepositoryImpl implements ImcRepository {

    private ImcDataSource dataSource;

    public ImcRepositoryImpl(ImcDataSource dataSource){
        this.dataSource = dataSource;
    }

    @Override
    public void registerImcValue(double imc, CalcDao dao, RequestCallback<Boolean> callback) {
        dataSource.registerImcValue(imc, dao, callback);
    }
}
