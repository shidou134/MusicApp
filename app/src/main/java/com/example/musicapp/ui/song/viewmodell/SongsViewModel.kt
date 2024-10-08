package com.example.musicapp.ui.song.viewmodell

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import coil.network.HttpException
import com.example.musicapp.SongApplication
import com.example.musicapp.data.favouritesongrepo.FavoriteRepository
import com.example.musicapp.data.favouritesongrepo.FavouriteRepositoryImpl
import com.example.musicapp.data.SongRepository
import com.example.musicapp.data.historyrepo.HistoryRepository
import com.example.musicapp.data.historyrepo.HistoryRepositoryImpl
import com.example.musicapp.data.myplaylistrepo.MyPlaylistRepository
import com.example.musicapp.data.myplaylistrepo.MyPlaylistRepositoryImpl
import com.example.musicapp.modelresponse.song.SongItem
import com.example.musicapp.ui.playingsong.state.SongUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.IOException

sealed interface SongsUiState {
    data class Success(val songs: List<SongItem>) : SongsUiState
    data object Loading : SongsUiState
    data object Error : SongsUiState
}

class SongsViewModel(
    private val songRepository: SongRepository,
    private val favouriteSongRepository: FavoriteRepository = FavouriteRepositoryImpl(),
    private val history: HistoryRepository = HistoryRepositoryImpl(),
    private val myPlaylist: MyPlaylistRepository = MyPlaylistRepositoryImpl()
) : ViewModel() {
    var songsUiState: SongsUiState by mutableStateOf(SongsUiState.Loading)
        private set
    private val _uiState = MutableStateFlow(SongUiState())
    var uiState: StateFlow<SongUiState> = _uiState.asStateFlow()
    private val _searchText = MutableStateFlow("")
    val searchText = _searchText.asStateFlow()

    private lateinit var player: ExoPlayer
    private lateinit var runnable: Runnable
    private var handler = Handler(Looper.getMainLooper())

    private val _songLiveData: MutableLiveData<List<SongItem?>> = MutableLiveData()
    val songLiveData: LiveData<List<SongItem?>>
        get() = _songLiveData

    private val _isLiked = MutableStateFlow(false)
    val isLiked = _isLiked.asStateFlow()
    private val _addPlaylist = MutableStateFlow(false)
    val addPlaylist = _addPlaylist.asStateFlow()

    fun addPlaylist(song: SongItem) {
        viewModelScope.launch {
            myPlaylist.song(song)
            _addPlaylist.value = true
        }
    }

    fun getMyPlaylist() {
        viewModelScope.launch {
            songsUiState = SongsUiState.Loading
            songsUiState = try {
                SongsUiState.Success(
                    myPlaylist.getMyPlaylist()
                )
            } catch (e: IOException) {
                SongsUiState.Error
            } catch (e: HttpException) {
                SongsUiState.Error
            }
        }
    }

    fun deletePlaylist(song: SongItem) {
        viewModelScope.launch {
            viewModelScope.launch {
                myPlaylist.deleteMyPlaylist(song = song)
                _addPlaylist.value = false
            }
        }
    }

    fun addHistory(song: SongItem) {
        viewModelScope.launch {
            history.addHistory(song = song)
        }
    }

    fun getHistory() {
        viewModelScope.launch {
            songsUiState = SongsUiState.Loading
            songsUiState = try {
                SongsUiState.Success(
                    history.getHistory()
                )
            } catch (e: IOException) {
                SongsUiState.Error
            } catch (e: HttpException) {
                SongsUiState.Error
            }
        }
    }

    fun saveSong(favouriteSong: SongItem) {
        viewModelScope.launch {
            favouriteSongRepository.likedSong(song = favouriteSong)
            _isLiked.value = true
        }
    }

    fun unLikeSong(favouriteSong: SongItem) {
        viewModelScope.launch {
            favouriteSongRepository.deleteSongFavorite(song = favouriteSong)
            _isLiked.value = false
        }
    }

    fun getFavouriteSong() {
        viewModelScope.launch {
            songsUiState = SongsUiState.Loading
            songsUiState = try {
                SongsUiState.Success(
                    favouriteSongRepository.getSongFavorite()
                )
            } catch (e: IOException) {
                SongsUiState.Error
            } catch (e: HttpException) {
                SongsUiState.Error
            }
        }
    }

    fun onSearchTextChange(text: String) {
        _searchText.value = text
    }

    fun searchSong(query: String) {
        viewModelScope.launch {
            songsUiState = SongsUiState.Loading
            songsUiState = try {
                SongsUiState.Success(
                    songRepository.searchSong(query)
                )
            } catch (e: IOException) {
                SongsUiState.Error
            } catch (e: HttpException) {
                SongsUiState.Error
            }
        }
    }

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

    fun getSongInPlaylist(idPlaylist: String) {
        viewModelScope.launch {
            songsUiState = SongsUiState.Loading
            songsUiState = try {
                SongsUiState.Success(
                    songRepository.getSongByPlaylist(idPlaylist)
                )
            } catch (e: IOException) {
                SongsUiState.Error
            } catch (e: HttpException) {
                SongsUiState.Error
            }
        }
    }

    fun getSongInAlbum(idAlbum: String) {
        viewModelScope.launch {
            songsUiState = SongsUiState.Loading
            songsUiState = try {
                SongsUiState.Success(
                    songRepository.getSongByAlbum(idAlbum)
                )
            } catch (e: IOException) {
                SongsUiState.Error
            } catch (e: HttpException) {
                SongsUiState.Error
            }
        }
    }

    fun getSongInArtist(artistName: String) {
        viewModelScope.launch {
            songsUiState = SongsUiState.Loading
            songsUiState = try {
                SongsUiState.Success(
                    songRepository.getSongByArtist(artistName)
                )
            } catch (e: IOException) {
                SongsUiState.Error
            } catch (e: HttpException) {
                SongsUiState.Error
            }
        }
    }

    fun setMusicExoPlayer(context: Context, song: SongItem) {
        player = ExoPlayer.Builder(context).build()
        val mediaItem = MediaItem.fromUri(song.songUrl ?: "")
        player.setMediaItem(mediaItem)
        player.prepare()
        player.play()

    }

    fun getCurrentPosition() {
        runnable = Runnable {
            _uiState.update {
                it.copy(
                    currentDuration = player.currentPosition
                )
            }
            Log.d("shidou", "getCurrentPosition: ${_uiState.value.currentDuration}")
            handler.postDelayed(runnable, 100)
        }
        handler.postDelayed(runnable, 100)

    }

    fun seekTo(position: Long) {
        player.seekTo(position * 1000)
        _uiState.update {
            it.copy(
                currentDuration = position * 1000
            )
        }
        Log.d("shidou", "seekTo: ${_uiState.value.currentDuration}")
    }

    fun setSong(song: SongItem) {
        _uiState.update {
            it.copy(
                currentSong = song,
                isSongPlaying = true,
                duration = song.duration ?: 0
            )
        }

    }

    fun playOrPauseSong() {
        _uiState.update {
            it.copy(isSongPlaying = !it.isSongPlaying)
        }
        if (_uiState.value.isSongPlaying) {
            player.play()
        } else {
            player.pause()
        }
    }

    fun playNextSong(songs: List<SongItem>) {
//        Log.d("shidou", "setMusicExoPlayer: ${player.currentMediaItemIndex}")
//        player.seekToNextMediaItem()
//        _uiState.update {
//            it.copy(
//                currentSong = songs[player.currentMediaItemIndex],
//                duration = songs[player.currentMediaItemIndex].duration ?: 0
//            )
//        }
//        player.prepare()
//        player.play()
    }

    fun playPreviousSong(songs: List<SongItem>) {
//        player.seekToPreviousMediaItem()
//        _uiState.update {
//            it.copy(
//                currentSong = songs[player.currentMediaItemIndex],
//                duration = songs[player.currentMediaItemIndex].duration ?: 0
//            )
//        }
//        player.prepare()
//        player.play()
    }

    fun loopSong(songs: List<SongItem>) {
        _uiState.update {
            it.copy(
                isLooping = !it.isLooping
            )
        }
        if (_uiState.value.isLooping) {
            player.repeatMode = Player.REPEAT_MODE_ONE
        } else {
            player.repeatMode = Player.REPEAT_MODE_OFF
            playNextSong(songs)
        }
    }

    fun shuffleSong() {
        _uiState.update {
            it.copy(
                isShuffle = !it.isShuffle
            )
        }
        if (_uiState.value.isShuffle) {
            player.shuffleModeEnabled
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