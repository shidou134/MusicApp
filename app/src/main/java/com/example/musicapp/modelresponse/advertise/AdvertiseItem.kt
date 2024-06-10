package com.example.musicapp.modelresponse.advertise


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AdvertiseItem(
    @SerialName("adContent")
    val adContent: String?,
    @SerialName("adImg")
    val adImg: String?,
    @SerialName("id")
    val id: String?,
    @SerialName("idSong")
    val idSong: String?,
    @SerialName("songImg")
    val songImg: String?,
    @SerialName("songName")
    val songName: String?
)