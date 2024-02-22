package com.example.musicapp

import android.content.Context
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.musicapp.data.DataSource
import com.example.musicapp.ui.PlayingSongScreen
import com.example.musicapp.ui.SongScreen
import com.example.musicapp.ui.SongViewModel
import com.example.musicapp.ui.WelcomeScreen

enum class MusicScreen() {
    Welcome,
    Song,
    PlayingSong
}

@Composable
fun MusicApp(modifier: Modifier = Modifier) {
    val navController: NavHostController = rememberNavController()
    val viewModel: SongViewModel = viewModel()


    Scaffold(modifier = modifier) {
        NavHost(
            navController = navController,
            startDestination = MusicScreen.Welcome.name,
            modifier = Modifier.padding(it)
        ) {
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
                        navController.navigate(
                            route = MusicScreen.PlayingSong.name
                        )
                    }
                )
            }
            composable(route = MusicScreen.PlayingSong.name){


            }
        }
    }
}