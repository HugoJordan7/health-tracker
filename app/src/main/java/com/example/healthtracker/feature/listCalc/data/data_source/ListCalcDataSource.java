package com.example.healthtracker.feature.listCalc.data.data_source;

import com.example.healthtracker.common.base.RequestCallback;
import com.example.healthtracker.model.Calc;
import com.example.healthtracker.model.CalcDao;

import java.util.List;

public interface ListCalcDataSource {

    void getAllRegisters(CalcDao dao, String type, RequestCallback<List<Calc>> callback);

    void clearRegisters(CalcDao dao, String type, RequestCallback<Boolean> callback);

}
