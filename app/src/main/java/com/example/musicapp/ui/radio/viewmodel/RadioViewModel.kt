package com.example.musicapp.ui.radio.viewmodel

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
import com.example.musicapp.modelresponse.radio.RadioModel
import kotlinx.coroutines.launch
import java.io.IOException

sealed interface RadiosUiState {
    data class Success(val radio: RadioModel) : RadiosUiState
    data object Loading : RadiosUiState
    data object Error : RadiosUiState
}

class RadioViewModel(private val songRepository: SongRepository): ViewModel() {
    var radioUiState: RadiosUiState by mutableStateOf(RadiosUiState.Loading)
        private set

    init {
        getRadios()
    }

    fun getRadios() {
        viewModelScope.launch {
            radioUiState = RadiosUiState.Loading
            radioUiState = try {
                RadiosUiState.Success(
                    songRepository.getRadios()
                )
            } catch (e: IOException) {
                RadiosUiState.Error
            } catch (e: HttpException) {
                RadiosUiState.Error
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as SongApplication)
                val songRepository = application.container.songRepository
                RadioViewModel(songRepository = songRepository)
            }
        }
    }
}