package com.example.healthtracker.feature.heartRate

import com.example.healthtracker.model.CalcDao

interface HeartRate {

    interface View{
        fun onHeartRateRegister()
        fun displayFailure(message: String)
    }

    interface Presenter{
        fun registerHeartRateValue(bpm: Double, hrClassification: String, dao: CalcDao)
        fun validate(age: String, bpm: String): Boolean
        fun getClassificationHeartRate(
            isMan: Boolean, bpm: Int, age: Int
        ): Pair<Int, Pair<Int, Int>>
    }

}