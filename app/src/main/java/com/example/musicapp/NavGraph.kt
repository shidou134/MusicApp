package com.example.musicapp

import android.util.Log
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.musicapp.common.CommonMethod
import com.example.musicapp.modelresponse.song.SongItem
import com.example.musicapp.ui.artist.view.ArtistScreen
import com.example.musicapp.ui.artist.viewmodel.ArtistViewModel
import com.example.musicapp.ui.brower.ui.BrowserScreen
import com.example.musicapp.ui.brower.viewmodel.BrowserViewModel
import com.example.musicapp.ui.genre.view.GenreScreen
import com.example.musicapp.ui.song.view.SongsScreen
import com.example.musicapp.ui.home.view.HomeScreen
import com.example.musicapp.ui.mymusic.view.MyMusicScreen
import com.example.musicapp.ui.genre.viewmodel.GenreViewModel
import com.example.musicapp.ui.song.viewmodell.SongsViewModel
import com.example.musicapp.ui.home.HomeViewModel
import com.example.musicapp.ui.mymusic.viewmodel.MyMusicViewModel
import com.example.musicapp.ui.playingsong.view.PlayingSongScreen
import com.example.musicapp.ui.search.view.SearchSong

enum class MainDestinations {
    Browser,
    Artists,
    Home,
    Genre,
    MyMusic,
    OnBoarding,
    Login,
    Register,
    ResetPassword,
    Welcome,
    Song,
    PlayingSong,
    Auth,
    MainApp,
    Search,
    Root
}

@Composable
fun NavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController
) {
    val sharedViewModel: SharedViewModel = viewModel()
    val context = LocalContext.current
    val uiState by sharedViewModel.uiState.collectAsState()
    val viewModel: SongsViewModel = viewModel(factory = SongsViewModel.Factory)
    val playingSongUiState by viewModel.uiState.collectAsState()
    val searchText by viewModel.searchText.collectAsState()
    var listSong = listOf<SongItem>()
    NavHost(
        navController,
        startDestination = MainDestinations.MainApp.name,
        modifier = modifier
    ) {
        //Main App
        navigation(
            startDestination = MainDestinations.Browser.name,
            route = MainDestinations.MainApp.name
        ) {
            composable(route = MainDestinations.Song.name) {
                fun getSong() {
                    when (uiState.type) {
                        CommonMethod.GENRE -> {
                            viewModel.getSong(uiState.genreId)
                        }

                        CommonMethod.FAVOURITE_SONG -> {
                            viewModel.getFavouriteSong()
                        }

                        CommonMethod.PLAYLIST -> {
                            viewModel.getSongInPlaylist(uiState.playlistId)
                        }

                        CommonMethod.ALBUM -> {
                            viewModel.getSongInAlbum(uiState.albumId)
                        }

                        CommonMethod.ARTIST -> {
                            viewModel.getSongInArtist(uiState.artistId)
                        }
                    }
                }
                LaunchedEffect(true) {
                    getSong()
                }
                SongsScreen(
                    songsState = viewModel.songsUiState,
                    retryAction = {
                        getSong()
                    },
                    onNavigateBack = {
                        navController.popBackStack()
                    },
                    onNavigateToPlayingSong = {
                        viewModel.setMusicExoPlayer(context, it)
                        viewModel.setSong(it)
                        Log.d("shidou", "songitem: $it")
                        navController.navigate(route = MainDestinations.PlayingSong.name)
                    }
                )
            }

            composable(route = MainDestinations.PlayingSong.name) {
                if (playingSongUiState.isSongPlaying) {
                    viewModel.getCurrentPosition()
                }
                PlayingSongScreen(
                    onClick = {
                        viewModel.playOrPauseSong()
                    },
                    isPlaying = playingSongUiState.isSongPlaying,
                    currentPosition = playingSongUiState.currentDuration.toFloat(),
                    duration = playingSongUiState.duration.toFloat(),
                    onValueChange = {
                        viewModel.seekTo(it.toLong())
                    },
                    onValueChangeFinish = {
                        viewModel.getCurrentPosition()
                    },
                    currentSong = playingSongUiState.currentSong!!,
                    onNextSong = {
                        viewModel.playNextSong(it)
                    },
                    onPreviousSong = {
                        viewModel.playPreviousSong(it)
                    },
                    isLooping = playingSongUiState.isLooping,
                    isShuffle = playingSongUiState.isShuffle,
                    onLoopSong = {
                        viewModel.loopSong(it)
                    },
                    onShuffleSong = {
                        viewModel.shuffleSong()
                    },
                    likedSong = {
                        viewModel.saveSong(it)
                    },
                    modifier = Modifier.fillMaxSize()
                )
            }
            composable(MainDestinations.Browser.name) {
                val browserViewModel: BrowserViewModel =
                    viewModel(factory = BrowserViewModel.Factory)
                BrowserScreen(
                    browserUiState = browserViewModel.browserUiState,
                    retryAction = browserViewModel::getData,
                    onNavigateToGenreScreen = {
                        uiState.type = CommonMethod.GENRE
                        uiState.topicId = it
                        navController.navigate(route = MainDestinations.Genre.name)
                    },
                    onNavigateToPlaylist = {
                        uiState.type = CommonMethod.PLAYLIST
                        uiState.playlistId = it
                        navController.navigate(route = MainDestinations.Song.name)
                    },
                    onSearchSong = {
                        navController.navigate(route = MainDestinations.Search.name)
                    }
                )
            }
            composable(MainDestinations.Artists.name) {
                val viewModel: ArtistViewModel = viewModel(factory = ArtistViewModel.Factory)
                ArtistScreen(
                    retryAction = viewModel::getArtists,
                    artistState = viewModel.artistUiState,
                    onSearchSong = {
                        navController.navigate(route = MainDestinations.Search.name)
                    },
                    onNavigateTop50Songs = {
                        uiState.type = CommonMethod.ARTIST
                        uiState.artistId = it
                        navController.navigate(route = MainDestinations.Song.name)
                    }
                )
            }
            composable(MainDestinations.Home.name) {
                val homeViewModel: HomeViewModel = viewModel(factory = HomeViewModel.Factory)
                HomeScreen(
                    homeUiState = homeViewModel.homeUiState,
                    retryAction = homeViewModel::getData,
                    onNavigateTop50SongsAlbum = {
                        uiState.type = CommonMethod.ALBUM
                        uiState.albumId = it
                        navController.navigate(route = MainDestinations.Song.name)
                    },
                    onNavigateToTracks = {
                        viewModel.setMusicExoPlayer(context, it)
                        viewModel.setSong(it)
                        navController.navigate(route = MainDestinations.PlayingSong.name)
                    },
                    onSearchSong = {
                        navController.navigate(route = MainDestinations.Search.name)
                    }
                )
            }
            composable(MainDestinations.Genre.name) {
                val viewModel: GenreViewModel = viewModel(factory = GenreViewModel.Factory)
                LaunchedEffect(true) {
                    viewModel.getGenre(uiState.topicId)
                }
                GenreScreen(
                    retryAction = {
                        viewModel.getGenre(uiState.topicId)
                    },
                    genreState = viewModel.genreUiState,
                    onNavigateToTracks = {
                        uiState.type = CommonMethod.GENRE
                        uiState.genreId = it
                        navController.navigate(route = MainDestinations.Song.name)
                    },
                    onSearchSong = {
                        navController.navigate(route = MainDestinations.Search.name)
                    }
                )
            }
            composable(MainDestinations.MyMusic.name) {
                val myMusicViewModel: MyMusicViewModel = viewModel()
                MyMusicScreen(
                    onNavigateToHistory = {},
                    onNavigateToLogOut = {
                        myMusicViewModel.logout()
                        navController.navigate(route = MainDestinations.Login.name)
                    },
                    onNavigateToLikedSongs = {
                        uiState.type = CommonMethod.FAVOURITE_SONG
                        navController.navigate(route = MainDestinations.Song.name)
                    },
                    onNavigateToMyPlaylists = {},
                    onNavigateToFollowedArtist = {},
                    onSearchSong = {
                        navController.navigate(route = MainDestinations.Search.name)
                    }
                )
            }
            composable(MainDestinations.Search.name) {

                SearchSong(
                    songUiState = viewModel.songsUiState,
                    text = searchText,
                    retryAction = {

                    },
                    onNavigateToPlayingSong = {
                        viewModel.setMusicExoPlayer(context, it)
                        viewModel.setSong(it)
                        navController.navigate(route = MainDestinations.PlayingSong.name)
                    },
                    onValueChange = {
                        viewModel.onSearchTextChange(it)
                        viewModel.searchSong(it)
                    },
                )
            }
        }
    }
}