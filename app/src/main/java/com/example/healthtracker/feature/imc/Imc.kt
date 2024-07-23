package com.example.healthtracker.feature.imc

import androidx.annotation.StringRes
import com.example.healthtracker.model.CalcDao

interface Imc {

    interface View{
        fun onRegisterImcValue()
        fun displayFailure(message: String)
    }

    interface Presenter{
        fun registerImcValue(imc: Double, type: String, dao: CalcDao)
        fun validate(height: String, weight: String): Boolean
        fun calculateImc(height: Int, weight: Int): Double
        @StringRes
        fun getImcSituation(imc: Double): Int
    }


}