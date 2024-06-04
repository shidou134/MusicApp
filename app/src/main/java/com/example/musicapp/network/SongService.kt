package com.example.musicapp.network

import com.example.musicapp.modelresponse.artist.ArtistResponse
import com.example.musicapp.modelresponse.radio.RadioModel
import com.example.musicapp.modelresponse.radiosong.RadioSongsModel
import com.example.musicapp.modelresponse.top50song.TopSongModel
import com.example.musicapp.modelresponse.track.Track
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface SongService {

    @Headers(
        "x-rapidapi-key: 9fd05c1334msh4d91ff91972bcd8p19a58ajsnf49982604ed0",
        "x-rapidapi-host: deezerdevs-deezer.p.rapidapi.com"
    )
    @GET("search/artist")
    suspend fun getArtists(@Query("q") query: String): ArtistResponse

    //    @Headers(
//        "x-rapidapi-key: 9fd05c1334msh4d91ff91972bcd8p19a58ajsnf49982604ed0",
//        "x-rapidapi-host: deezerdevs-deezer.p.rapidapi.com"
//    )
    @GET("artist/{id}/top?limit=50")
    suspend fun getTop50Songs(@Path("id") id: Long): TopSongModel

    @GET("track/{id}")
    suspend fun getTrack(@Path("id") id: Long): Track

    @GET("radio/lists")
    suspend fun getRadios():RadioModel

    @GET("radio/{id}/tracks")
    suspend fun getRadioTracks(@Path("id") id: Long):RadioSongsModel

    @GET("radio/top")
    suspend fun getTopRadios():RadioModel

}