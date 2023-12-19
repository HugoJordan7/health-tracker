package com.example.healthtracker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.appcompat.app.AlertDialog

class ImcActivity : AppCompatActivity() {

    lateinit var editHeight: EditText
    lateinit var editWeight: EditText
    lateinit var buttonResult: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_imc)
        editHeight = findViewById(R.id.imc_height)
        editWeight = findViewById(R.id.imc_weight)
        buttonResult = findViewById(R.id.imc_button)

        buttonResult.setOnClickListener {
            if(!validate(editHeight.toString(), editWeight.toString())){
                Toast.makeText(this,R.string.toast_invalid_info,Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val height = editHeight.text.toString().toDouble()
            val weight = editWeight.text.toString().toDouble()
            val imcResult = calculateImc(height.toInt(),weight.toInt())

            AlertDialog.Builder(this).apply {
                setTitle(getString(R.string.dialog_imc_title,imcResult))
                setMessage(getImcSituation(imcResult))
                setPositiveButton(R.string.ok){ _,_ ->

                }
                setNegativeButton(R.string.save){ _,_ ->

                }
                create()
                show()
            }
        }
    }

    private fun validate(height: String, weight: String): Boolean{
        return height.isNotEmpty() && weight.isNotEmpty() &&
                !height.startsWith("0") && !weight.startsWith("0")
    }

    private fun calculateImc(height: Int, weight: Int): Double{
        return (weight / ((height/100.00)*(height/100.00)))
    }

    @StringRes
    private fun getImcSituation(imc: Double): Int{
        return when{
            imc<15.0 -> R.string.imc_severely_low_weight
            imc<16.0 -> R.string.imc_very_low_weight
            imc<18.5 -> R.string.imc_low_weight
            imc<25.0 -> R.string.imc_normal_weight
            imc<30.0 -> R.string.imc_so_above_weight
            imc<35.0 -> R.string.imc_above_weight
            imc<40.0 -> R.string.imc_very_above_weight
            else -> R.string.imc_severely_above_weight
        }
    }

}