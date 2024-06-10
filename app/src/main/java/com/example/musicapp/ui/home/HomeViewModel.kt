package com.example.musicapp.ui.home

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
import com.example.musicapp.modelresponse.album.AlbumItem
import com.example.musicapp.modelresponse.song.SongItem
import kotlinx.coroutines.launch
import java.io.IOException

sealed interface HomeUiState {
    data class Success(
        val topFavouriteSongs: List<SongItem>,
        val album: List<AlbumItem>
    ) : HomeUiState

    data object Loading : HomeUiState
    data object Error : HomeUiState
}

class HomeViewModel(private val songRepository: SongRepository) : ViewModel() {
    var homeUiState: HomeUiState by mutableStateOf(HomeUiState.Loading)
        private set

    init {
        getData()
    }

    fun getData() {
        viewModelScope.launch {
            homeUiState = HomeUiState.Loading
            homeUiState = try {
                HomeUiState.Success(
                    songRepository.getTopFavouriteSongs(),
                    songRepository.getAlbums()
                )
            } catch (e: IOException) {
                HomeUiState.Error
            } catch (e: HttpException) {
                HomeUiState.Error
            }
        }
    }


    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application =
                    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as SongApplication)
                val songRepository = application.container.songRepository
                HomeViewModel(songRepository = songRepository)
            }
        }
    }
}