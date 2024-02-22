package com.example.musicapp.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class Song(
    @StringRes val artist: Int,
    @StringRes val name: Int,
    @DrawableRes val image: Int,
    val duration: String,
    val url: String
){
    private fun convertToMMSS(): String {
        val parts = duration.split(":")
        var minutes = 0L
        var seconds = 0L
        if (parts.size == 2) {
            minutes = parts[0].toLong()
            seconds = parts[1].toLong()
        }
        return String.format("%02d:%02d", minutes, seconds)
    }
}
