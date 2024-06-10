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
import com.example.musicapp.ui.brower.ui.BrowserScreen
import com.example.musicapp.ui.brower.viewmodel.BrowserViewModel
import com.example.musicapp.ui.genre.view.GenreScreen
import com.example.musicapp.ui.home.view.HomeScreen
import com.example.musicapp.ui.mymusic.view.MyMusicScreen
import com.example.musicapp.ui.genre.viewmodel.GenreViewModel
import com.example.musicapp.ui.home.HomeViewModel

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
//                    CommonMethod.ARTIST_TYPE -> {
//                        val topSongViewModel: TopSongViewModel =
//                            viewModel(factory = TopSongViewModel.Factory)
//                        LaunchedEffect(true) {
//                            topSongViewModel.getTopSong(uiState.artistId)
//                        }
//
////                        TopSongScreen(
////                            retryAction = {
////                                topSongViewModel.getTopSong(uiState.artistId)
////                            },
////                            onNavigateBack = {
////                                navController.popBackStack()
////                            },
////                            onNavigateToPlayingSong = {
//////                                song ->
//////                                sharedViewModel.setMusicExoPlayer(context, song)
//////                                Log.d("shidou", "listSongs: ${uiState.listSong.size}")
//////                                sharedViewModel.setSong(song)
//////                                navController.navigate(route = MainDestinations.PlayingSong.name)
////                            },
////                            topSongState = topSongViewModel.topSongUiState,
////                            modifier = Modifier.fillMaxSize()
////                        )


//                        val radioTrackViewModel: RadioTracksViewModel =
//                            viewModel(factory = RadioTracksViewModel.Factory)
//                        LaunchedEffect(true) {
//                            radioTrackViewModel.getRadioTracks(uiState.radioId)
//                        }
//                        RadioTracksScreen(
//                            retryAction = {
//                                radioTrackViewModel.getRadioTracks(uiState.radioId)
//                            },
//                            onNavigateBack = {
//                                navController.popBackStack()
//                            },
//                            onNavigateToPlayingSong = {
////                                sharedViewModel.setMusicExoPlayer(context, song)
////                                Log.d("shidou", "listSongs: ${uiState.listSong.size}")
////                                sharedViewModel.setSong(song)
////                                navController.navigate(route = MainDestinations.PlayingSong.name)
//                            },
//                            radioTracksState = radioTrackViewModel.radioTracksUiState,
//                            modifier = Modifier.fillMaxSize()
//                        )

            }

            composable(route = MainDestinations.PlayingSong.name) {
//                val viewModel: SongViewModel = viewModel(factory = SongViewModel.Factory)
//                val playingSongUiState by viewModel.uiState.collectAsState()
//                if (uiState.isSongPlaying) {
//                    sharedViewModel.getCurrentPosition()
//                }
//                PlayingSongScreen(
//                    onClick = {
//                        sharedViewModel.playOrPauseSong()
//                    },
//                    isPlaying = uiState.isSongPlaying,
//                    currentPosition = uiState.currentDuration.toFloat(),
//                    duration = uiState.duration.toFloat(),
//                    onValueChange = {
//                        sharedViewModel.seekTo(it.toLong())
//                    },
//                    onValueChangeFinish = {
//                        sharedViewModel.getCurrentPosition()
//                    },
//                    currentSong = uiState.currentSong!!,
//                    onNextSong = {
//                        sharedViewModel.playNextSong(it)
//                    },
//                    onPreviousSong = {
//                        sharedViewModel.playPreviousSong(it)
//                    },
//                    isLooping = uiState.isLooping,
//                    isShuffle = uiState.isShuffle,
//                    onLoopSong = {
//                        sharedViewModel.loopSong(it)
//                    },
//                    onShuffleSong = {
//                        sharedViewModel.shuffleSong()
//                    },
//                    modifier = Modifier.fillMaxSize()
//                )
            }
            composable(MainDestinations.Browser.name) {
                val browserViewModel: BrowserViewModel =
                    viewModel(factory = BrowserViewModel.Factory)
                BrowserScreen(
                    browserUiState = browserViewModel.browserUiState,
                    retryAction = browserViewModel::getData,
                    onNavigateToGenreScreen = {
                        uiState.type = CommonMethod.RADIO_TYPE
                        uiState.topicId = it
                        navController.navigate(route = MainDestinations.Genre.name)
                    },
                    onNavigateTop50Songs = {
                        uiState.type = CommonMethod.ARTIST_TYPE
                        navController.navigate(route = MainDestinations.Song.name)
                    }
                )
            }
            composable(MainDestinations.Artists.name) {
//                val viewModel: ArtistViewModel = viewModel(factory = ArtistViewModel.Factory)
//                Log.d("shidou", "NavGraph: ${uiState.artistId}")
//                ArtistScreen(
//                    retryAction = viewModel::getArtists,
//                    artistState = viewModel.artistUiState,
//                    onNavigateTop50Songs = {
//                        uiState.type = CommonMethod.ARTIST_TYPE
//                        uiState.artistId = it
//                        navController.navigate(route = MainDestinations.Song.name)
//                        Log.d("shidou", "id: $it")
//                    }
//                )
            }
            composable(MainDestinations.Home.name) {
                val homeViewModel: HomeViewModel = viewModel(factory = HomeViewModel.Factory)
                HomeScreen(
                    homeUiState = homeViewModel.homeUiState,
                    retryAction = homeViewModel::getData,
                    onNavigateTop50Songs = {

                    },
                    onNavigateToTracks = {}
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
                        uiState.type = CommonMethod.RADIO_TYPE
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