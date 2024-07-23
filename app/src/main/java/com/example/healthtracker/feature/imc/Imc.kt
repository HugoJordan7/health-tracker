package com.example.healthtracker.feature.imc

import androidx.annotation.StringRes
import com.example.healthtracker.common.base.BasePresenter
import com.example.healthtracker.common.base.BaseView
import com.example.healthtracker.feature.heartRate.HeartRate
import com.example.healthtracker.model.CalcDao

interface Imc {

    interface View: BaseView<Presenter> {
        fun onRegisterImcValue()
    }

    interface Presenter: BasePresenter {
        fun registerImcValue(imc: Double, dao: CalcDao)
        fun validate(height: String, weight: String): Boolean
        fun calculateImc(height: Int, weight: Int): Double
        @StringRes
        fun getImcSituation(imc: Double): Int
    }

}