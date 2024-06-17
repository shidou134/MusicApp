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
import com.example.musicapp.data.followedartistrepo.FollowedArtistRepository
import com.example.musicapp.data.followedartistrepo.FollowedArtistRepositoryImpl
import com.example.musicapp.modelresponse.artist.ArtistItem
import com.example.musicapp.modelresponse.song.SongItem
import com.example.musicapp.ui.song.viewmodell.SongsUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.io.IOException

sealed interface ArtistsUiState {
    data class Success(val artists: List<ArtistItem>) : ArtistsUiState
    data object Loading : ArtistsUiState
    data object Error : ArtistsUiState
}

class ArtistViewModel(
    private val songRepository: SongRepository,
    private val followedArtistRepository: FollowedArtistRepository = FollowedArtistRepositoryImpl()
) : ViewModel() {
    var artistUiState: ArtistsUiState by mutableStateOf(ArtistsUiState.Loading)
        private set
    private val _isFollowed = MutableStateFlow(false)
    val isFollowed = _isFollowed.asStateFlow()

    init {
        getArtists()
    }

    fun saveArtist(artist: ArtistItem) {
        viewModelScope.launch {
            followedArtistRepository.followedArtist(artist = artist)
            _isFollowed.value = true
        }
    }
    fun unfollowArtist(artist: ArtistItem) {
        viewModelScope.launch {
            followedArtistRepository.deleteArtist(artist = artist)
            _isFollowed.value = false
        }
    }

    fun getFollowedArtist() {
        viewModelScope.launch {
            artistUiState = ArtistsUiState.Loading
            artistUiState = try {
                ArtistsUiState.Success(
                    followedArtistRepository.getFollowedArtist()
                )
            } catch (e: IOException) {
                ArtistsUiState.Error
            } catch (e: HttpException) {
                ArtistsUiState.Error
            }
        }
    }
    fun getArtists() {
        viewModelScope.launch {
            artistUiState = ArtistsUiState.Loading
            artistUiState = try {
                ArtistsUiState.Success(
                    songRepository.getListArtist()
                )
            } catch (e: IOException) {
                ArtistsUiState.Error
            } catch (e: HttpException) {
                ArtistsUiState.Error
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application =
                    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as SongApplication)
                val songRepository = application.container.songRepository
                ArtistViewModel(songRepository = songRepository)
            }
        }
    }
}