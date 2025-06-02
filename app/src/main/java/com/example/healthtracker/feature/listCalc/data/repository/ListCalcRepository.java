package com.example.healthtracker.feature.listCalc.data.repository;

import com.example.healthtracker.common.base.RequestCallback;
import com.example.healthtracker.model.Calc;

import java.util.List;

public interface ListCalcRepository {

    void getAllRegisters(String type, RequestCallback<List<Calc>> callback);

    void clearRegisters(String type, RequestCallback<Boolean> callback);

}
