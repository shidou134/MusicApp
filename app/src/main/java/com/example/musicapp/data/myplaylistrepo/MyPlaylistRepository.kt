package com.example.musicapp.data.myplaylistrepo

import com.example.musicapp.modelresponse.artist.ArtistItem
import com.example.musicapp.modelresponse.song.SongItem

interface MyPlaylistRepository {
    suspend fun song(song: SongItem)
    suspend fun getMyPlaylist():List<SongItem>
    suspend fun deleteMyPlaylist(song: SongItem)
}