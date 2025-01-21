package com.example.healthtracker.feature.tmb.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.*
import androidx.appcompat.app.AlertDialog
import com.example.healthtracker.App
import com.example.healthtracker.R
import com.example.healthtracker.feature.listCalc.view.ListCalcActivity
import com.example.healthtracker.feature.tmb.Tmb
import com.example.healthtracker.feature.tmb.presentation.TmbPresenter

class TmbActivity : AppCompatActivity(), Tmb.View {

    override lateinit var presenter: Tmb.Presenter
    private lateinit var editHeight: EditText
    private lateinit var editWeight: EditText
    private lateinit var editAge: EditText
    private lateinit var buttonResult: Button
    private lateinit var autoLifestyle: AutoCompleteTextView
    private lateinit var radioButtonMasculine: RadioButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tmb)
        presenter = TmbPresenter(this)
        editHeight = findViewById(R.id.tmb_height)
        editWeight = findViewById(R.id.tmb_weight)
        editAge = findViewById(R.id.tmb_age)
        buttonResult = findViewById(R.id.tmb_button)
        autoLifestyle = findViewById(R.id.auto_lifestyle)
        radioButtonMasculine = findViewById(R.id.radio_button_masculine_tmb)
        radioButtonMasculine.isChecked = true

        val app = application as App
        val dao = app.db.calcDao()

        val arrowBackButton: ImageButton = findViewById(R.id.arrow_refs_tmb)
        arrowBackButton.setOnClickListener {
            finish()
        }

        val historyButton: ImageButton = findViewById(R.id.historic_refs_tmb)
        historyButton.setOnClickListener {
            onRegisterTmbValue()
        }

        val items = resources.getStringArray(R.array.lifestye_tmb)
        autoLifestyle.setText(items.first())
        val adapter = ArrayAdapter(this,android.R.layout.simple_list_item_1,items)
        autoLifestyle.setAdapter(adapter)

        buttonResult.setOnClickListener {

            if(!presenter.validate(editHeight.text.toString(),editWeight.text.toString(),editAge.text.toString())){
                displayFailure(getString(R.string.toast_invalid_info))
                return@setOnClickListener
            }

            val height = editHeight.text.toString().toInt()
            val weight = editWeight.text.toString().toInt()
            val age = editAge.text.toString().toInt()
            val tmb = presenter.calculateTmb(radioButtonMasculine.isChecked, height, weight, age)
            val tmbAdapted = presenter.tmbAdaptedForLifestyle(autoLifestyle.text.toString(), tmb, items)

            AlertDialog.Builder(this).apply {
                setTitle(getString(R.string.dialog_tmb_title,tmbAdapted))
                setPositiveButton(R.string.ok){ _, _ ->

                }
                setNegativeButton(R.string.save){ _, _ ->
                    presenter.registerTmbValue(tmbAdapted, dao)
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
            onRegisterTmbValue()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onRegisterTmbValue() {
        startActivity(
            Intent(this, ListCalcActivity::class.java).putExtra("type","tmb")
        )
    }

    override fun displayFailure(message: String) {
        Toast.makeText(this, message,Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        presenter.onDestroy()
        super.onDestroy()
    }

}