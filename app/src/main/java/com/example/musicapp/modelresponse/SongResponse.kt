package com.example.musicapp.modelresponse


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SongResponse(
    @SerialName("data")
    val `data`: List<Data?>?,
    @SerialName("next")
    val next: String?,
    @SerialName("total")
    val total: Int?
)