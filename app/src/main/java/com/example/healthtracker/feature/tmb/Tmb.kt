package com.example.healthtracker.feature.tmb

import com.example.healthtracker.common.base.BasePresenter
import com.example.healthtracker.common.base.BaseView
import com.example.healthtracker.feature.heartRate.HeartRate
import com.example.healthtracker.model.CalcDao

interface Tmb {

    interface View: BaseView<Presenter> {
        fun onRegisterTmbValue()
    }

    interface Presenter: BasePresenter {
        fun registerTmbValue(tmb: Double, dao: CalcDao)
        fun validate(height: String, weight: String, age: String): Boolean
        fun calculateTmb(isMan: Boolean, height: Int, weight: Int, age: Int): Double
        fun tmbAdaptedForLifestyle(currentLifeStyle: String, tmb: Double, arrayLifestyle: Array<String>): Double
    }

}