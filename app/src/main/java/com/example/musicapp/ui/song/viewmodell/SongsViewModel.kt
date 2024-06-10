package com.example.musicapp.ui.song.viewmodell

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
import com.example.musicapp.data.SongRepository
import com.example.musicapp.modelresponse.song.SongItem
import kotlinx.coroutines.launch
import java.io.IOException

sealed interface SongsUiState {
    data class Success(val songs: List<SongItem>) : SongsUiState
    data object Loading : SongsUiState
    data object Error : SongsUiState
}

class SongsViewModel(private val songRepository: SongRepository) : ViewModel() {
    var songsUiState: SongsUiState by mutableStateOf(SongsUiState.Loading)
        private set

    fun getSong(idGenre: String) {
        viewModelScope.launch {
            songsUiState = SongsUiState.Loading
            songsUiState = try {
                SongsUiState.Success(
                    songRepository.getSongByGenre(idGenre)
                )
            } catch (e: IOException) {
                SongsUiState.Error
            } catch (e: HttpException) {
                SongsUiState.Error
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application =
                    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as SongApplication)
                val songRepository = application.container.songRepository
                SongsViewModel(songRepository = songRepository)
            }
        }
    }
}