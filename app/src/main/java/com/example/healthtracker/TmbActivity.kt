package com.example.healthtracker

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.*
import androidx.appcompat.app.AlertDialog
import com.example.healthtracker.model.Calc

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
            val height = editHeight.text.toString().toInt()
            val weight = editWeight.text.toString().toInt()
            val age = editAge.text.toString().toInt()
            val tmb = calculateTmb(height, weight, age)
            val tmbAdapted = tmbAdaptedForLifestyle(tmb,items)
            AlertDialog.Builder(this).apply {
                setTitle(getString(R.string.dialog_tmb_title,tmbAdapted))
                setPositiveButton(R.string.ok){_,_ ->

                }
                setNegativeButton(R.string.save){_,_ ->
                    Thread{
                        val app = application as App
                        val dao = app.db.calcDao()
                        dao.insert(Calc(type = "tmb", res = tmbAdapted))
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

    private fun validate(height: String, weight: String, age: String): Boolean{
        return (height.isNotEmpty() && !height.startsWith("0") &&
                weight.isNotEmpty() && !weight.startsWith("0") &&
                age.isNotEmpty() && !age.startsWith("0") )
    }

    private fun calculateTmb(height: Int, weight: Int, age: Int): Double{
        return (66 + (13.8 * weight) + (5 * height) - (6.8 * age))
    }

    private fun tmbAdaptedForLifestyle(tmb: Double, arrayLifestyle: Array<String>): Double{
        return when(autoLifestyle.text.toString()){
            arrayLifestyle[0] -> tmb * 1.2
            arrayLifestyle[1] -> tmb * 1.375
            arrayLifestyle[2] -> tmb * 1.55
            arrayLifestyle[3] -> tmb * 1.725
            arrayLifestyle[4] -> tmb * 1.9
            else->0.0
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.menu_item_search){
            openListCalcActivity()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun openListCalcActivity() {
        startActivity(
            Intent(this,ListCalcActivity::class.java).putExtra("type","tmb")
        )
    }

}