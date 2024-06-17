package com.example.musicapp.data.favouritesongrepo

import com.example.musicapp.modelresponse.song.SongItem
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class FavouriteRepositoryImpl : FavoriteRepository {
    private val db = FirebaseFirestore.getInstance()

    override suspend fun likedSong(song: SongItem) {
        db.collection("listFavoriteSongs")
            .document(song.idSong ?: "")
            .set(song)
    }

    override suspend fun getSongFavorite(): List<SongItem> {
        val snapshot = db.collection("listFavoriteSongs").get().await()
        return snapshot.documents.mapNotNull { document ->
            document.toObject(SongItem::class.java)
        }
    }

    override suspend fun deleteSongFavorite(song: SongItem) {
        val idSong = song.idSong
        if (!idSong.isNullOrEmpty()) {
            db.collection("listFavoriteSongs")
                .document(idSong)
                .delete()
                .await()

        }
    }
}