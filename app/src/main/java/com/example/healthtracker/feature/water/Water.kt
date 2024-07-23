package com.example.healthtracker.feature.water

import com.example.healthtracker.model.CalcDao

interface Water {

    interface View{
        fun onWaterRegister(){ throw UnsupportedOperationException() }
        fun displayFailure(message: String)
    }

    interface Presenter{
        fun registerWaterValue(bpm: Double, hrClassification: String, dao: CalcDao){ throw UnsupportedOperationException()}
        fun validate(weight: String, age: String): Boolean
        fun calculateIdealQuantityWater(age: Int, weight: Int): Int
        fun quantityByExercise(exerciseFrequency: String, array: Array<String>): Int
    }

}