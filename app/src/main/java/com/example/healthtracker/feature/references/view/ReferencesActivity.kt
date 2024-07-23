package com.example.healthtracker.feature.references.view

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.text.method.LinkMovementMethod
import android.widget.ImageButton
import android.widget.TextView
import com.example.healthtracker.R

class ReferencesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_references)

        val arrowBackButton: ImageButton = findViewById(R.id.arrow_refs)
        arrowBackButton.setOnClickListener {
            finish()
        }

    }

}