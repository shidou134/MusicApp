package com.example.musicapp.data

import com.example.musicapp.modelresponse.song.SongItem
import com.google.firebase.firestore.FirebaseFirestore
import okhttp3.internal.wait

class FavouriteRepositoryImpl : FavoriteRepository {
    private val db = FirebaseFirestore.getInstance()
    private lateinit var listSong: ArrayList<SongItem>

    override suspend fun likedSong(song: SongItem) {
        val value = HashMap<String, SongItem>()
        value["Song"] = song
        db.collection("listFavoriteSongs")
            .document(song.idSong ?: "")
            .set(value)
    }

    override suspend fun getSongFavorite(): List<SongItem> {
        listSong = mutableListOf<SongItem>() as ArrayList<SongItem>
        db.collection("listFavoriteSongs")
            .get()
            .addOnSuccessListener { result ->
                for (song in result) {
                    val songs = song.toObject(SongItem::class.java)
                    listSong.add(songs)
                }
            }
        return listSong.toList()
    }

    override suspend fun deleteSongFavorite(song: SongItem) {

    }
}