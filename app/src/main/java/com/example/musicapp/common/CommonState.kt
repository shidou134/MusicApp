package com.example.musicapp.common


data class CommonState(
    var type: Int = 0,
    var topicId: String = "",
    var genreId: String = "",
    var radioId: Long = 0,
    var songId: Long = 0,
    val isSongPlaying: Boolean = false,
    val progress: Float = 0f,
    val isLooping: Boolean = false,
    val isShuffle: Boolean = false,
    val duration: Long = 0,
    val currentDuration: Long = 0,
    val sliderPosition: Int = 0,
    val isBound: Boolean = false,
)
