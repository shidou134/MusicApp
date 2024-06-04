package com.example.musicapp.modelresponse.track


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Album(
    @SerialName("cover")
    val cover: String?,
    @SerialName("cover_big")
    val coverBig: String?,
    @SerialName("cover_medium")
    val coverMedium: String?,
    @SerialName("cover_small")
    val coverSmall: String?,
    @SerialName("cover_xl")
    val coverXl: String?,
    @SerialName("id")
    val id: Long?,
    @SerialName("link")
    val link: String?,
    @SerialName("md5_image")
    val md5Image: String?,
    @SerialName("release_date")
    val releaseDate: String?,
    @SerialName("title")
    val title: String?,
    @SerialName("tracklist")
    val tracklist: String?,
    @SerialName("type")
    val type: String?
)