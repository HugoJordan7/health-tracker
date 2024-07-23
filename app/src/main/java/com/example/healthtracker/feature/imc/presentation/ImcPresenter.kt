package com.example.healthtracker.feature.imc.presentation

import androidx.annotation.StringRes
import com.example.healthtracker.R
import com.example.healthtracker.feature.imc.Imc
import com.example.healthtracker.model.Calc
import com.example.healthtracker.model.CalcDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception

class ImcPresenter(private val view: Imc.View): Imc.Presenter {

    private val presenterScope = CoroutineScope(Dispatchers.IO)

    override fun registerImcValue(imc: Double, type: String, dao: CalcDao) {
        presenterScope.launch {
            try {
                dao.insert(Calc(type = type, res = imc))
                withContext(Dispatchers.Main){
                    view.onRegisterImcValue()
                }
            } catch (e: Exception){
                withContext(Dispatchers.Main){
                    view.displayFailure(e.message ?: "Unknown error")
                }
            }
        }
    }

    override fun validate(height: String, weight: String): Boolean{
        return height.isNotEmpty() && weight.isNotEmpty() &&
                !height.startsWith("0") && !weight.startsWith("0")
    }

    override fun calculateImc(height: Int, weight: Int): Double{
        return (weight / ((height/100.00)*(height/100.00)))
    }

    @StringRes
    override fun getImcSituation(imc: Double): Int{
        return when{
            imc<18.5 -> R.string.imc_low_weight
            imc<25.0 -> R.string.imc_normal_weight
            imc<30.0 -> R.string.imc_so_above_weight
            imc<35.0 -> R.string.imc_above_1
            imc<40.0 -> R.string.imc_above_2
            else -> R.string.imc_above_3
        }
    }

}