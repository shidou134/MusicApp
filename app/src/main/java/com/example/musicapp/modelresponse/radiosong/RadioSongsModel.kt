package com.example.musicapp.modelresponse.radiosong


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RadioSongsModel(
    @SerialName("data")
    val `data`: List<Data?>?
)