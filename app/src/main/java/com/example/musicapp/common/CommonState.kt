package com.example.musicapp.common

import androidx.compose.runtime.mutableStateListOf
import com.example.musicapp.modelresponse.song.SongItem


data class CommonState(
    var type: Int = 0,
    var topicId: String = "",
    var genreId: String = "",
    var playlistId:String = "",
    var albumId:String = "",
    var artistId:String = "",
    var searchQuery:String = "",
    var listSong: List<SongItem> = mutableStateListOf(),
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
