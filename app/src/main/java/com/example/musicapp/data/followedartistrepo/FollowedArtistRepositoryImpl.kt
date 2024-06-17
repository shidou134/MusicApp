package com.example.musicapp.data.followedartistrepo

import com.example.musicapp.modelresponse.artist.ArtistItem
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class FollowedArtistRepositoryImpl : FollowedArtistRepository {
    private val db = FirebaseFirestore.getInstance()

    override suspend fun followedArtist(artist: ArtistItem) {
        db.collection("listFollowedArtist")
            .document(artist.idArtist ?: "")
            .set(artist)
    }

    override suspend fun getFollowedArtist(): List<ArtistItem> {
        val snapshot = db.collection("listFollowedArtist").get().await()
        return snapshot.documents.mapNotNull { document ->
            document.toObject(ArtistItem::class.java)
        }
    }

    override suspend fun deleteArtist(artist: ArtistItem) {
        val idArtist = artist.idArtist
        if (!idArtist.isNullOrEmpty()) {
            db.collection("listFollowedArtist")
                .document(idArtist)
                .delete()
                .await()

        }
    }
}