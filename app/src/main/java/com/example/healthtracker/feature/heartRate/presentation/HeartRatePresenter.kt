package com.example.healthtracker.feature.heartRate.presentation

import com.example.healthtracker.App
import com.example.healthtracker.R
import com.example.healthtracker.feature.heartRate.HeartRate
import com.example.healthtracker.model.Calc
import com.example.healthtracker.model.CalcDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception

class HeartRatePresenter(private val view: HeartRate.View): HeartRate.Presenter {

    companion object{
        const val BPM = "bpm"
    }

    private val presenterScope = CoroutineScope(Dispatchers.IO)

    override fun registerHeartRateValue(bpm: Double, hrClassification: String, dao: CalcDao) {
        presenterScope.launch {
            try {
                dao.insert(Calc(type = BPM, res = bpm, situation = hrClassification))
                withContext(Dispatchers.Main){
                    view.onHeartRateRegister()
                }
            } catch (e: Exception){
                withContext(Dispatchers.Main){
                    view.displayFailure(e.message ?: "Unknown error")
                }
            }
        }
    }

    override fun validate(age: String, bpm: String): Boolean{
        return (bpm.isNotEmpty() && !bpm.startsWith("0")
                && age.isNotEmpty() && !age.startsWith("0"))
    }

    override fun getClassificationHeartRate(isMan: Boolean, bpm: Int, age: Int): Pair<Int, Pair<Int, Int>> {
        if (isMan) {
            when (age) {
                in 18..25 -> {
                    return when (bpm) {
                        in 0..55 -> Pair(R.string.bellow_normal_heart_rate, Pair(0,55))
                        in 56..61 -> Pair(R.string.excellent_heart_rate, Pair(56, 61))
                        in 62..65 -> Pair(R.string.optimum_heart_rate, Pair(62, 65))
                        in 66..69 -> Pair(R.string.good_heart_rate, Pair(66, 69))
                        in 70..73 -> Pair(R.string.normal_heart_rate, Pair(70, 73))
                        in 74..81 -> Pair(R.string.less_good_heart_rate, Pair(74, 81))
                        else -> Pair(R.string.bad_heart_rate, Pair(81, 999))
                    }
                }
                in 26..35 -> {
                    return when (bpm) {
                        in 0..54 -> Pair(R.string.bellow_normal_heart_rate, Pair(0,54))
                        in 55..61 -> Pair(R.string.excellent_heart_rate, Pair(55, 61))
                        in 62..65 -> Pair(R.string.optimum_heart_rate, Pair(62, 65))
                        in 66..70 -> Pair(R.string.good_heart_rate, Pair(66, 70))
                        in 71..74 -> Pair(R.string.normal_heart_rate, Pair(71, 74))
                        in 75..81 -> Pair(R.string.less_good_heart_rate, Pair(75, 81))
                        else -> Pair(R.string.bad_heart_rate, Pair(81, 999))
                    }
                }
                in 36..45 -> {
                    return when (bpm) {
                        in 0..56 -> Pair(R.string.bellow_normal_heart_rate, Pair(0,56))
                        in 57..62 -> Pair(R.string.excellent_heart_rate, Pair(57, 62))
                        in 63..66 -> Pair(R.string.optimum_heart_rate, Pair(63, 66))
                        in 67..70 -> Pair(R.string.good_heart_rate, Pair(67, 70))
                        in 71..75 -> Pair(R.string.normal_heart_rate, Pair(71, 75))
                        in 76..82 -> Pair(R.string.less_good_heart_rate, Pair(76, 82))
                        else -> Pair(R.string.bad_heart_rate, Pair(82, 999))
                    }
                }
                in 46..55 -> {
                    return when (bpm) {
                        in 0..57 -> Pair(R.string.bellow_normal_heart_rate, Pair(0,57))
                        in 58..63 -> Pair(R.string.excellent_heart_rate, Pair(58, 63))
                        in 64..67 -> Pair(R.string.optimum_heart_rate, Pair(64, 67))
                        in 68..71 -> Pair(R.string.good_heart_rate, Pair(68, 71))
                        in 72..76 -> Pair(R.string.normal_heart_rate, Pair(72, 76))
                        in 77..83 -> Pair(R.string.less_good_heart_rate, Pair(77, 83))
                        else -> Pair(R.string.bad_heart_rate, Pair(83, 999))
                    }
                }
                in 56..65 -> {
                    return when (bpm) {
                        in 0..56 -> Pair(R.string.bellow_normal_heart_rate, Pair(0,56))
                        in 57..61 -> Pair(R.string.excellent_heart_rate, Pair(57, 61))
                        in 62..67 -> Pair(R.string.optimum_heart_rate, Pair(62, 67))
                        in 68..71 -> Pair(R.string.good_heart_rate, Pair(68, 71))
                        in 72..75 -> Pair(R.string.normal_heart_rate, Pair(72, 75))
                        in 76..81 -> Pair(R.string.less_good_heart_rate, Pair(76, 81))
                        else -> Pair(R.string.bad_heart_rate, Pair(81, 999))
                    }
                }
                else -> {
                    return when (bpm) {
                        in 0..55 -> Pair(R.string.bellow_normal_heart_rate, Pair(0,55))
                        in 56..61 -> Pair(R.string.excellent_heart_rate, Pair(56, 61))
                        in 62..65 -> Pair(R.string.optimum_heart_rate, Pair(62, 65))
                        in 66..69 -> Pair(R.string.good_heart_rate, Pair(66, 69))
                        in 70..73 -> Pair(R.string.normal_heart_rate, Pair(70, 73))
                        in 74..79 -> Pair(R.string.less_good_heart_rate, Pair(74, 79))
                        else -> Pair(R.string.bad_heart_rate, Pair(79, 999))
                    }
                }
            }
        } else {
            when (age) {
                in 18..25 -> {
                    return when (bpm) {
                        in 0..60 -> Pair(R.string.bellow_normal_heart_rate, Pair(0,60))
                        in 61..65 -> Pair(R.string.excellent_heart_rate, Pair(61, 65))
                        in 66..69 -> Pair(R.string.optimum_heart_rate, Pair(66, 69))
                        in 70..73 -> Pair(R.string.good_heart_rate, Pair(70, 73))
                        in 74..78 -> Pair(R.string.normal_heart_rate, Pair(74, 78))
                        in 79..84 -> Pair(R.string.less_good_heart_rate, Pair(79, 84))
                        else -> Pair(R.string.bad_heart_rate, Pair(84, 999))
                    }
                }
                in 26..35 -> {
                    return when (bpm) {
                        in 0..59 -> Pair(R.string.bellow_normal_heart_rate, Pair(0,59))
                        in 60..64 -> Pair(R.string.excellent_heart_rate, Pair(60, 64))
                        in 65..68 -> Pair(R.string.optimum_heart_rate, Pair(65, 68))
                        in 69..72 -> Pair(R.string.good_heart_rate, Pair(69, 72))
                        in 73..76 -> Pair(R.string.normal_heart_rate, Pair(73, 76))
                        in 77..82 -> Pair(R.string.less_good_heart_rate, Pair(77, 82))
                        else -> Pair(R.string.bad_heart_rate, Pair(82, 999))
                    }
                }
                in 36..45 -> {
                    return when (bpm) {
                        in 0..59 -> Pair(R.string.bellow_normal_heart_rate, Pair(0,59))
                        in 60..64 -> Pair(R.string.excellent_heart_rate, Pair(60, 64))
                        in 65..69 -> Pair(R.string.optimum_heart_rate, Pair(65, 69))
                        in 70..73 -> Pair(R.string.good_heart_rate, Pair(70, 73))
                        in 74..78 -> Pair(R.string.normal_heart_rate, Pair(74, 78))
                        in 79..84 -> Pair(R.string.less_good_heart_rate, Pair(79, 84))
                        else -> Pair(R.string.bad_heart_rate, Pair(84, 999))
                    }
                }
                in 46..55 -> {
                    return when (bpm) {
                        in 0..60 -> Pair(R.string.bellow_normal_heart_rate, Pair(0,60))
                        in 61..65 -> Pair(R.string.excellent_heart_rate, Pair(61, 65))
                        in 66..69 -> Pair(R.string.optimum_heart_rate, Pair(66, 69))
                        in 70..73 -> Pair(R.string.good_heart_rate, Pair(70, 73))
                        in 74..77 -> Pair(R.string.normal_heart_rate, Pair(74, 77))
                        in 78..83 -> Pair(R.string.less_good_heart_rate, Pair(78, 83))
                        else -> Pair(R.string.bad_heart_rate, Pair(83, 999))
                    }
                }
                in 56..65 -> {
                    return when (bpm) {
                        in 0..59 -> Pair(R.string.bellow_normal_heart_rate, Pair(0,59))
                        in 60..64 -> Pair(R.string.excellent_heart_rate, Pair(60, 64))
                        in 65..68 -> Pair(R.string.optimum_heart_rate, Pair(65, 68))
                        in 69..73 -> Pair(R.string.good_heart_rate, Pair(69, 73))
                        in 74..77 -> Pair(R.string.normal_heart_rate, Pair(74, 77))
                        in 78..83 -> Pair(R.string.less_good_heart_rate, Pair(78, 83))
                        else -> Pair(R.string.bad_heart_rate, Pair(83, 999))
                    }
                }
                else -> {
                    return when (bpm) {
                        in 0..59 -> Pair(R.string.bellow_normal_heart_rate, Pair(0,59))
                        in 60..64 -> Pair(R.string.excellent_heart_rate, Pair(60, 64))
                        in 65..68 -> Pair(R.string.optimum_heart_rate, Pair(65, 68))
                        in 69..72 -> Pair(R.string.good_heart_rate, Pair(69, 72))
                        in 73..76 -> Pair(R.string.normal_heart_rate, Pair(73, 76))
                        in 77..84 -> Pair(R.string.less_good_heart_rate, Pair(77, 84))
                        else -> Pair(R.string.bad_heart_rate, Pair(84, 999))
                    }
                }
            }
        }
    }


}