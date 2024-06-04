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
import com.example.musicapp.ui.playingsong.view.PlayingSongScreen
import com.example.musicapp.ui.artist.view.ArtistScreen
import com.example.musicapp.ui.artist.viewmodel.ArtistViewModel
import com.example.musicapp.ui.brower.ui.BrowserScreen
import com.example.musicapp.ui.brower.viewmodel.BrowserViewModel
import com.example.musicapp.ui.home.view.HomeScreen
import com.example.musicapp.ui.mymusic.view.MyMusicScreen
import com.example.musicapp.ui.topsong.view.TopSongScreen
import com.example.musicapp.ui.topsong.viewmodel.TopSongViewModel
import com.example.musicapp.ui.playingsong.viewModel.SongViewModel
import com.example.musicapp.ui.radio.view.RadioScreen
import com.example.musicapp.ui.radio.view.RadioTracksScreen
import com.example.musicapp.ui.radio.viewmodel.RadioTracksViewModel
import com.example.musicapp.ui.radio.viewmodel.RadioViewModel

enum class MainDestinations {
    Browser,
    Artists,
    Home,
    Radios,
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
                when (uiState.type) {
                    CommonMethod.ARTIST_TYPE -> {
                        val topSongViewModel: TopSongViewModel =
                            viewModel(factory = TopSongViewModel.Factory)
                        LaunchedEffect(true) {
                            topSongViewModel.getTopSong(uiState.artistId)
                        }

                        TopSongScreen(
                            retryAction = {
                                topSongViewModel.getTopSong(uiState.artistId)
                            },
                            onNavigateBack = {
                                navController.popBackStack()
                            },
                            onNavigateToPlayingSong = { song ->
                                sharedViewModel.setMusicExoPlayer(context, song)
                                Log.d("shidou", "listSongs: ${uiState.listSong.size}")
                                sharedViewModel.setSong(song)
                                navController.navigate(route = MainDestinations.PlayingSong.name)
                            },
                            topSongState = topSongViewModel.topSongUiState,
                            modifier = Modifier.fillMaxSize()
                        )
                    }

                    CommonMethod.RADIO_TYPE -> {
                        val radioTrackViewModel: RadioTracksViewModel =
                            viewModel(factory = RadioTracksViewModel.Factory)
                        LaunchedEffect(true) {
                            Log.d("shidou", "before uiState.radioId: ${uiState.radioId}")
                            radioTrackViewModel.getRadioTracks(uiState.radioId)
                            Log.d("shidou", "after uiState.radioId: ${uiState.radioId}")
                        }
                        RadioTracksScreen(
                            retryAction = {
                                radioTrackViewModel.getRadioTracks(uiState.radioId)
                            },
                            onNavigateBack = {
                                navController.popBackStack()
                            },
                            onNavigateToPlayingSong = {
//                                sharedViewModel.setMusicExoPlayer(context, song)
//                                Log.d("shidou", "listSongs: ${uiState.listSong.size}")
//                                sharedViewModel.setSong(song)
//                                navController.navigate(route = MainDestinations.PlayingSong.name)
                            },
                            radioTracksState = radioTrackViewModel.radioTracksUiState,
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                }
            }
            composable(route = MainDestinations.PlayingSong.name) {
                val viewModel: SongViewModel = viewModel(factory = SongViewModel.Factory)
                val playingSongUiState by viewModel.uiState.collectAsState()
                if (uiState.isSongPlaying) {
                    sharedViewModel.getCurrentPosition()
                }
                PlayingSongScreen(
                    onClick = {
                        sharedViewModel.playOrPauseSong()
                    },
                    isPlaying = uiState.isSongPlaying,
                    currentPosition = uiState.currentDuration.toFloat(),
                    duration = uiState.duration.toFloat(),
                    onValueChange = {
                        sharedViewModel.seekTo(it.toLong())
                    },
                    onValueChangeFinish = {
                        sharedViewModel.getCurrentPosition()
                    },
                    currentSong = uiState.currentSong!!,
                    onNextSong = {
                        sharedViewModel.playNextSong(it)
                    },
                    onPreviousSong = {
                        sharedViewModel.playPreviousSong(it)
                    },
                    isLooping = uiState.isLooping,
                    isShuffle = uiState.isShuffle,
                    onLoopSong = {
                        sharedViewModel.loopSong(it)
                    },
                    onShuffleSong = {
                        sharedViewModel.shuffleSong()
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
                    onNavigateToTracks = {
                        uiState.type = CommonMethod.RADIO_TYPE
                        uiState.radioId = it
                        navController.navigate(route = MainDestinations.Song.name)
                    },
                    onNavigateTop50Songs = {
                        uiState.type = CommonMethod.ARTIST_TYPE
                        uiState.artistId = it
                        navController.navigate(route = MainDestinations.Song.name)
                    }
                )
            }
            composable(MainDestinations.Artists.name) {
                val viewModel: ArtistViewModel = viewModel(factory = ArtistViewModel.Factory)
                Log.d("shidou", "NavGraph: ${uiState.artistId}")
                ArtistScreen(
                    retryAction = viewModel::getArtists,
                    artistState = viewModel.artistUiState,
                    onNavigateTop50Songs = {
                        uiState.type = CommonMethod.ARTIST_TYPE
                        uiState.artistId = it
                        navController.navigate(route = MainDestinations.Song.name)
                        Log.d("shidou", "id: $it")
                    }
                )
            }
            composable(MainDestinations.Home.name) {
                HomeScreen()
            }
            composable(MainDestinations.Radios.name) {
                val viewModel: RadioViewModel = viewModel(factory = RadioViewModel.Factory)
                RadioScreen(
                    retryAction = viewModel::getRadios,
                    radioState = viewModel.radioUiState,
                    onNavigateToTracks = {
                        uiState.type = CommonMethod.RADIO_TYPE
                        uiState.radioId = it
                        navController.navigate(route = MainDestinations.Song.name)
                        Log.d("shidou", "id: $it")
                    }
                )
            }
            composable(MainDestinations.MyMusic.name) {
                MyMusicScreen()
            }
        }

    }
}