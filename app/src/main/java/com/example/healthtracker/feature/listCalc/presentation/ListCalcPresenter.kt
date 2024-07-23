package com.example.healthtracker.feature.listCalc.presentation

import android.content.Context
import android.content.res.Resources
import com.example.healthtracker.R
import com.example.healthtracker.feature.listCalc.ListCalc
import com.example.healthtracker.model.Calc
import com.example.healthtracker.model.CalcDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ListCalcPresenter(private var view: ListCalc.View?): ListCalc.Presenter {

    private val presenterScope = CoroutineScope(Dispatchers.IO)

    override fun getAllRegisters(dao: CalcDao, type: String){
        presenterScope.launch {
            val list = dao.getRegisterByType(type)
            withContext(Dispatchers.Main){
                view?.displayAllRegisters(list)
            }
        }
    }

    override fun clearRegisters(list: List<Calc>, dao: CalcDao, type: String) {
        presenterScope.launch {
            if (list.isEmpty()){
                withContext(Dispatchers.Main){
                    view?.displayFailure("")
                }
            } else {
                dao.deleteAllByType(type)
                withContext(Dispatchers.Main){
                    view?.onDeleteRegisters()
                }
            }
        }
    }

    override fun onDestroy() {
        presenterScope.cancel()
        view = null
    }

}