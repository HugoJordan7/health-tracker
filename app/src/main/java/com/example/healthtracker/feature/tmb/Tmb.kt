package com.example.healthtracker.feature.tmb

import com.example.healthtracker.model.CalcDao

interface Tmb {

    interface View{
        fun onRegisterTmbValue()
        fun displayFailure(message: String)
    }

    interface Presenter{
        fun registerTmbValue(tmb: Double, type: String, dao: CalcDao)
        fun validate(height: String, weight: String, age: String): Boolean
        fun calculateTmb(isMan: Boolean, height: Int, weight: Int, age: Int): Double
        fun tmbAdaptedForLifestyle(currentLifeStyle: String, tmb: Double, arrayLifestyle: Array<String>): Double
    }

}