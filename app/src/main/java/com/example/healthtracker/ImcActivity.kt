package com.example.healthtracker

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.annotation.StringRes
import androidx.appcompat.app.AlertDialog
import com.example.healthtracker.model.AppDataBase
import com.example.healthtracker.model.Calc

class ImcActivity : AppCompatActivity() {

    private lateinit var editHeight: EditText
    private lateinit var editWeight: EditText
    private lateinit var buttonResult: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_imc)
        editHeight = findViewById(R.id.imc_height)
        editWeight = findViewById(R.id.imc_weight)
        buttonResult = findViewById(R.id.imc_button)

        val arrowBackButton: ImageButton = findViewById(R.id.arrow_refs_imc)
        arrowBackButton.setOnClickListener {
            finish()
        }

        val historyButton: ImageButton = findViewById(R.id.historic_refs_imc)
        historyButton.setOnClickListener {
            openListCalcActivity()
        }

        buttonResult.setOnClickListener {
            if(!validate(editHeight.text.toString(), editWeight.text.toString())){
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
                    Thread{
                        val app = application as App
                        val dao = app.db.calcDao()
                        dao.insert(Calc(type = "imc",res = imcResult))
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
            imc<18.5 -> R.string.imc_low_weight
            imc<25.0 -> R.string.imc_normal_weight
            imc<30.0 -> R.string.imc_so_above_weight
            imc<35.0 -> R.string.imc_above_1
            imc<40.0 -> R.string.imc_above_2
            else -> R.string.imc_above_3
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
            Intent(this,ListCalcActivity::class.java).putExtra("type","imc")
        )
    }

}