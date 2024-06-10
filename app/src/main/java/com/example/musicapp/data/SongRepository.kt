package com.example.musicapp.data

import com.example.musicapp.modelresponse.album.AlbumItem
import com.example.musicapp.modelresponse.genre.GenreItem
import com.example.musicapp.modelresponse.playlist.PlaylistItem
import com.example.musicapp.modelresponse.song.SongItem
import com.example.musicapp.modelresponse.topic.TopicItem
import com.example.musicapp.network.SongService

interface SongRepository {
    suspend fun getTopics(): List<TopicItem>
    suspend fun getPlaylists(): List<PlaylistItem>
    suspend fun getTopFavouriteSongs(): List<SongItem>
    suspend fun getAlbums(): List<AlbumItem>
    suspend fun getGenres(idTopic: String): List<GenreItem>
    suspend fun getSongByGenre(idGenre: String): List<SongItem>
}

class NetworkSongRepository(
    private val song: SongService
) : SongRepository {
    override suspend fun getTopics(): List<TopicItem> = song.getTopics()
    override suspend fun getPlaylists(): List<PlaylistItem> = song.getPlaylists()
    override suspend fun getTopFavouriteSongs(): List<SongItem> = song.getFavouriteSongs()
    override suspend fun getAlbums(): List<AlbumItem> = song.getAlbums()
    override suspend fun getGenres(idTopic: String): List<GenreItem> = song.getGenre(idTopic)
    override suspend fun getSongByGenre(idGenre: String): List<SongItem> = song.getSongByGenre(idGenre)
}