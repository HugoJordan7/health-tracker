package com.example.healthtracker.feature.water.view

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.healthtracker.R
import com.example.healthtracker.feature.water.Water
import com.example.healthtracker.feature.water.presentation.WaterPresenter

class WaterActivity : AppCompatActivity(), Water.View {

    lateinit var presenter: Water.Presenter
    private lateinit var editWeight: EditText
    private lateinit var editAge: EditText
    private lateinit var button: Button
    private lateinit var editQuantity: EditText
    private lateinit var autoExercise: AutoCompleteTextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_water)
        presenter = WaterPresenter(this)
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
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, arrayExercise)
        autoExercise.setAdapter(adapter)

        button.setOnClickListener {

            if(!presenter.validate(editWeight.text.toString(), editAge.text.toString())){
                displayFailure(getString(R.string.toast_invalid_info))
                return@setOnClickListener
            }

            val weight = editWeight.text.toString().toInt()
            val age = editAge.text.toString().toInt()
            val quantity = editQuantity.text.toString().toDouble()
            val idealQuantityWater =
                presenter.calculateIdealQuantityWater(age,weight) + presenter.quantityByExercise(autoExercise.text.toString(), arrayExercise)
            val idealQuantityWaterL: Double = idealQuantityWater/1000.0

            @StringRes
            val titleType: Int =
                if (quantity > idealQuantityWater) R.string.dialog_water_title_above
                else R.string.dialog_water_title_below

            AlertDialog.Builder(this).apply {
                setTitle(titleType)
                setMessage(getString(R.string.dialog_water_message,idealQuantityWater,idealQuantityWaterL))
                setPositiveButton(R.string.ok){ _, _->}
                create()
                show()
            }
        }
    }

    override fun displayFailure(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        presenter.onDestroy()
        super.onDestroy()
    }

}