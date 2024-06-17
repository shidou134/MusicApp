package com.example.musicapp.data.followedartistrepo

import com.example.musicapp.modelresponse.artist.ArtistItem

interface FollowedArtistRepository {
    suspend fun followedArtist(artist: ArtistItem)
    suspend fun getFollowedArtist():List<ArtistItem>
    suspend fun deleteArtist(artist: ArtistItem)
}