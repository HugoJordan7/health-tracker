package com.example.healthtracker.feature.heartRate.view

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.RadioButton
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.healthtracker.App
import com.example.healthtracker.R
import com.example.healthtracker.feature.heartRate.HeartRate
import com.example.healthtracker.feature.heartRate.presentation.HeartRatePresenter
import com.example.healthtracker.feature.listCalc.view.ListCalcActivity

class HeartRateActivity : AppCompatActivity(), HeartRate.View {

    private lateinit var presenter: HeartRate.Presenter
    private lateinit var editHeartRate: EditText
    private lateinit var editAge: EditText
    private lateinit var radioMasculine: RadioButton
    private lateinit var button: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_heart_rate)
        presenter = HeartRatePresenter(this)
        editHeartRate = findViewById(R.id.hr_edit_heart_rate)
        editAge = findViewById(R.id.hr_age)
        radioMasculine = findViewById(R.id.hr_radio_masculine)
        button = findViewById(R.id.hr_button)
        radioMasculine.isChecked = true

        val app = application as App
        val dao = app.db.calcDao()

        val arrowBackButton: ImageButton = findViewById(R.id.arrow_refs_hr)
        arrowBackButton.setOnClickListener {
            finish()
        }

        val historyButton: ImageButton = findViewById(R.id.historic_refs_hr)
        historyButton.setOnClickListener {
            onHeartRateRegister()
        }

        button.setOnClickListener {

            if(!presenter.validate(editAge.text.toString(),editHeartRate.text.toString())){
                displayFailure(getString(R.string.toast_invalid_info))
                return@setOnClickListener
            }

            val age = editAge.text.toString().toInt()
            val bpm = editHeartRate.text.toString().toInt()

            if (age < 18) {
                displayFailure(getString(R.string.toast_bellow_age))
                return@setOnClickListener
            }

            val heartRateSituation = presenter.getClassificationHeartRate(radioMasculine.isChecked, bpm, age)
            val hrClassification: String = getString(heartRateSituation.first)
            val firstHrValueRange = heartRateSituation.second.first
            val secondHrValueRange = heartRateSituation.second.second
            val currentHrRange = when {
                secondHrValueRange == 999 -> getString(R.string.current_hear_rate_range_bad,firstHrValueRange)
                firstHrValueRange == 0 -> getString(R.string.current_hear_rate_range_bellow_normal,(secondHrValueRange+1))
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
                    presenter.registerHeartRateValue(bpm.toDouble(), hrClassification, dao)
                }
                create()
                show()
            }

        }

    }

    override fun onHeartRateRegister() {
        startActivity(
            Intent(this, ListCalcActivity::class.java).putExtra("type", "bpm")
        )
    }

    override fun displayFailure(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

}