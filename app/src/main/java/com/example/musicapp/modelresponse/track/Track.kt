package com.example.musicapp.modelresponse.track


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Track(
    @SerialName("album")
    val album: Album?,
    @SerialName("artist")
    val artist: Artist?,
    @SerialName("available_countries")
    val availableCountries: List<String?>?,
    @SerialName("bpm")
    val bpm: Double?,
    @SerialName("contributors")
    val contributors: List<Contributor?>?,
    @SerialName("disk_number")
    val diskNumber: Int?,
    @SerialName("duration")
    val duration: Int?,
    @SerialName("explicit_content_cover")
    val explicitContentCover: Int?,
    @SerialName("explicit_content_lyrics")
    val explicitContentLyrics: Int?,
    @SerialName("explicit_lyrics")
    val explicitLyrics: Boolean?,
    @SerialName("gain")
    val gain: Double?,
    @SerialName("id")
    val id: Long?,
    @SerialName("isrc")
    val isrc: String?,
    @SerialName("link")
    val link: String?,
    @SerialName("md5_image")
    val md5Image: String?,
    @SerialName("preview")
    val preview: String?,
    @SerialName("rank")
    val rank: Int?,
    @SerialName("readable")
    val readable: Boolean?,
    @SerialName("release_date")
    val releaseDate: String?,
    @SerialName("share")
    val share: String?,
    @SerialName("title")
    val title: String?,
    @SerialName("title_short")
    val titleShort: String?,
    @SerialName("title_version")
    val titleVersion: String?,
    @SerialName("track_position")
    val trackPosition: Int?,
    @SerialName("type")
    val type: String?
)