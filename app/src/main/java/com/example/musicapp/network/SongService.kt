package com.example.musicapp.network

import com.example.musicapp.modelresponse.album.AlbumItem
import com.example.musicapp.modelresponse.genre.GenreItem
import com.example.musicapp.modelresponse.playlist.PlaylistItem
import com.example.musicapp.modelresponse.song.SongItem
import com.example.musicapp.modelresponse.topic.TopicItem
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST


interface SongService {


//    @GET("search/artist")
//    suspend fun getArtists(@Query("q") query: String): ArtistResponse
//
//    @GET("artist/{id}/top?limit=50")
//    suspend fun getTop50Songs(@Path("id") id: Long): TopSongModel
//
//    @GET("track/{id}")
//    suspend fun getTrack(@Path("id") id: Long): Track
//
//    @GET("radio/lists")
//    suspend fun getRadios():RadioModel
//
//    @GET("radio/{id}/tracks")
//    suspend fun getRadioTracks(@Path("id") id: Long):RadioSongsModel

    @GET("listTopic.php")
    suspend fun getTopics(): List<TopicItem>

    @GET("playlistForToday.php")
    suspend fun getPlaylists(): List<PlaylistItem>

    @GET("favouriteSong.php")
    suspend fun getFavouriteSongs(): List<SongItem>

    @GET("album.php")
    suspend fun getAlbums(): List<AlbumItem>

    @FormUrlEncoded
    @POST("listGenre.php")
    suspend fun getGenre(@retrofit2.http.Field("idTopic") idTopic: String): List<GenreItem>


}