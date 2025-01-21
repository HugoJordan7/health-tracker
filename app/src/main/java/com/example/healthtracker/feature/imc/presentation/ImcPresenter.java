package com.example.healthtracker.feature.imc.presentation;

import com.example.healthtracker.R;
import com.example.healthtracker.common.util.Constants;
import com.example.healthtracker.feature.imc.Imc;
import com.example.healthtracker.model.Calc;
import com.example.healthtracker.model.CalcDao;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class ImcPresenter implements Imc.Presenter {

    private Imc.View view;

    private final CompositeDisposable disposables = new CompositeDisposable();

    public ImcPresenter(Imc.View view) {
        this.view = view;
    }

    @Override
    public void registerImcValue(double imc, CalcDao dao) {
        Completable
                .fromAction(() -> {
                    dao.insert(new Calc(Constants.IMC, imc, null));
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        disposables.add(d);
                    }

                    @Override
                    public void onComplete() {
                        view.onRegisterImcValue();
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        view.displayFailure(e.getMessage());
                    }
                });

    }

    @Override
    public boolean validate(String height, String weight) {
        return !height.isEmpty() && !weight.isEmpty() &&
                !height.startsWith("0") && !weight.startsWith("0");
    }

    @Override
    public double calculateImc(double height, double weight) {
        return (weight / ((height / 100.00) * (height / 100.00)));
    }

    @Override
    public int getImcSituation(double imc) {
        if (imc < 18.5) {
            return R.string.imc_low_weight;
        } else if (imc < 25.0) {
            return R.string.imc_normal_weight;
        } else if (imc < 30.0) {
            return R.string.imc_so_above_weight;
        } else if (imc < 35.0) {
            return R.string.imc_above_1;
        } else if (imc < 40.0) {
            return R.string.imc_above_2;
        } else {
            return R.string.imc_above_3;
        }
    }

    @Override
    public void onDestroy() {
        disposables.clear();
        view = null;
    }
}
