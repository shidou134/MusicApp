package com.example.musicapp.ultis

import java.util.concurrent.TimeUnit

object ConvertTime {
    fun convertToTime(duration: Long): String {
        val minutes = duration / 60
        val seconds = duration % 60

        return String.format("%02d:%02d", minutes, seconds)
    }
    fun convertTimestamp(timestamp: Long): String {
        return String.format(
            "%02d:%02d",
            TimeUnit.MILLISECONDS.toMinutes(timestamp) % TimeUnit.HOURS.toMinutes(1),
            TimeUnit.MILLISECONDS.toSeconds(timestamp) % TimeUnit.MINUTES.toSeconds(1)
        )
    }
}
