package com.example.healthtracker.feature.listCalc;

import com.example.healthtracker.common.base.BasePresenter;
import com.example.healthtracker.common.base.BaseView;
import com.example.healthtracker.model.Calc;
import com.example.healthtracker.model.CalcDao;
import java.util.List;

public interface ListCalc {

    interface View extends BaseView {
        void displayAllRegisters(List<Calc> list);
        void onDeleteRegisters();
    }

    interface Presenter extends BasePresenter {
        void getAllRegisters(CalcDao dao, String type);
        void clearRegisters(CalcDao dao, String type);
    }
}
