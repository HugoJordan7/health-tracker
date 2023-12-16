package com.example.healthtracker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {

    var listItems = mutableListOf<MainItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        listItems.add(
            MainItem(
                id = 1,
                text = R.string.app_name,
                color = R.color.teal_200,
                icon = R.drawable.ic_baseline_accessibility_new_24
            )
        )
        listItems.add(
            MainItem(
                id = 1,
                text = R.string.app_name,
                color = R.color.teal_200,
                icon = R.drawable.ic_baseline_accessibility_new_24
            )
        )
    }
}