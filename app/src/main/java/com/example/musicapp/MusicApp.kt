package com.example.musicapp

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.musicapp.data.DataSource
import com.example.musicapp.ui.OnBroadingScreen
import com.example.musicapp.ui.PlayingSongScreen
import com.example.musicapp.ui.home.SongScreen
import com.example.musicapp.ui.viewModel.SongViewModel
import com.example.musicapp.ui.home.WelcomeScreen
import com.example.musicapp.ui.login.resetpassword.view.ResetPasswordScreen
import com.example.musicapp.ui.login.view.LoginScreen
import com.example.musicapp.ui.register.view.RegistrationScreen

enum class MusicScreen {
    OnBoarding,
    Login,
    Register,
    ResetPassword,
    Welcome,
    Song,
    PlayingSong
}

@Composable
fun MusicApp(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    viewModel: SongViewModel = viewModel()
) {
    val context = LocalContext.current
    Scaffold(modifier = modifier) { paddingValues ->
        val uiState by viewModel.uiState.collectAsState()
        NavHost(
            navController = navController,
            startDestination = MusicScreen.OnBoarding.name,
            modifier = Modifier.padding(paddingValues)
        ) {
            composable(route = MusicScreen.OnBoarding.name) {
                OnBroadingScreen(
                    onLoginClick = {
                        navController.navigate(route = MusicScreen.Login.name)
                    },
                    onRegisterClick = {
                        navController.navigate(route = MusicScreen.Register.name)
                    },
                    onLoginWithGoogleClick = {},
                    modifier = Modifier.fillMaxSize()
                )
            }
            composable(route = MusicScreen.Login.name) {
                LoginScreen(
                    onNavigateToRegistration = {
                        navController.navigate(route = MusicScreen.Register.name)
                    },
                    onLoginWithGoogle = {},
                    onNavigateToForgotPassword = {
                        navController.navigate(route = MusicScreen.ResetPassword.name)
                    },
                    onNavigateToWelcomeScreen = {
                        navController.navigate(route = MusicScreen.Welcome.name)
                    }
                )
            }
            composable(route = MusicScreen.Register.name) {
                RegistrationScreen(
                    onLoginWithGoogle = {},
                    onNavigateBack = {
                        navController.popBackStack()
                    },
                    onNavigateToLoginScreen = {
                        navController.navigate(route = MusicScreen.Login.name)
                    }
                )
            }
            composable(route = MusicScreen.ResetPassword.name) {
                ResetPasswordScreen(
                    onNavigateBack = {
                        navController.popBackStack()
                    },
                    onLoginWithGoogle = {},
                    onNavigateToLoginScreen = {
                        navController.navigate(route = MusicScreen.Login.name)

                    }
                )
            }
            composable(route = MusicScreen.Welcome.name) {
                WelcomeScreen(
                    onWelcomeClick = {
                        navController.navigate(route = MusicScreen.Song.name)
                    },
                    modifier = Modifier.fillMaxSize()
                )
            }
            composable(route = MusicScreen.Song.name) {
                SongScreen(
                    song = DataSource.songs,
                    onPlaySongClicked = {
                        if (uiState.currentSong == null) {
                            viewModel.setSong(it)
                            viewModel.setMusicExoPlayer(context)
                        }
                        navController.navigate(
                            route = MusicScreen.PlayingSong.name
                        )
                    },
                    modifier = Modifier.fillMaxSize()
                )
            }
            composable(route = MusicScreen.PlayingSong.name) {
                if (uiState.isSongPlaying) {
                    viewModel.getCurrentPosition()
                }
                PlayingSongScreen(
                    onClick = {
                        viewModel.playOrPauseSong()
                    },
                    isPlaying = uiState.isSongPlaying,
                    currentPosition = uiState.currentDuration.toFloat(),
                    duration = uiState.duration.toFloat(),
                    onValueChange = {
                        viewModel.seekTo(it.toLong())
                    },
                    onValueChangeFinish = {
                        viewModel.getCurrentPosition()
                    },
                    currentSong = uiState.currentSong!!,
                    onNextSong = {
                        viewModel.playNextSong()
                    },
                    onPreviousSong = {
                        viewModel.playPreviousSong()
                    },
                    isLooping = uiState.isLooping,
                    isShuffle = uiState.isShuffle,
                    onLoopSong = {
                        viewModel.loopSong()
                    },
                    onShuffleSong = {
                        viewModel.shuffleSong()
                    },
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
}