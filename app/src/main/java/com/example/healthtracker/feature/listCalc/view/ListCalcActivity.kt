package com.example.healthtracker.feature.listCalc.view

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.healthtracker.App
import com.example.healthtracker.R
import com.example.healthtracker.feature.listCalc.ListCalc
import com.example.healthtracker.feature.listCalc.presentation.ListCalcPresenter
import com.example.healthtracker.model.Calc

class ListCalcActivity : AppCompatActivity(), ListCalc.View {

    override lateinit var presenter: ListCalc.Presenter
    private lateinit var adapter: ListCalcAdapter
    private lateinit var type: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_calc)
        presenter = ListCalcPresenter(this)
        val app = application as App
        val dao = app.db.calcDao()
        type = intent?.extras?.getString("type") ?: throw Exception("The type is not specified")

        presenter.getAllRegisters(dao, type)

        val arrowBackButton: ImageButton = findViewById(R.id.arrow_refs_history)
        arrowBackButton.setOnClickListener { finish() }

        val clearHistoryButton: ImageButton = findViewById(R.id.delete_history_button)
        clearHistoryButton.setOnClickListener {
            AlertDialog.Builder(this).apply {
                setTitle(R.string.dialog_title_delete_history)
                setPositiveButton(R.string.yes) { _, _ ->
                    presenter.clearRegisters(adapter.list, dao, type)
                }
                setNegativeButton(R.string.back) { _, _ -> }
                create()
                show()
            }
        }
        adapter = ListCalcAdapter(this)
        val rvListCalc: RecyclerView = findViewById(R.id.rv_list_calc)
        rvListCalc.adapter = adapter
        rvListCalc.layoutManager = LinearLayoutManager(this@ListCalcActivity)
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun displayAllRegisters(list: List<Calc>) {
        adapter.list = list
        adapter.notifyDataSetChanged()
    }

    override fun displayFailure(message: String) {
        Toast.makeText(this@ListCalcActivity, R.string.toast_delete_history_error, Toast.LENGTH_LONG).show()
    }

    override fun onDeleteRegisters() {
        Toast.makeText(this@ListCalcActivity,R.string.toast_delete_history,Toast.LENGTH_LONG).show()
        displayAllRegisters(emptyList())
        finish()
    }

    override fun onDestroy() {
        presenter.onDestroy()
        super.onDestroy()
    }

}