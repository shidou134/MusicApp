package com.example.musicapp.ui.viewModel

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import com.example.musicapp.model.Song
import com.example.musicapp.model.SongUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class SongViewModel() : ViewModel() {

    private val _uiState = MutableStateFlow(SongUiState())
    var uiState: StateFlow<SongUiState> = _uiState.asStateFlow()

    private lateinit var player: ExoPlayer

    fun setMusicExoPlayer(context: Context) {
//    val context = LocalContext.current
        player = ExoPlayer.Builder(context).build()
        val mediaItem = MediaItem.fromUri(_uiState.value.currentSong!!.url)
        player.setMediaItem(mediaItem)
        player.prepare()
        player.play()
    }

    fun getCurrentPosition(): Long {
        return player.currentPosition
    }

//    fun getDuration(): Long {
//        _uiState.value.duration = player.duration
//        Log.d("shidou", "getDuration:${player.duration} ")
//        return _uiState.value.duration
//    }

    fun seekTo(position: Long) {
        player.seekTo(position)
    }

    fun setSong(song: Song) {
        _uiState.update {
            it.copy(
                currentSong = song,
                isSongPlaying = true,
                duration = song.duration
            )
        }
        Log.d("shidou", "setSong: ${song.duration}")
    }

    fun playSong() {
        player.play()
        _uiState.update {
            it.copy(isSongPlaying = true)
        }
    }

    fun pauseSong() {
        player.pause()
        _uiState.update {
            it.copy(isSongPlaying = false)
        }
    }

    fun playOrPauseSong() {
        _uiState.update {
            it.copy(isSongPlaying = !it.isSongPlaying)
        }
        if (_uiState.value.isSongPlaying) {
            player.play()
        } else {
            player.pause()
        }
    }

}

fun convertToMMSS(duration: String): String {
    val parts = duration.split(":")
    var minutes = 0L
    var seconds = 0L
    if (parts.size == 2) {
        minutes = parts[0].toLong()
        seconds = parts[1].toLong()
    }
    return String.format("%02d:%02d", minutes, seconds)
}


