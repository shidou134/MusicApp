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
import kotlinx.coroutines.launch
import java.io.IOException

//sealed interface RadioTracksUiState {
//    data class Success(val radioTracks: RadioSongsModel) : RadioTracksUiState
//    data object Loading : RadioTracksUiState
//    data object Error : RadioTracksUiState
//}
//
//class RadioTracksViewModel(private val songRepository: SongRepository) : ViewModel() {
//    var radioTracksUiState: RadioTracksUiState by mutableStateOf(RadioTracksUiState.Loading)
//        private set
//    fun getRadioTracks(id:Long) {
//        viewModelScope.launch {
//            radioTracksUiState = RadioTracksUiState.Loading
//            radioTracksUiState = try {
//                RadioTracksUiState.Success(
//                    songRepository.getRadioTracks(id)
//                )
//            } catch (e: IOException) {
//                RadioTracksUiState.Error
//            } catch (e: HttpException) {
//                RadioTracksUiState.Error
//            }
//        }
//    }
//
//    companion object {
//        val Factory: ViewModelProvider.Factory = viewModelFactory {
//            initializer {
//                val application =
//                    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as SongApplication)
//                val songRepository = application.container.songRepository
//                RadioTracksViewModel(songRepository = songRepository)
//            }
//        }
//    }
//}