package com.example.musicapp.ui.home.view

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.musicapp.R
import com.example.musicapp.common.ErrorScreen
import com.example.musicapp.common.HeaderSection
import com.example.musicapp.common.LoadingScreen
import com.example.musicapp.common.SectionHeader
import com.example.musicapp.modelresponse.album.AlbumItem
import com.example.musicapp.modelresponse.song.SongItem
import com.example.musicapp.ui.home.HomeUiState
import com.example.musicapp.ui.theme.DarkBackground
import com.example.musicapp.ui.theme.DarkBackgroundOpacity
import com.example.musicapp.ui.theme.Silver

@Composable
fun HomeScreen(
    homeUiState: HomeUiState,
    onNavigateTop50Songs: (Long) -> Unit,
    onNavigateToTracks: () -> Unit,
    retryAction: () -> Unit,
    modifier: Modifier = Modifier
) {
    when(homeUiState){
        is HomeUiState.Loading -> LoadingScreen(modifier = modifier.fillMaxSize())
        is HomeUiState.Success -> {
            Column(
                modifier = Modifier
                    .background(DarkBackground)
                    .fillMaxSize()
                    .padding(vertical = 32.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                HeaderSection("Home", Modifier.padding(horizontal = 32.dp))
                SectionHeader(title = "Top Favourite", subtitle = "Song")
                TopFavouriteSongsSection(
                    topFavouriteSong = homeUiState.topFavouriteSongs,
                    onNavigateToTracks = onNavigateToTracks
                )
                SectionHeader(title = "Hot", subtitle = "Album")
                AlbumSection(
                    album = homeUiState.album,
                    onNavigateTop50Songs = onNavigateTop50Songs,
                )
            }
        }
        is HomeUiState.Error -> ErrorScreen(
            retryAction = retryAction,
            modifier = modifier.fillMaxSize()
        )
    }
}

@Composable
fun AlbumSection(
    album: List<AlbumItem>,
    onNavigateTop50Songs: (Long) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyRow(
        contentPadding = PaddingValues(horizontal = 32.dp)
    ) {
        items(items = album){item->
            AlbumItem(
                album = item,
                onNavigateTop50Songs = onNavigateTop50Songs
            )
        }
    }
}

@Composable
fun AlbumItem(
    album: AlbumItem,
    onNavigateTop50Songs: (Long) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.padding(horizontal = 8.dp)
    ) {
        Box(
            modifier = Modifier.size(128.dp)
                .clickable {
                    onNavigateTop50Songs(album.idAlbum?.toLong() ?: 0)
                }
        ) {
            AlbumPhoto(
                albumImg = album.albumImg ?: ""
            )
            Canvas(
                modifier = Modifier
                    .size(32.dp)
                    .align(Alignment.Center)
            ) {
                drawCircle(
                    color = DarkBackgroundOpacity,
                    radius = 48f,
                    style = Fill
                )
                drawCircle(
                    color = DarkBackground,
                    radius = 32f,
                    style = Fill
                )
            }
        }
        Text(
            text = album.albumName ?: "",
            color = Silver,
            style = MaterialTheme.typography.displaySmall,
            modifier = Modifier.padding(top = 4.dp)
        )
    }
}

@Composable
fun AlbumPhoto(
    albumImg: String,
    modifier: Modifier = Modifier
) {
    AsyncImage(
        model = ImageRequest.Builder(context = LocalContext.current)
            .data(albumImg)
            .crossfade(true)
            .build(),
        contentDescription = stringResource(R.string.playlist_name),
        error = painterResource(R.drawable.ic_connection_error),
        placeholder = painterResource(R.drawable.loading_img),
        modifier = modifier.clip(CircleShape)
    )
}

@Composable
fun TopFavouriteSongsSection(
    topFavouriteSong: List<SongItem>,
    onNavigateToTracks: () -> Unit,
    modifier: Modifier = Modifier
) {
    LazyRow(
        modifier = modifier,
        contentPadding = PaddingValues(horizontal = 32.dp)
    ) {
        items(items = topFavouriteSong){item->
            TopFavouriteSongsItem(
                topFavouriteSong = item,
                onNavigateToTracks = onNavigateToTracks
            )
        }
    }
}

@Composable
fun TopFavouriteSongsItem(
    topFavouriteSong: SongItem,
    onNavigateToTracks: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth()
            .padding(horizontal =  8.dp)
            .clickable {
                onNavigateToTracks()
            }
    ) {
        TopFavouriteSongsPhoto(
            topFavouriteSongImg = topFavouriteSong.songImg ?: ""
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = topFavouriteSong.songName ?: "",
            color = Silver,
            style = MaterialTheme.typography.displaySmall,
            modifier = Modifier.padding(horizontal = 8.dp)
                .align(Alignment.CenterHorizontally)
        )
    }
}

@Composable
fun TopFavouriteSongsPhoto(
    topFavouriteSongImg: String,
    modifier: Modifier = Modifier
) {
    AsyncImage(
        model = ImageRequest.Builder(context = LocalContext.current)
            .data(topFavouriteSongImg)
            .crossfade(true)
            .build(),
        contentDescription = stringResource(R.string.favourite_song_name),
        error = painterResource(R.drawable.ic_connection_error),
        placeholder = painterResource(R.drawable.loading_img),
        modifier = modifier.clip(RoundedCornerShape(12.dp))
    )

}