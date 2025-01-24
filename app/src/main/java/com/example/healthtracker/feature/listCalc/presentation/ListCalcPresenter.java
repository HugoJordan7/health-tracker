package com.example.healthtracker.feature.listCalc.presentation;

import com.example.healthtracker.common.base.RequestCallback;
import com.example.healthtracker.feature.listCalc.ListCalc;
import com.example.healthtracker.feature.listCalc.data.repository.ListCalcRepository;
import com.example.healthtracker.model.Calc;
import com.example.healthtracker.model.CalcDao;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ListCalcPresenter implements ListCalc.Presenter {

    private ListCalc.View view;
    private final ListCalcRepository repository;

    public ListCalcPresenter(ListCalc.View view, ListCalcRepository repository) {
        this.view = view;
        this.repository = repository;
    }

    @Override
    public void getAllRegisters(CalcDao dao, String type) {
        repository.getAllRegisters(dao, type, new RequestCallback<List<Calc>>() {
            @Override
            public void onSuccess(List<Calc> data) {
                view.displayAllRegisters(data);
            }

            @Override
            public void onFailure(String message) {
                view.displayFailure(message != null ? message : "Unknown error");
            }
        });
    }

    @Override
    public void clearRegisters(CalcDao dao, String type) {
        repository.clearRegisters(dao, type, new RequestCallback<Boolean>() {
            @Override
            public void onSuccess(Boolean data) {
                view.onDeleteRegisters();
            }

            @Override
            public void onFailure(String message) {
                view.displayFailure(message != null ? message : "Unknown error");
            }
        });
    }

    @Override
    public void onDestroy() {
        view = null;
    }
}
