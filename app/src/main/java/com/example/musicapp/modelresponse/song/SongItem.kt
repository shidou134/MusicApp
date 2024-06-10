package com.example.musicapp.modelresponse.song


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SongItem(
    @SerialName("artistName")
    val artistName: String?,
    @SerialName("idSong")
    val idSong: String?,
    @SerialName("liked")
    val liked: String?,
    @SerialName("songImg")
    val songImg: String?,
    @SerialName("songName")
    val songName: String?,
    @SerialName("songUrl")
    val songUrl: String?
)