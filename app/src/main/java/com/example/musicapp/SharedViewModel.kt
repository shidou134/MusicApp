package com.example.musicapp

import android.os.Handler
import android.os.Looper
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.media3.exoplayer.ExoPlayer
import com.example.musicapp.common.CommonState
import com.example.musicapp.modelresponse.song.SongItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class SharedViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(CommonState())
    var uiState: StateFlow<CommonState> = _uiState.asStateFlow()

    private lateinit var player: ExoPlayer
    private lateinit var runnable: Runnable
    private var handler = Handler(Looper.getMainLooper())

    private var _listSong = MutableLiveData<List<SongItem>>()
    val listSong: LiveData<List<SongItem>> = _listSong

    fun getListSong(song: List<SongItem>) {
        _listSong.value = song
    }

//    fun setSong(song: Data?) {
//        _uiState.update {
//            it.copy(
//                currentSong = song,
//                isSongPlaying = true,
//                duration = song?.duration ?: 0
//            )
//        }
//        Log.d("shidou", "setSong: ${song?.duration}")
//    }
//
//    fun setMusicExoPlayer(context: Context, song:Data) {
//        player = ExoPlayer.Builder(context).build()
//        val mediaItem = MediaItem.fromUri( "https://measureless-knife.000webhostapp.com/song/HayTraoChoAnh-SonTungMTPSnoopDogg-6010660.mp3")
//        player.addMediaItem(mediaItem)
//        player.prepare()
//        player.play()
//
//    }
//
//    fun getCurrentPosition() {
//        runnable = Runnable {
//            _uiState.update {
//                it.copy(
//                    currentDuration = player.currentPosition
//                )
//            }
//            Log.d("shidou", "getCurrentPosition: ${_uiState.value.currentDuration}")
//            handler.postDelayed(runnable, 100)
//        }
//        handler.postDelayed(runnable, 100)
//
//    }
//
//    fun seekTo(position: Long) {
//        player.seekTo(position * 1000)
//        _uiState.update {
//            it.copy(
//                currentDuration = position * 1000
//            )
//        }
//        Log.d("shidou", "seekTo: ${_uiState.value.currentDuration}")
//    }
//
//    fun playOrPauseSong() {
//        _uiState.update {
//            it.copy(isSongPlaying = !it.isSongPlaying)
//        }
//        if (_uiState.value.isSongPlaying) {
//            player.play()
//        } else {
//            player.pause()
//        }
//    }
//
//    fun playNextSong(songs: List<Data>) {
//        Log.d("shidou", "setMusicExoPlayer: ${player.currentMediaItemIndex}")
//        player.seekToNextMediaItem()
//        _uiState.update {
//            it.copy(
//                currentSong = songs.get(player.currentMediaItemIndex),
//                duration = songs.get(player.currentMediaItemIndex)?.duration ?: 0
//            )
//        }
//        player.prepare()
//        player.play()
//    }
//
//    fun playPreviousSong(songs: List<Data>) {
//        player.seekToPreviousMediaItem()
//        _uiState.update {
//            it.copy(
//                currentSong = songs.get(player.currentMediaItemIndex),
//                duration = songs.get(player.currentMediaItemIndex)?.duration ?: 0
//            )
//        }
//        player.prepare()
//        player.play()
//    }
//
//    fun loopSong(songs: List<Data>) {
//        _uiState.update {
//            it.copy(
//                isLooping = !it.isLooping
//            )
//        }
//        if (_uiState.value.isLooping) {
//            player.repeatMode = Player.REPEAT_MODE_ONE
//        } else {
//            player.repeatMode = Player.REPEAT_MODE_OFF
//            playNextSong(songs)
//        }
//    }
//
//    fun shuffleSong() {
//        _uiState.update {
//            it.copy(
//                isShuffle = !it.isShuffle
//            )
//        }
//        if (_uiState.value.isShuffle) {
//            player.shuffleModeEnabled
//        }
//    }
}