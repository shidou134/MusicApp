package com.example.musicapp.model

import android.accounts.Account

data class SongUiState(
    val isSongPlaying: Boolean = false,
    val progress: Float = 0f,
    val currentSong: Song? = null,
    val isLooping: Boolean = false,
    val isShuffle: Boolean = false,
    val duration: Long = 0,
    val currentDuration: Long = 0,
    val sliderPosition: Int = 0,
    val isBound:Boolean = false,
    val listAccount:List<Account> = emptyList()
)



