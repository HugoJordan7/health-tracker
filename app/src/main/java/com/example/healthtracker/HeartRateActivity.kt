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

            if(age < 18){
                Toast.makeText(this,R.string.toast_bellow_age,Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val heartRateSituation = getClassificationHeartRate(bpm, age)

            AlertDialog.Builder(this).apply {
                setTitle(heartRateSituation)
                setPositiveButton(R.string.ok){_,_->}
                setNegativeButton(R.string.save){_,_->
                    Thread{
                        val app = application as App
                        val dao = app.db.calcDao()
                        dao.insert(Calc(type = "bpm", res = bpm.toDouble()))
                        runOnUiThread{
                            openListCalcActivity()
                        }
                    }.start()
                }
                create()
                show()
            }

        }

    }

    @StringRes
    private fun getClassificationHeartRate(bpm: Int, age: Int): Int{
        //Masculine
        if (radioMasculine.isChecked){
            when(age){
                in 18..25 -> {
                    return when(bpm){
                        in 56..61 -> R.string.excellent_heart_rate
                        in 62..65 -> R.string.optimum_heart_rate
                        in 66..69 -> R.string.good_heart_rate
                        in 70..73 -> R.string.normal_heart_rate
                        in 74..81 -> R.string.less_good_heart_rate
                        else -> R.string.bad_heart_rate
                    }
                }
                in 26..35 -> {
                    return when(bpm){
                        in 55..61 -> R.string.excellent_heart_rate
                        in 62..65 -> R.string.optimum_heart_rate
                        in 66..70 -> R.string.good_heart_rate
                        in 71..74 -> R.string.normal_heart_rate
                        in 75..81 -> R.string.less_good_heart_rate
                        else -> R.string.bad_heart_rate
                    }
                }
                in 36..45 -> {
                    return when(bpm){
                        in 57..62 -> R.string.excellent_heart_rate
                        in 63..66 -> R.string.optimum_heart_rate
                        in 67..70 -> R.string.good_heart_rate
                        in 71..75 -> R.string.normal_heart_rate
                        in 76..82 -> R.string.less_good_heart_rate
                        else -> R.string.bad_heart_rate
                    }
                }
                in 46..55 -> {
                    return when(bpm){
                        in 58..63 -> R.string.excellent_heart_rate
                        in 64..67 -> R.string.optimum_heart_rate
                        in 68..71 -> R.string.good_heart_rate
                        in 72..76 -> R.string.normal_heart_rate
                        in 77..83 -> R.string.less_good_heart_rate
                        else -> R.string.bad_heart_rate
                    }
                }
                in 56..65 -> {
                    return when(bpm){
                        in 57..61 -> R.string.excellent_heart_rate
                        in 62..67 -> R.string.optimum_heart_rate
                        in 68..71 -> R.string.good_heart_rate
                        in 72..75 -> R.string.normal_heart_rate
                        in 76..81 -> R.string.less_good_heart_rate
                        else -> R.string.bad_heart_rate
                    }
                }
                else-> {
                    return when(bpm){
                        in 56..61 -> R.string.excellent_heart_rate
                        in 62..65 -> R.string.optimum_heart_rate
                        in 66..69 -> R.string.good_heart_rate
                        in 70..73 -> R.string.normal_heart_rate
                        in 74..79 -> R.string.less_good_heart_rate
                        else -> R.string.bad_heart_rate
                    }
                }
            }
        }

        //Feminine
        when(age){
            in 18..25 -> {
                return when(bpm){
                    in 61..65 -> R.string.excellent_heart_rate
                    in 66..69 -> R.string.optimum_heart_rate
                    in 70..73 -> R.string.good_heart_rate
                    in 74..78 -> R.string.normal_heart_rate
                    in 79..84 -> R.string.less_good_heart_rate
                    else -> R.string.bad_heart_rate
                }
            }
            in 26..35 -> {
                return when(bpm){
                    in 60..64 -> R.string.excellent_heart_rate
                    in 65..68 -> R.string.optimum_heart_rate
                    in 69..72 -> R.string.good_heart_rate
                    in 73..76 -> R.string.normal_heart_rate
                    in 77..82 -> R.string.less_good_heart_rate
                    else -> R.string.bad_heart_rate
                }
            }
            in 36..45 -> {
                return when(bpm){
                    in 60..64 -> R.string.excellent_heart_rate
                    in 65..69 -> R.string.optimum_heart_rate
                    in 70..73 -> R.string.good_heart_rate
                    in 74..78 -> R.string.normal_heart_rate
                    in 79..84 -> R.string.less_good_heart_rate
                    else -> R.string.bad_heart_rate
                }
            }
            in 46..55 -> {
                return when(bpm){
                    in 61..65 -> R.string.excellent_heart_rate
                    in 66..69 -> R.string.optimum_heart_rate
                    in 70..73 -> R.string.good_heart_rate
                    in 74..77 -> R.string.normal_heart_rate
                    in 78..83 -> R.string.less_good_heart_rate
                    else -> R.string.bad_heart_rate
                }
            }
            in 56..65 -> {
                return when(bpm){
                    in 60..64 -> R.string.excellent_heart_rate
                    in 65..68 -> R.string.optimum_heart_rate
                    in 69..73 -> R.string.good_heart_rate
                    in 74..77 -> R.string.normal_heart_rate
                    in 78..83 -> R.string.less_good_heart_rate
                    else -> R.string.bad_heart_rate
                }
            }
            else-> {
                return when(bpm){
                    in 60..64 -> R.string.excellent_heart_rate
                    in 65..68 -> R.string.optimum_heart_rate
                    in 69..72 -> R.string.good_heart_rate
                    in 73..76 -> R.string.normal_heart_rate
                    in 77..84 -> R.string.less_good_heart_rate
                    else -> R.string.bad_heart_rate
                }
            }
        }
    }

    private fun openListCalcActivity() {
        startActivity(
            Intent(this,ListCalcActivity::class.java).putExtra("type","tgc")
        )
    }

}