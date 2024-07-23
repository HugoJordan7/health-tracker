package com.example.healthtracker.feature.heartRate

import com.example.healthtracker.common.base.BasePresenter
import com.example.healthtracker.common.base.BaseView
import com.example.healthtracker.model.CalcDao

interface HeartRate {

    interface View: BaseView<Presenter> {
        fun onHeartRateRegister()
    }

    interface Presenter: BasePresenter {
        fun registerHeartRateValue(bpm: Double, hrClassification: String, dao: CalcDao)
        fun validate(age: String, bpm: String): Boolean
        fun getClassificationHeartRate(
            isMan: Boolean, bpm: Int, age: Int
        ): Pair<Int, Pair<Int, Int>>
    }

}