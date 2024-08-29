package com.example.musicapp.data.myplaylistrepo

import com.example.musicapp.modelresponse.song.SongItem
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class MyPlaylistRepositoryImpl:MyPlaylistRepository {
    private val db = FirebaseFirestore.getInstance()

    override suspend fun song(song: SongItem) {
        db.collection("myPlaylist")
            .document(song.idSong ?: "")
            .set(song)
    }

    override suspend fun getMyPlaylist(): List<SongItem> {
        val snapshot = db.collection("myPlaylist").get().await()
        return snapshot.documents.mapNotNull { document ->
            document.toObject(SongItem::class.java)
        }
    }

    override suspend fun deleteMyPlaylist(song: SongItem) {
        val idSong = song.idSong
        if (!idSong.isNullOrEmpty()) {
            db.collection("myPlaylist")
                .document(idSong)
                .delete()
                .await()

        }
    }
}