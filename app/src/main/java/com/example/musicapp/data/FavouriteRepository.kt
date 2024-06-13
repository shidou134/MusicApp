package com.example.musicapp.data

import com.example.musicapp.modelresponse.song.SongItem

interface FavoriteRepository {

    suspend fun likedSong(song: SongItem)
    suspend fun getSongFavorite():List<SongItem>
    suspend fun deleteSongFavorite(song: SongItem)
}