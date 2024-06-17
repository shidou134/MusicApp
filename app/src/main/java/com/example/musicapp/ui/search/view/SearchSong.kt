package com.example.musicapp.ui.search.view

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material3.MaterialTheme
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
import com.example.musicapp.common.LoadingScreen
import com.example.musicapp.modelresponse.song.SongItem
import com.example.musicapp.ui.playingsong.state.SongUiState
import com.example.musicapp.ui.song.viewmodell.SongsUiState
import com.example.musicapp.ui.theme.DarkBackground
import com.example.musicapp.ui.theme.DarkBackgroundOpacity
import com.example.musicapp.ui.theme.Silver

@Composable
fun SearchSong(
    songUiState: SongsUiState,
    text: String,
    retryAction: () -> Unit,
    onValueChange: (String) -> Unit,
    onNavigateToPlayingSong: (SongItem) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        TextField(
            value = text,
            onValueChange = onValueChange,
            modifier = Modifier.fillMaxWidth(),
            placeholder = {
                Text(
                    text = "Search",
                    color = Silver,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            }
        )
        Spacer(modifier = Modifier.height(16.dp))
        when (songUiState) {
            is SongsUiState.Loading -> {}
            is SongsUiState.Success -> ListSongs(
                song = songUiState.songs,
                onNavigateToPlayingSong = onNavigateToPlayingSong,
            )

            is SongsUiState.Error -> ErrorScreen(
                retryAction = {
                    retryAction()
                },
                modifier = modifier.fillMaxSize()
            )
        }

    }
}

@Composable
fun SongPhoto(
    songImg: String,
    modifier: Modifier = Modifier
) {
    AsyncImage(
        model = ImageRequest.Builder(context = LocalContext.current)
            .data(songImg)
            .crossfade(true)
            .build(),
        contentDescription = stringResource(R.string.top_song_content_description),
        error = painterResource(R.drawable.ic_connection_error),
        placeholder = painterResource(R.drawable.loading_img),
        modifier = modifier.clip(CircleShape)
    )
}

@Composable
fun SongCard(
    song: SongItem,
    onNavigateToPlayingSong: (SongItem) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 8.dp)
            .clickable { onNavigateToPlayingSong(song) }
    ) {
        Box(
            modifier = Modifier.size(128.dp)
        ) {
            SongPhoto(
                songImg = song.songImg ?: ""
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
        Spacer(modifier = Modifier.width(20.dp))
        Column(
            modifier = Modifier.padding(horizontal = 8.dp)
                .align(Alignment.CenterVertically)
        ) {
            androidx.compose.material3.Text(
                text = song.songName ?: "",
                style = MaterialTheme.typography.labelSmall,
                color = Silver,
                modifier = Modifier
            )
            androidx.compose.material3.Text(
                text = song.artistName ?: "",
                style = MaterialTheme.typography.displaySmall,
                color = Silver,
                modifier = Modifier
            )
        }
        Spacer(modifier = Modifier.width(8.dp))
    }

}

@Composable
fun ListSongs(
    song: List<SongItem>,
    onNavigateToPlayingSong: (SongItem) -> Unit,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp)
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize(),
        contentPadding = contentPadding
    ) {
        items(items = song) { data ->
            SongCard(
                song = data,
                onNavigateToPlayingSong = onNavigateToPlayingSong,
                modifier = modifier.fillMaxWidth()
            )
        }
    }
}