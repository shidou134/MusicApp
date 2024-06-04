package com.example.musicapp.data

import com.example.musicapp.modelresponse.artist.ArtistResponse
import com.example.musicapp.modelresponse.radio.RadioModel
import com.example.musicapp.modelresponse.radiosong.RadioSongsModel
import com.example.musicapp.modelresponse.top50song.TopSongModel
import com.example.musicapp.modelresponse.track.Track
import com.example.musicapp.network.SongService

interface SongRepository {

    suspend fun getArtist(): ArtistResponse
    suspend fun getTopSongs(id: Long): TopSongModel
    suspend fun getTrack(id: Long): Track
    suspend fun getRadios(): RadioModel
    suspend fun getRadioTracks(id: Long): RadioSongsModel
    suspend fun getTopRadio():RadioModel
}

class NetworkSongRepository(
    private val song: SongService
) : SongRepository {
    override suspend fun getArtist(): ArtistResponse = song.getArtists("a")
    override suspend fun getTopSongs(id: Long): TopSongModel = song.getTop50Songs(id)
    override suspend fun getTrack(id: Long): Track = song.getTrack(id)
    override suspend fun getRadios(): RadioModel = song.getRadios()
    override suspend fun getRadioTracks(id: Long): RadioSongsModel = song.getRadioTracks(id)
    override suspend fun getTopRadio(): RadioModel = song.getTopRadios()
}