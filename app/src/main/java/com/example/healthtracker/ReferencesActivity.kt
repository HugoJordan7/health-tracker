package com.example.healthtracker

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.text.method.LinkMovementMethod
import android.widget.TextView

class ReferencesActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_references)
        var imcRef: TextView = findViewById(R.id.ref_imc)
        var tmbRef: TextView = findViewById(R.id.ref_tmb)
        var tgcRef: TextView = findViewById(R.id.ref_tgc)
        var waterRef: TextView = findViewById(R.id.ref_water)

        imcRef.text = Html.fromHtml(getString(R.string.imc_ref))
        tmbRef.text = Html.fromHtml(getString(R.string.tmb_ref))
        tgcRef.text = Html.fromHtml(getString(R.string.tgc_ref))
        waterRef.text = Html.fromHtml(getString(R.string.water_ref))

        imcRef.movementMethod = LinkMovementMethod.getInstance()
        tmbRef.movementMethod = LinkMovementMethod.getInstance()
        tgcRef.movementMethod = LinkMovementMethod.getInstance()
        waterRef.movementMethod = LinkMovementMethod.getInstance()
    }
}