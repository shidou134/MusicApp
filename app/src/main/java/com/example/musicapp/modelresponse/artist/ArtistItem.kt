package com.example.musicapp.modelresponse.artist

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ArtistItem(
    @SerialName("idArtist")
    val idArtist: String?,
    @SerialName("artistName")
    val artistName: String?,
    @SerialName("artistImg")
    val artistImg: String?,
)
