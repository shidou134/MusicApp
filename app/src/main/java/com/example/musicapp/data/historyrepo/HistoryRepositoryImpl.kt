package com.example.musicapp.data.historyrepo

import com.example.musicapp.modelresponse.song.SongItem
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class HistoryRepositoryImpl : HistoryRepository {
    private val db = FirebaseFirestore.getInstance()
    override suspend fun addHistory(song: SongItem) {
        db.collection("History")
            .document(song.idSong ?: "")
            .set(song)
    }

    override suspend fun getHistory(): List<SongItem> {
        val snapshot = db.collection("History").get().await()
        return snapshot.documents.mapNotNull { document ->
            document.toObject(SongItem::class.java)
        }
    }
}