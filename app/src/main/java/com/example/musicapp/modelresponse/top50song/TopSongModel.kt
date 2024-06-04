package com.example.musicapp.modelresponse.top50song


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TopSongModel(
    @SerialName("data")
    val `data`: List<Data?>? = emptyList(),
    @SerialName("total")
    val total: Int? = 0
)