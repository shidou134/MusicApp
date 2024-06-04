package com.example.musicapp.modelresponse.artist


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ArtistResponse(
    @SerialName("data")
    val `data`: List<Data?>?,
    @SerialName("next")
    val next: String?,
    @SerialName("total")
    val total: Int?
)