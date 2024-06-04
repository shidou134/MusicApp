package com.example.musicapp.ui.topsong.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import coil.network.HttpException
import com.example.musicapp.SongApplication
import com.example.musicapp.common.CommonState
import com.example.musicapp.data.SongRepository
import com.example.musicapp.ui.playingsong.state.SongUiState
import com.example.musicapp.modelresponse.top50song.TopSongModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.io.IOException

sealed interface TopSongUiState {
    data class Success(val topSong: TopSongModel) : TopSongUiState
    data object Loading : TopSongUiState
    data object Error : TopSongUiState
}

class TopSongViewModel(private val songRepository: SongRepository) : ViewModel() {
    var topSongUiState: TopSongUiState by mutableStateOf(TopSongUiState.Loading)
        private set
    fun getTopSong(id:Long) {
        viewModelScope.launch {
            topSongUiState = TopSongUiState.Loading
            topSongUiState = try {
                TopSongUiState.Success(
                    songRepository.getTopSongs(id)
                )
            } catch (e: IOException) {
                TopSongUiState.Error
            } catch (e: HttpException) {
                TopSongUiState.Error
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application =
                    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as SongApplication)
                val songRepository = application.container.songRepository
                TopSongViewModel(songRepository = songRepository)
            }
        }
    }
}