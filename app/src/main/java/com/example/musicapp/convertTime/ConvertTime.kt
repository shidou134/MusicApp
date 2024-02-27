package com.example.musicapp.convertTime

object ConvertTime {
    fun convertToTime(duration: Long): String {
        val minutes = duration / 60
        val seconds = duration % 60

        return String.format("%02d:%02d", minutes, seconds)
    }
}