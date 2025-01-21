package com.example.healthtracker.feature.imc.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.*
import androidx.appcompat.app.AlertDialog
import com.example.healthtracker.App
import com.example.healthtracker.R
import com.example.healthtracker.feature.imc.Imc
import com.example.healthtracker.feature.imc.presentation.ImcPresenter
import com.example.healthtracker.feature.listCalc.view.ListCalcActivity

class ImcActivity : AppCompatActivity(), Imc.View {

    lateinit var presenter: Imc.Presenter

    private lateinit var editHeight: EditText
    private lateinit var editWeight: EditText
    private lateinit var buttonResult: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_imc)
        editHeight = findViewById(R.id.imc_height)
        editWeight = findViewById(R.id.imc_weight)
        buttonResult = findViewById(R.id.imc_button)

        presenter = ImcPresenter(this)
        val app = application as App
        val dao = app.db.calcDao()

        val arrowBackButton: ImageButton = findViewById(R.id.arrow_refs_imc)
        arrowBackButton.setOnClickListener {
            finish()
        }

        val historyButton: ImageButton = findViewById(R.id.historic_refs_imc)
        historyButton.setOnClickListener {
            onRegisterImcValue()
        }

        buttonResult.setOnClickListener {

            if(!presenter.validate(editHeight.text.toString(), editWeight.text.toString())){
                displayFailure(getString(R.string.toast_invalid_info))
                return@setOnClickListener
            }
            val height = editHeight.text.toString().toDouble()
            val weight = editWeight.text.toString().toDouble()
            val imcResult = presenter.calculateImc(height.toInt(),weight.toInt())

            AlertDialog.Builder(this).apply {
                setTitle(getString(R.string.dialog_imc_title,imcResult))
                setMessage(presenter.getImcSituation(imcResult))
                setPositiveButton(R.string.ok){ _, _ ->

                }
                setNegativeButton(R.string.save){ _, _ ->
                    presenter.registerImcValue(imcResult, dao)
                }
                create()
                show()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.menu_item_search){
            onRegisterImcValue()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onRegisterImcValue() {
        startActivity(
            Intent(this, ListCalcActivity::class.java).putExtra("type","imc")
        )
    }

    override fun displayFailure(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        presenter.onDestroy()
        super.onDestroy()
    }

}