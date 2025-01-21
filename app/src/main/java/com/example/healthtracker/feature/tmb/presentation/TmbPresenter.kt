package com.example.healthtracker.feature.tmb.presentation

import com.example.healthtracker.common.util.TMB
import com.example.healthtracker.feature.tmb.Tmb
import com.example.healthtracker.model.Calc
import com.example.healthtracker.model.CalcDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TmbPresenter(private var view: Tmb.View?): Tmb.Presenter {

    private val presenterScope = CoroutineScope(Dispatchers.IO)

    override fun registerTmbValue(tmb: Double, dao: CalcDao) {
        presenterScope.launch {
            try {
                dao.insert(Calc(TMB, tmb, null))
                withContext(Dispatchers.Main){
                    view?.onRegisterTmbValue()
                }
            } catch (e: Exception){
                withContext(Dispatchers.Main){
                    view?.displayFailure(e.message ?: "Unknown error")
                }
            }
        }
    }

    override fun validate(height: String, weight: String, age: String): Boolean{
        return (height.isNotEmpty() && !height.startsWith("0") &&
                weight.isNotEmpty() && !weight.startsWith("0") &&
                age.isNotEmpty() && !age.startsWith("0") )
    }

    override fun calculateTmb(isMan: Boolean, height: Int, weight: Int, age: Int): Double{
        return if(isMan) (66 + (13.7 * weight) + (5 * height) - (6.8 * age))
        else (655 + (9.6 * weight) + (1.8 * height) - (4.7 * age))
    }

    override fun tmbAdaptedForLifestyle(currentLifeStyle: String, tmb: Double, arrayLifestyle: Array<String>): Double{
        return when(currentLifeStyle){
            arrayLifestyle[0] -> tmb * 1.2
            arrayLifestyle[1] -> tmb * 1.375
            arrayLifestyle[2] -> tmb * 1.55
            arrayLifestyle[3] -> tmb * 1.725
            arrayLifestyle[4] -> tmb * 1.9
            else->0.0
        }
    }

    override fun onDestroy() {
        presenterScope.cancel()
        view = null
    }

}