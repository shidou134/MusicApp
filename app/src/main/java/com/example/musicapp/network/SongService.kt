package com.example.musicapp.network

import com.example.musicapp.modelresponse.advertise.AdvertiseItem
import com.example.musicapp.modelresponse.album.AlbumItem
import com.example.musicapp.modelresponse.artist.ArtistItem
import com.example.musicapp.modelresponse.genre.GenreItem
import com.example.musicapp.modelresponse.playlist.PlaylistItem
import com.example.musicapp.modelresponse.song.SongItem
import com.example.musicapp.modelresponse.topic.TopicItem
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST


interface SongService {

    @GET("listTopic.php")
    suspend fun getTopics(): List<TopicItem>

    @GET("playlistForToday.php")
    suspend fun getPlaylists(): List<PlaylistItem>

    @GET("favouriteSong.php")
    suspend fun getFavouriteSongs(): List<SongItem>

    @GET("album.php")
    suspend fun getAlbums(): List<AlbumItem>

    @GET("listArtist.php")
    suspend fun getArtist(): List<ArtistItem>

    @GET("songbanner.php")
    suspend fun getSongBanner(): List<AdvertiseItem>

    @FormUrlEncoded
    @POST("listGenre.php")
    suspend fun getGenre(@retrofit2.http.Field("idTopic") idTopic: String): List<GenreItem>

    @FormUrlEncoded
    @POST("listSong.php")
    suspend fun getSongByGenre(@retrofit2.http.Field("idGenre") idGenre: String): List<SongItem>

    @FormUrlEncoded
    @POST("searchSong.php")
    suspend fun searchSong(@retrofit2.http.Field("index") index: String): List<SongItem>

    @FormUrlEncoded
    @POST("listSong.php")
    suspend fun getSongByPlaylist(@retrofit2.http.Field("idPlaylist") idPlaylist: String): List<SongItem>

    @FormUrlEncoded
    @POST("listSong.php")
    suspend fun getSongByAlbum(@retrofit2.http.Field("idAlbum") idAlbum: String): List<SongItem>

    @FormUrlEncoded
    @POST("listSong.php")
    suspend fun getSongByArtist(@retrofit2.http.Field("idArtist") idArtist: String): List<SongItem>


}