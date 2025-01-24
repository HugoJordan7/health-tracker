package com.example.healthtracker.feature.tmb.data.data_source;

import com.example.healthtracker.common.base.RequestCallback;
import com.example.healthtracker.common.util.Constants;
import com.example.healthtracker.model.Calc;
import com.example.healthtracker.model.CalcDao;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class TmbDataSourceImpl implements TmbDataSource {

    @Override
    public void registerTmbValue(double tmb, CalcDao dao, RequestCallback<Boolean> callback) {
        Completable
                .fromAction(() -> {
                    dao.insert(new Calc(Constants.TMB, tmb, null));
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
