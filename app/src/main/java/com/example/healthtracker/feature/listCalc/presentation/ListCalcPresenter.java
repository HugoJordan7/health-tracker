package com.example.healthtracker.feature.listCalc.presentation;

import com.example.healthtracker.feature.listCalc.ListCalc;
import com.example.healthtracker.model.Calc;
import com.example.healthtracker.model.CalcDao;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ListCalcPresenter implements ListCalc.Presenter {

    private ListCalc.View view;
    private final ExecutorService executorService;

    public ListCalcPresenter(ListCalc.View view) {
        this.view = view;
        this.executorService = Executors.newSingleThreadExecutor();
    }

    @Override
    public void getAllRegisters(CalcDao dao, String type) {
        executorService.execute(() -> {
            List<Calc> list = dao.getRegisterByType(type);
            if (view != null) {
                new android.os.Handler(android.os.Looper.getMainLooper()).post(() -> {
                    if (view != null) {
                        view.displayAllRegisters(list);
                    }
                });
            }
        });
    }

    @Override
    public void clearRegisters(List<Calc> list, CalcDao dao, String type) {
        executorService.execute(() -> {
            if (list.isEmpty()) {
                if (view != null) {
                    new android.os.Handler(android.os.Looper.getMainLooper()).post(() -> {
                        if (view != null) {
                            view.displayFailure("");
                        }
                    });
                }
            } else {
                dao.deleteAllByType(type);
                if (view != null) {
                    new android.os.Handler(android.os.Looper.getMainLooper()).post(() -> {
                        if (view != null) {
                            view.onDeleteRegisters();
                        }
                    });
                }
            }
        });
    }

    @Override
    public void onDestroy() {
        if (!executorService.isShutdown()) {
            executorService.shutdown();
        }
        view = null;
    }
}
