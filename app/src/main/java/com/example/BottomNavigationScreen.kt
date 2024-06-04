package com.example

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArtTrack
import androidx.compose.material.icons.rounded.Audiotrack
import androidx.compose.material.icons.rounded.Headset
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.List
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material.icons.rounded.Person
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import com.example.musicapp.MainDestinations

import com.example.musicapp.R

sealed class BottomNavigationScreens(
    val route: String,
    @StringRes val resourceId: Int,
    val icon: ImageVector,
) {

    object Browser :
        BottomNavigationScreens(
            MainDestinations.Browser.name,
            R.string.browser_screen_route,
            Icons.Rounded.ArtTrack
        )

    object Artists :
        BottomNavigationScreens(
            MainDestinations.Artists.name,
            R.string.artists_screen_route,
            Icons.Rounded.Person
        )

    object Home :
        BottomNavigationScreens(
            MainDestinations.Home.name,
            R.string.home_screen_route,
            Icons.Rounded.Home
        )

    object Genres :
        BottomNavigationScreens(
            MainDestinations.Radios.name,
            R.string.genres_bag_screen_route,
            Icons.Rounded.Audiotrack
        )

    object MyMusic :
        BottomNavigationScreens(
            MainDestinations.MyMusic.name,
            R.string.my_music_screen_route,
            Icons.Rounded.Headset
        )
}