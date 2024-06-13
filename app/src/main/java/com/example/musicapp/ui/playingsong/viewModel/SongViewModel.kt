package com.example.musicapp.ui.playingsong.viewModel

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import com.example.musicapp.SongApplication
import com.example.musicapp.data.SongRepository
import com.example.musicapp.modelresponse.song.SongItem
import com.example.musicapp.ui.playingsong.state.SongUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class SongViewModel(private val songRepository: SongRepository) : ViewModel() {

    private val _uiState = MutableStateFlow(SongUiState())
    var uiState: StateFlow<SongUiState> = _uiState.asStateFlow()

    private lateinit var player: ExoPlayer
    private lateinit var runnable: Runnable
    private var handler = Handler(Looper.getMainLooper())

    fun setMusicExoPlayer(context: Context, song: List<SongItem>) {
        player = ExoPlayer.Builder(context).build()
        song.forEach { songItem ->
            val mediaItem = MediaItem.fromUri(songItem.songUrl ?: "")
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

    fun seekTo(position: Long) {
        player.seekTo(position * 1000)
        _uiState.update {
            it.copy(
                currentDuration = position * 1000
            )
        }
        Log.d("shidou", "seekTo: ${_uiState.value.currentDuration}")
    }

    fun setSong(song: SongItem) {
        _uiState.update {
            it.copy(
                currentSong = song,
                isSongPlaying = true,
                duration = player.duration / 1000
            )
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

    fun playNextSong(songs: List<SongItem>) {
//        Log.d("shidou", "setMusicExoPlayer: ${player.currentMediaItemIndex}")
//        player.seekToNextMediaItem()
//        _uiState.update {
//            it.copy(
//                currentSong = songs[player.currentMediaItemIndex],
//                duration = songs[player.currentMediaItemIndex].duration ?: 0
//            )
//        }
//        player.prepare()
//        player.play()
    }

    fun playPreviousSong(songs: List<SongItem>) {
//        player.seekToPreviousMediaItem()
//        _uiState.update {
//            it.copy(
//                currentSong = songs[player.currentMediaItemIndex],
//                duration = songs[player.currentMediaItemIndex].duration ?: 0
//            )
//        }
//        player.prepare()
//        player.play()
    }

    fun loopSong(songs: List<SongItem>) {
        _uiState.update {
            it.copy(
                isLooping = !it.isLooping
            )
        }
        if (_uiState.value.isLooping) {
            player.repeatMode = Player.REPEAT_MODE_ONE
        } else {
            player.repeatMode = Player.REPEAT_MODE_OFF
            playNextSong(songs)
        }
    }

    fun shuffleSong() {
        _uiState.update {
            it.copy(
                isShuffle = !it.isShuffle
            )
        }
        if (_uiState.value.isShuffle) {
            player.shuffleModeEnabled
        }
    }

    //    fun getSong(id:Long){
//        viewModelScope.launch {
//            songRepository.getTrack(id)
//        }
//    }
    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application =
                    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as SongApplication)
                val songRepository = application.container.songRepository
                SongViewModel(songRepository = songRepository)
            }
        }
    }

}


