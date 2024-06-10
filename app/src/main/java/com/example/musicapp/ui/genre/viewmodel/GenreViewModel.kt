package com.example.musicapp.ui.genre.viewmodel

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
import com.example.musicapp.modelresponse.genre.GenreItem
import kotlinx.coroutines.launch
import java.io.IOException

sealed interface GenreUiState {
    data class Success(val genre: List<GenreItem>) : GenreUiState
    data object Loading : GenreUiState
    data object Error : GenreUiState
}

class GenreViewModel(private val songRepository: SongRepository) : ViewModel() {
    var genreUiState: GenreUiState by mutableStateOf(GenreUiState.Loading)
        private set

    fun getGenre(idTopic: String) {
        viewModelScope.launch {
            genreUiState = GenreUiState.Loading
            genreUiState = try {
                GenreUiState.Success(
                    songRepository.getGenres(idTopic)
                )
            } catch (e: IOException) {
                GenreUiState.Error
            } catch (e: HttpException) {
                GenreUiState.Error
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application =
                    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as SongApplication)
                val songRepository = application.container.songRepository
                GenreViewModel(songRepository = songRepository)
            }
        }
    }
}