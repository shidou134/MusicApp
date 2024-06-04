package com.example.musicapp.ui.playingsong.state

import com.example.musicapp.modelresponse.top50song.Data

data class SongUiState(
    val isSongPlaying: Boolean = false,
    val progress: Float = 0f,
    val currentSong: Data? = null,
    val isLooping: Boolean = false,
    val isShuffle: Boolean = false,
    val duration: Long = 0,
    val currentDuration: Long = 0,
    val sliderPosition: Int = 0,
    val isBound: Boolean = false
)



