package com.example.musicapp.common

import com.example.musicapp.modelresponse.top50song.Data

data class CommonState(
    var type: Int = 0,
    var artistId: Long = 0,
    var radioId: Long = 0,
    var songId: Long = 0,
    var currentSong: Data? = null,
    val isSongPlaying: Boolean = false,
    val progress: Float = 0f,
    val isLooping: Boolean = false,
    val isShuffle: Boolean = false,
    val duration: Long = 0,
    val currentDuration: Long = 0,
    val sliderPosition: Int = 0,
    val isBound: Boolean = false,
    var listSong: MutableList<Data> = mutableListOf()
)
