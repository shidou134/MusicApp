package com.example.musicapp.ui

import android.content.Context
import androidx.compose.ui.platform.LocalContext
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

    private lateinit var player:ExoPlayer

    fun setMusicExoPlayer(context:Context){
//    val context = LocalContext.current
        player = ExoPlayer.Builder(context).build()
        val mediaItem = MediaItem.fromUri(_uiState.value.currentSong!!.url)
        player.setMediaItem(mediaItem)
        player.prepare()
        player.play()
    }
   
}