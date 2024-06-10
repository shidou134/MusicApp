package com.example.musicapp.data

interface FavouriteRepository {
    suspend fun setLikedSong()
}