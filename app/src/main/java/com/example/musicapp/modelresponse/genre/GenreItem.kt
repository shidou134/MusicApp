package com.example.musicapp.modelresponse.genre


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GenreItem(
    @SerialName("genreImg")
    val genreImg: String?,
    @SerialName("genreName")
    val genreName: String?,
    @SerialName("idGenre")
    val idGenre: String?,
    @SerialName("idTopic")
    val idTopic: String?
)