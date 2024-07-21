package com.example.healthtracker.feature.main.view

import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class MainItem(
    val id: Int,
    @StringRes val text: Int,
    @DrawableRes val icon: Int
)
