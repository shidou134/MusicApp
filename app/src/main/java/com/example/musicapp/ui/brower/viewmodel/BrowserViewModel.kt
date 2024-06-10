package com.example.musicapp.ui.brower.viewmodel

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
import com.example.musicapp.modelresponse.playlist.PlaylistItem
import com.example.musicapp.modelresponse.topic.TopicItem
import kotlinx.coroutines.launch
import java.io.IOException

sealed interface BrowserUiState {
    data class Success(
        val topic: List<TopicItem> ,
        val playlist: List<PlaylistItem>
    ) : BrowserUiState

    data object Loading : BrowserUiState
    data object Error : BrowserUiState
}

class BrowserViewModel(private val songRepository: SongRepository) : ViewModel() {
    var browserUiState: BrowserUiState by mutableStateOf(BrowserUiState.Loading)
        private set

    init {
        getData()
    }

    fun getData() {
        viewModelScope.launch {
            browserUiState = BrowserUiState.Loading
            browserUiState = try {
                BrowserUiState.Success(
                    songRepository.getTopics(),
                    songRepository.getPlaylists()
                )
            } catch (e: IOException) {
                BrowserUiState.Error
            } catch (e: HttpException) {
                BrowserUiState.Error
            }
        }
    }


    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application =
                    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as SongApplication)
                val songRepository = application.container.songRepository
                BrowserViewModel(songRepository = songRepository)
            }
        }
    }
}