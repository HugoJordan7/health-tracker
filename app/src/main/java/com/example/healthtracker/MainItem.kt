package com.example.healthtracker

import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class MainItem(
    val id: Int,
    @StringRes val text: Int,
    @DrawableRes val gradient: Int = R.drawable.red_gradient,
    @DrawableRes val icon: Int
)
