package com.example.healthtracker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.appcompat.app.AlertDialog

private lateinit var editWeight: EditText
private lateinit var editAge: EditText
private lateinit var button: Button
private lateinit var editQuantity: EditText

class WaterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_water)
        editWeight = findViewById(R.id.water_weight)
        editAge = findViewById(R.id.water_age)
        editQuantity = findViewById(R.id.water_quantity)
        button = findViewById(R.id.water_button)
        button.setOnClickListener {
            if(!validate()){
                Toast.makeText(this,R.string.toast_invalid_info,Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val weight = editWeight.text.toString().toInt()
            val age = editAge.text.toString().toInt()
            val quantity = editQuantity.text.toString().toDouble()
            val idealQuantityWater = calculateIdealQuantityWater(age,weight)
            AlertDialog.Builder(this).apply {
                setTitle(R.string.dialog_water_title)

                setMessage(getString(R.string.dialog_water_message,
                if(quantity < idealQuantityWater) R.string.warning else R.string.congratilations,

                ))
            }
        }
    }

    private fun validate(): Boolean{
        return (editWeight.text.toString().isNotEmpty() &&
                !editWeight.text.toString().startsWith("0") &&
                editAge.text.toString().isNotEmpty() &&
                !editAge.text.toString().startsWith("0"))
    }

    private fun calculateIdealQuantityWater(age: Int, weight: Int): Double{
        return when(age){
            in 0 .. 17 -> (40 * weight)/1000.0
            in 18 .. 55 -> (35 * weight)/1000.0
            in 56 .. 65 -> (30 * weight)/1000.0
            else -> (25 * weight)/1000.0
        }
    }

    @StringRes
    private fun situationOfQuantityWater(quantity: Double, idealQuantity: Double): Int{
        return if(quantity > idealQuantity) {
            R.string.congratilations
        } else{
            R.string.warning
        }

    }
}