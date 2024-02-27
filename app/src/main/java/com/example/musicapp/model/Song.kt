package com.example.musicapp.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class Song(
    @StringRes val artist: Int,
    @StringRes val name: Int,
    @DrawableRes val image: Int,
    val duration: Long,
    val url: String
)
