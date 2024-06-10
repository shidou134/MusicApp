package com.example.musicapp.modelresponse.album


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AlbumItem(
    @SerialName("albumImg")
    val albumImg: String?,
    @SerialName("albumName")
    val albumName: String?,
    @SerialName("artistName")
    val artistName: String?,
    @SerialName("idAlbum")
    val idAlbum: String?
)