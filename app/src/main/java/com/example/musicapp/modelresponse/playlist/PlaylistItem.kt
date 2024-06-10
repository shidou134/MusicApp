package com.example.musicapp.modelresponse.playlist


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PlaylistItem(
    @SerialName("icon")
    val icon: String?,
    @SerialName("idPlaylist")
    val idPlaylist: String?,
    @SerialName("img")
    val img: String?,
    @SerialName("name")
    val name: String?
)