package com.example.musicapp.ui.brower

import androidx.annotation.DrawableRes

data class Album(
    val title: String,
    val singer: String,
    @DrawableRes val image: Int
)
