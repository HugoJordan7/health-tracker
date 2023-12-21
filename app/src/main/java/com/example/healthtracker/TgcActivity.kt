package com.example.healthtracker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import androidx.annotation.StringRes
import androidx.appcompat.app.AlertDialog

class TgcActivity : AppCompatActivity() {

    private lateinit var editImc: EditText
    private lateinit var editAge: EditText
    private lateinit var radioGroupSex: RadioGroup
    private lateinit var radioMasculine: RadioButton
    private lateinit var button: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tgc)
        editImc = findViewById(R.id.tgc_value_imc)
        editAge = findViewById(R.id.tgc_age)
        radioGroupSex = findViewById(R.id.tgc_radio_group_sex)
        radioMasculine = findViewById(R.id.tgc_radio_masculine)
        button = findViewById(R.id.tgc_button)
        radioMasculine.isChecked = true

        button.setOnClickListener {
            if(!validate()){
                Toast.makeText(this,R.string.toast_invalid_info,Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val tgc = calculateTgc()

            AlertDialog.Builder(this).apply {
                setTitle(getString(R.string.dialog_tgc_title,tgc))
                setMessage(tgcClassification(tgc.toInt()))
                setPositiveButton(R.string.ok){_,_->}
                setNegativeButton(R.string.save){_,_->}
                create()
                show()
            }

        }

    }

    private fun validate(): Boolean{
        return (editImc.text.toString().isNotEmpty() && !editImc.text.toString().startsWith("0") &&
                editAge.text.toString().isNotEmpty() && !editAge.text.toString().startsWith("0"))
    }

    private fun calculateTgc(): Double{
        return when(radioMasculine.isChecked){
                true -> ((1.2 * editImc.text.toString().toDouble()) - (10.8 * 1) + (0.23 * editAge.text.toString().toInt()) - 5.4 * 1000.1/ (25.5*25.5))
                false -> ((1.2 * editImc.text.toString().toDouble()) - (10.8 * 0) + (0.23 * editAge.text.toString().toInt()) - 5.4 * 1000.1/ (25.5*25.5))
        }
    }

    @StringRes
    private fun tgcClassification(tgc: Int):Int {
        if (radioMasculine.isChecked) { // Is Masculine
            return when(tgc){
                in 3 until 6 -> R.string.tgc_essential_fat
                in 6 until 14 -> R.string.tgc_athletic_fat
                in 14 until 18 -> R.string.tgc_gymnast_fat
                in 18 until 25 -> R.string.tgc_acceptable_fat
                else -> R.string.tgc_obesity_fat
            }
        }
        return when(tgc){ // Is Feminine
            in 10 until 14 -> R.string.tgc_essential_fat
            in 14 until 21 -> R.string.tgc_athletic_fat
            in 21 until 25 -> R.string.tgc_gymnast_fat
            in 25 until 32 -> R.string.tgc_acceptable_fat
            else -> R.string.tgc_obesity_fat
        }
    }
}