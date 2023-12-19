package com.example.healthtracker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*

class TmbActivity : AppCompatActivity() {

    lateinit var editHeight: EditText
    lateinit var editWeight: EditText
    lateinit var editAge: EditText
    lateinit var buttonResult: Button
    lateinit var autoLifestyle: AutoCompleteTextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tmb)
        editHeight = findViewById(R.id.tmb_height)
        editWeight = findViewById(R.id.tmb_weight)
        editAge = findViewById(R.id.tmb_age)
        buttonResult = findViewById(R.id.tmb_button)
        autoLifestyle = findViewById(R.id.auto_lifestyle)
        val items = resources.getStringArray(R.array.lifestye_tmb)
        autoLifestyle.setText(items.first())
        val adapter = ArrayAdapter(this,android.R.layout.simple_list_item_1,items)
        autoLifestyle.setAdapter(adapter)

        buttonResult.setOnClickListener {
            if(!validate(editHeight.text.toString(),editWeight.text.toString(),editAge.text.toString())){
                Toast.makeText(this,R.string.toast_invalid_info,Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val height = editHeight.text.toString().toDouble()
            val weight = editWeight.text.toString().toDouble()
            val age = editAge.text.toString().toInt()


        }
    }

    private fun validate(height: String, weight: String, age: String): Boolean{
        return (height.isNotEmpty() && !height.startsWith("0") &&
                weight.isNotEmpty() && !weight.startsWith("0") &&
                age.isNotEmpty() && !age.startsWith("0") )
    }
}