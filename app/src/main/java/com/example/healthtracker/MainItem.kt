package com.example.healthtracker

import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class MainItem(
    val id: Int,
    @StringRes val text: Int,
    val color: Int,
    @DrawableRes val icon: Int
)
