package com.example.healthtracker.feature.main.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.healthtracker.feature.heartRate.view.HeartRateActivity
import com.example.healthtracker.feature.imc.view.ImcActivity
import com.example.healthtracker.R
import com.example.healthtracker.feature.references.view.ReferencesActivity
import com.example.healthtracker.feature.tmb.view.TmbActivity
import com.example.healthtracker.feature.water.view.WaterActivity

class MainActivity : AppCompatActivity() {

    private var listItems = mutableListOf<MainItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val infoButton: ImageButton = findViewById(R.id.info_button)

        infoButton.setOnClickListener {
            startActivity(Intent(this, ReferencesActivity::class.java))
        }

        listItems.apply {
            add(
                MainItem(
                    id = 0,
                    text = R.string.imc,
                    icon = R.drawable.conditions
                )
            )
            add(
                MainItem(
                    id = 1,
                    text = R.string.tmb,
                    icon = R.drawable.fire
                )
            )
            add(
                MainItem(
                    id = 2,
                    text = R.string.bpm,
                    icon = R.drawable.heart_rate
                )
            )
            add(
                MainItem(
                    id = 3,
                    text = R.string.water,
                    icon = R.drawable.water
                )
            )
        }

        val mainAdapter = MainAdapter(listItems){ screenId ->
            when (screenId) {
                0 -> startActivity(Intent(this, ImcActivity::class.java))
                1 -> startActivity(Intent(this, TmbActivity::class.java))
                2 -> startActivity(Intent(this, HeartRateActivity::class.java))
                3 -> startActivity(Intent(this, WaterActivity::class.java))
            }
        }
        val recyclerViewMain: RecyclerView = findViewById(R.id.main_rv)
        recyclerViewMain.adapter = mainAdapter
        recyclerViewMain.layoutManager = GridLayoutManager(this, 2)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.main_refs -> startActivity(Intent(this, ReferencesActivity::class.java))
        }
        return super.onOptionsItemSelected(item)
    }

}