package com.example.healthtracker.feature.water.presentation

import com.example.healthtracker.feature.water.Water
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel

class WaterPresenter(private var view: Water.View?): Water.Presenter {

    private val presenter = CoroutineScope(Dispatchers.IO)

    override fun validate(weight: String, age: String): Boolean{
        return (weight.isNotEmpty() && !weight.startsWith("0") &&
                age.isNotEmpty() && !age.startsWith("0"))
    }

    override fun calculateIdealQuantityWater(age: Int, weight: Int): Int{
        return when(age){
            in 0 .. 17 -> (40 * weight)
            in 18 .. 55 -> (35 * weight)
            in 56 .. 65 -> (30 * weight)
            else -> (25 * weight)
        }
    }

    override fun quantityByExercise(exerciseFrequency: String, array: Array<String>): Int{
        return when(exerciseFrequency){
            array[0] -> 0
            array[1] -> 325
            array[2] -> 750
            array[3] -> 1075
            array[4] -> 1500
            else -> 2250
        }
    }

    override fun onDestroy() {
        presenter.cancel()
        view = null
    }

}