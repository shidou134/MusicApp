package com.example.musicapp.model

data class SongUiState(
    val isSongPlaying: Boolean = false,
    val progress: Float = 0f,
    val currentSong: Song? = null,
    val isLooping: Boolean = false,
    val isShuffle: Boolean = false,
    val duration: Int = 0,
    val currentDuration: Int = 0,
    val sliderPosition: Int = 0
)

