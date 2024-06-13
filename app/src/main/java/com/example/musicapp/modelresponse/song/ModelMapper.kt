package com.example.musicapp.modelresponse.song

object ModelMapper {
    fun SongItem.toSongFirebase(): SongFirebase {
        return SongFirebase(
            this.artistName,
            this.idSong,
            this.liked,
            this.songImg,
            this.songName,
            this.songUrl,
            this.duration
        )
    }
}