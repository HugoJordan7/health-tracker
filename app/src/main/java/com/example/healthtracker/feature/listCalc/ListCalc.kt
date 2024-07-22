package com.example.healthtracker.feature.listCalc

import androidx.annotation.StringRes
import com.example.healthtracker.model.Calc
import com.example.healthtracker.model.CalcDao

interface ListCalc {

    interface View{
        fun displayAllRegisters(list: List<Calc>)
        fun displayFailure(@StringRes message: Int)
        fun onDeleteRegisters()
    }

    interface Presenter{
        fun getAllRegisters(dao: CalcDao, type: String)
        fun clearRegisters(list: List<Calc>, dao: CalcDao, type: String)
    }

}