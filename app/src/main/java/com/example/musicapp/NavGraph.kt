package com.example.musicapp

import android.util.Log
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
    val isLiked by viewModel.isLiked.collectAsState()
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
                var title by remember {
                    mutableStateOf("")
                }
                fun getSong() {
                    when (uiState.type) {
                        CommonMethod.GENRE -> {
                            title = "Song by Genre"
                            viewModel.getSong(uiState.genreId)
                        }

                        CommonMethod.FAVOURITE_SONG -> {
                            title = "Favourite Song"
                            viewModel.getFavouriteSong()
                        }

                        CommonMethod.PLAYLIST -> {
                            title = "Song by Playlist"
                            viewModel.getSongInPlaylist(uiState.playlistId)
                        }

                        CommonMethod.ALBUM -> {
                            title = "Song by Album"
                            viewModel.getSongInAlbum(uiState.albumId)
                        }

                        CommonMethod.ARTIST -> {
                            title = "Song by Artist"
                            viewModel.getSongInArtist(uiState.artistId)
                        }

                        CommonMethod.HISTORY -> {
                            title = "History"
                            viewModel.getHistory()
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
                        navController.navigateUp()
                    },
                    title = title,
                    onNavigateToPlayingSong = {
                        viewModel.setMusicExoPlayer(context, it)
                        viewModel.setSong(it)
                        viewModel.addHistory(it)
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
                    unLikedSong = {
                        viewModel.unLikeSong(it)
                    },
                    isLiked = isLiked,
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
                val artistViewModel: ArtistViewModel = viewModel(factory = ArtistViewModel.Factory)
                val isFollowed by artistViewModel.isFollowed.collectAsState()
                fun getArtists() {
                    if (uiState.isFavouriteArtist) {
                        artistViewModel.getFollowedArtist()
                    } else {
                        artistViewModel.getArtists()
                    }
                }
                LaunchedEffect(true) {
                    getArtists()
                }
                ArtistScreen(
                    retryAction = {
                        getArtists()
                    },
                    artistState = artistViewModel.artistUiState,
                    onSearchSong = {
                        uiState.isFavouriteArtist = false
                        navController.navigate(route = MainDestinations.Search.name)
                    },
                    onNavigateTop50Songs = {
                        uiState.type = CommonMethod.ARTIST
                        uiState.artistId = it
                        uiState.isFavouriteArtist = false
                        navController.navigate(route = MainDestinations.Song.name)
                    },
                    isFollowed = isFollowed,
                    followArtist = {
                        artistViewModel.saveArtist(it)
                    },
                    unfollowArtist = {
                        artistViewModel.unfollowArtist(it)
                    }
                )
                uiState.isFavouriteArtist = false
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
                val genreViewModel: GenreViewModel = viewModel(factory = GenreViewModel.Factory)
                LaunchedEffect(true) {
                    genreViewModel.getGenre(uiState.topicId)
                }
                GenreScreen(
                    retryAction = {
                        genreViewModel.getGenre(uiState.topicId)
                    },
                    genreState = genreViewModel.genreUiState,
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
                    onNavigateToHistory = {
                        uiState.type = CommonMethod.HISTORY
                        navController.navigate(route = MainDestinations.Song.name)
                    },
                    onNavigateToLogOut = {
                        myMusicViewModel.logout()
                        navController.navigate(route = MainDestinations.Login.name)
                    },
                    onNavigateToLikedSongs = {
                        uiState.type = CommonMethod.FAVOURITE_SONG
                        navController.navigate(route = MainDestinations.Song.name)
                    },
                    onNavigateToMyPlaylists = {},
                    onNavigateToFollowedArtist = {
                        uiState.isFavouriteArtist = true
                        navController.navigate(route = MainDestinations.Artists.name)
                    },
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