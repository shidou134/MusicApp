package com.example.musicapp.data.historyrepo

import com.example.musicapp.modelresponse.song.SongItem

interface HistoryRepository {
    suspend fun addHistory(song:SongItem)
    suspend fun getHistory():List<SongItem>
}