package com.example.musicapp.ui.viewModel

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.ViewModel
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import com.example.musicapp.data.DataSource
import com.example.musicapp.model.Song
import com.example.musicapp.model.SongUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlin.math.log

class SongViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(SongUiState())
    var uiState: StateFlow<SongUiState> = _uiState.asStateFlow()

    private lateinit var player: ExoPlayer
    private lateinit var runnable: Runnable
    private var handler = Handler(Looper.getMainLooper())

    fun setMusicExoPlayer(context: Context) {
//    val context = LocalContext.current
        player = ExoPlayer.Builder(context).build()
        DataSource.songs.forEach { song ->
            val mediaItem = MediaItem.fromUri(song.url)
            player.addMediaItem(mediaItem)
        }
        player.prepare()
        player.play()

    }

    fun getCurrentPosition() {
        runnable = Runnable {
            _uiState.update {
                it.copy(
                    currentDuration = player.currentPosition
                )
            }
            Log.d("shidou", "getCurrentPosition: ${_uiState.value.currentDuration}")
            handler.postDelayed(runnable, 100)
        }
        handler.postDelayed(runnable, 100)

    }

//    fun getDuration(): Long {
//        _uiState.value.duration = player.duration
//        Log.d("shidou", "getDuration:${player.duration} ")
//        return _uiState.value.duration
//    }

    fun seekTo(position: Long) {
        player.seekTo(position * 1000)
        _uiState.update {
            it.copy(
                currentDuration = position * 1000
            )
        }
        Log.d("shidou", "seekTo: ${_uiState.value.currentDuration}")
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

    fun playNextSong() {
        Log.d("shidou", "setMusicExoPlayer: ${player.currentMediaItemIndex}")
        player.seekToNextMediaItem()
        _uiState.update {
            it.copy(
                currentSong = DataSource.songs[player.currentMediaItemIndex],
                duration = DataSource.songs[player.currentMediaItemIndex].duration
            )
        }
        player.prepare()
        player.play()
    }

    fun playPreviousSong() {
        player.seekToPreviousMediaItem()
        _uiState.update {
            it.copy(
                currentSong = DataSource.songs[player.currentMediaItemIndex],
                duration = DataSource.songs[player.currentMediaItemIndex].duration
            )
        }
        player.prepare()
        player.play()
    }

    fun loopSong() {
        _uiState.update {
            it.copy(
                isLooping = !it.isLooping
            )
        }
        if (_uiState.value.isLooping) {
            player.repeatMode = Player.REPEAT_MODE_ONE
        }else{
            player.repeatMode = Player.REPEAT_MODE_OFF
            playNextSong()
        }
    }
//    fun addListener(){
//        player.addListener(object : Player.Listener {
//            override fun onPlaybackStateChanged(playbackState: Int) {
//                super.onPlaybackStateChanged(playbackState)
//                if (_uiState.value.isLooping) {
//                    player.repeatMode = Player.REPEAT_MODE_ONE
//                }else{
//                    player.repeatMode = Player.REPEAT_MODE_OFF
//                    playNextSong()
//                }
//            }
//        })
//    }

}


