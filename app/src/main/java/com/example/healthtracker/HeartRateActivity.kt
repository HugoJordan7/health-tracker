package com.example.healthtracker

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.*
import androidx.annotation.StringRes
import androidx.appcompat.app.AlertDialog
import com.example.healthtracker.model.Calc

class HeartRateActivity : AppCompatActivity() {

    private lateinit var editHeartRate: EditText
    private lateinit var editAge: EditText
    private lateinit var radioMasculine: RadioButton
    private lateinit var button: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_heart_rate)
        editHeartRate = findViewById(R.id.hr_edit_heart_rate)
        editAge = findViewById(R.id.hr_age)
        radioMasculine = findViewById(R.id.hr_radio_masculine)
        button = findViewById(R.id.hr_button)
        radioMasculine.isChecked = true

        val arrowBackButton: ImageButton = findViewById(R.id.arrow_refs_hr)
        arrowBackButton.setOnClickListener {
            finish()
        }

        val historyButton: ImageButton = findViewById(R.id.historic_refs_hr)
        historyButton.setOnClickListener {
            openListCalcActivity()
        }

        button.setOnClickListener {
            val age = editAge.text.toString().toInt()
            val bpm = editHeartRate.text.toString().toInt()

            if (age < 18) {
                Toast.makeText(this, R.string.toast_bellow_age, Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val heartRateSituation = getClassificationHeartRate(bpm, age)
            val hrClassification: String = getString(heartRateSituation.first)
            val firstHrValueRange = heartRateSituation.second.first
            val secondHrValueRange = heartRateSituation.second.second
            val currentHrRange = when {
                secondHrValueRange == 999 -> getString(R.string.current_hear_rate_range_bad,firstHrValueRange)
                firstHrValueRange == 0 -> getString(R.string.current_hear_rate_range_bellow_normal,secondHrValueRange)
                else -> getString(R.string.current_hear_rate_range,firstHrValueRange,secondHrValueRange)
            }
            val stringSex = when{
                radioMasculine.isChecked -> getString(R.string.men)
                else -> getString(R.string.women)
            }

            AlertDialog.Builder(this).apply {
                setTitle(getString(R.string.dialog_title_bpm,hrClassification))
                setMessage(getString(R.string.dialog_message_bpm,hrClassification,stringSex,age,currentHrRange))
                setPositiveButton(R.string.ok) { _, _ -> }
                setNegativeButton(R.string.save) { _, _ ->
                    Thread {
                        val app = application as App
                        val dao = app.db.calcDao()
                        dao.insert(Calc(type = "bpm", res = bpm.toDouble(), situation = hrClassification))
                        runOnUiThread {
                            openListCalcActivity()
                        }
                    }.start()
                }
                create()
                show()
            }

        }

    }

    private fun openListCalcActivity() {
        startActivity(
            Intent(this, ListCalcActivity::class.java).putExtra("type", "bpm")
        )
    }

    private fun getClassificationHeartRate(bpm: Int, age: Int): Pair<Int, Pair<Int, Int>> {
        val classification: Pair<Int, Pair<Int, Int>> =
            if (radioMasculine.isChecked) {
                when (age) {
                    in 18..25 -> {
                        return when (bpm) {
                            in 0..55 -> Pair(R.string.bellow_normal_heart_rate, Pair(0,60))
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
        return classification
    }

}