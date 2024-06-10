package com.example.musicapp.ui.artist.viewmodel

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
import kotlinx.coroutines.launch
import java.io.IOException

//sealed interface ArtistsUiState {
//    data class Success(val artists: ArtistResponse) : ArtistsUiState
//    data object Loading : ArtistsUiState
//    data object Error : ArtistsUiState
//}
//
//class ArtistViewModel(private val songRepository: SongRepository): ViewModel() {
//    var artistUiState: ArtistsUiState by mutableStateOf(ArtistsUiState.Loading)
//        private set
//
//    init {
//        getArtists()
//    }
//
//    fun getArtists() {
//        viewModelScope.launch {
//            artistUiState = ArtistsUiState.Loading
//            artistUiState = try {
//                ArtistsUiState.Success(
//                    songRepository.getArtist()
//                )
//            } catch (e: IOException) {
//                ArtistsUiState.Error
//            } catch (e: HttpException) {
//                ArtistsUiState.Error
//            }
//        }
//    }
//
//    companion object {
//        val Factory: ViewModelProvider.Factory = viewModelFactory {
//            initializer {
//                val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as SongApplication)
//                val songRepository = application.container.songRepository
//                ArtistViewModel(songRepository = songRepository)
//            }
//        }
//    }
//}