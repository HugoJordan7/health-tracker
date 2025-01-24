package com.example.healthtracker.feature.listCalc.data.repository;

import com.example.healthtracker.common.base.RequestCallback;
import com.example.healthtracker.feature.listCalc.data.data_source.ListCalcDataSource;
import com.example.healthtracker.model.Calc;
import com.example.healthtracker.model.CalcDao;

import java.util.List;

public class ListCalcRepositoryImpl implements ListCalcRepository {

    private final ListCalcDataSource dataSource;

    public ListCalcRepositoryImpl(ListCalcDataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void getAllRegisters(CalcDao dao, String type, RequestCallback<List<Calc>> callback) {
        dataSource.getAllRegisters(dao, type, callback);
    }

    @Override
    public void clearRegisters(CalcDao dao, String type, RequestCallback<Boolean> callback) {
        dataSource.clearRegisters(dao, type, callback);
    }
}
