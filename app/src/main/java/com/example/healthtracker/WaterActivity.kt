package com.example.healthtracker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import androidx.annotation.StringDef
import androidx.annotation.StringRes
import androidx.appcompat.app.AlertDialog

private lateinit var editWeight: EditText
private lateinit var editAge: EditText
private lateinit var button: Button
private lateinit var editQuantity: EditText
private lateinit var autoExercise: AutoCompleteTextView

class WaterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_water)
        editWeight = findViewById(R.id.water_weight)
        editAge = findViewById(R.id.water_age)
        editQuantity = findViewById(R.id.water_quantity)
        button = findViewById(R.id.water_button)

        val arrowBackButton: ImageButton = findViewById(R.id.arrow_refs_water)
        arrowBackButton.setOnClickListener {
            finish()
        }

        autoExercise = findViewById(R.id.auto_exercise)
        val arrayExercise: Array<String> = resources.getStringArray(R.array.exercise_frequency)
        autoExercise.setText(arrayExercise.first())
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1,arrayExercise)
        autoExercise.setAdapter(adapter)

        button.setOnClickListener {
            if(!validate()){
                Toast.makeText(this,R.string.toast_invalid_info,Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val weight = editWeight.text.toString().toInt()
            val age = editAge.text.toString().toInt()
            val quantity = editQuantity.text.toString().toDouble()
            val idealQuantityWater = calculateIdealQuantityWater(age,weight) + quantityByExercise(arrayExercise)
            val idealQuantityWaterL: Double = idealQuantityWater/1000.0

            @StringRes
            val titleType: Int =
                if (quantity > idealQuantityWater) R.string.dialog_water_title_above
                else R.string.dialog_water_title_below

            AlertDialog.Builder(this).apply {
                setTitle(titleType)
                setMessage(getString(R.string.dialog_water_message,idealQuantityWater,idealQuantityWaterL))
                setPositiveButton(R.string.ok){_,_->}
                create()
                show()
            }
        }
    }

    private fun validate(): Boolean{
        return (editWeight.text.toString().isNotEmpty() &&
                !editWeight.text.toString().startsWith("0") &&
                editAge.text.toString().isNotEmpty() &&
                !editAge.text.toString().startsWith("0"))
    }

    private fun calculateIdealQuantityWater(age: Int, weight: Int): Int{
        return when(age){
            in 0 .. 17 -> (40 * weight)
            in 18 .. 55 -> (35 * weight)
            in 56 .. 65 -> (30 * weight)
            else -> (25 * weight)
        }
    }

    private fun quantityByExercise(array: Array<String>): Int{
        return when(autoExercise.text.toString()){
            array[0] -> 0
            array[1] -> 325
            array[2] -> 750
            array[3] -> 1075
            array[4] -> 1500
            else -> 2250
        }
    }
}