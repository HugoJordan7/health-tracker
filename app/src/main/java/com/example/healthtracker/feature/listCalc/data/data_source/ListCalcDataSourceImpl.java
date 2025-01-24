package com.example.healthtracker.feature.listCalc.data.data_source;

import com.example.healthtracker.common.base.RequestCallback;
import com.example.healthtracker.model.Calc;
import com.example.healthtracker.model.CalcDao;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Callable;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class ListCalcDataSourceImpl implements ListCalcDataSource {

    @Override
    public void getAllRegisters(CalcDao dao, String type, RequestCallback<List<Calc>> callback) {
        Observable
                .fromCallable(() -> dao.getRegisterByType(type))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<Calc>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {}

                    @Override
                    public void onNext(@NonNull List<Calc> calcList) {
                        callback.onSuccess(calcList);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        callback.onFailure(e.getMessage());
                    }

                    @Override
                    public void onComplete() {}
                });
    }

    @Override
    public void clearRegisters(CalcDao dao, String type, RequestCallback<Boolean> callback) {
        Completable
                .fromAction(() -> {
                    dao.deleteAllByType(type);
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {}

                    @Override
                    public void onComplete() {
                        callback.onSuccess(true);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        callback.onFailure(e.getMessage());
                    }
                });
    }

}
