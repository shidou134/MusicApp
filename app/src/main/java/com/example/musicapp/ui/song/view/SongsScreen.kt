package com.example.musicapp.ui.song.view

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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.musicapp.R
import com.example.musicapp.SharedViewModel
import com.example.musicapp.common.CommonState
import com.example.musicapp.common.ErrorScreen
import com.example.musicapp.common.LoadingScreen
import com.example.musicapp.modelresponse.song.SongItem
import com.example.musicapp.ui.song.viewmodell.SongsUiState
import com.example.musicapp.ui.theme.DarkBackground
import com.example.musicapp.ui.theme.DarkBackgroundOpacity
import com.example.musicapp.ui.theme.Silver
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

@Composable
fun SongsScreen(
    songsState: SongsUiState,
    retryAction: () -> Unit,
    onNavigateToPlayingSong: (SongItem) -> Unit,
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp)
) {
    when (songsState) {
        is SongsUiState.Loading -> LoadingScreen(modifier = modifier.fillMaxSize())
        is SongsUiState.Success -> ListSongs(
            song = songsState.songs,
            onNavigateToPlayingSong = onNavigateToPlayingSong,
            onNavigateBack = onNavigateBack,
            modifier = modifier.fillMaxSize()
        )

        is SongsUiState.Error -> ErrorScreen(
            retryAction = {
                retryAction()
            },
            modifier = modifier.fillMaxSize()
        )
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
            .padding(vertical = 8.dp)
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
            Text(
                text = song.songName ?: "",
                style = MaterialTheme.typography.labelSmall,
                color = Silver,
                modifier = Modifier
            )
            Text(
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
    onNavigateBack: () -> Unit,
    onNavigateToPlayingSong: (SongItem) -> Unit,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp)
) {
    val listSong = song.toMutableStateList()
    val sharedViewModel = SharedViewModel()
    Column(
        modifier = Modifier
            .background(DarkBackground)
            .fillMaxSize()
            .padding(top = 16.dp)
    ) {
        Row(
            modifier = Modifier.padding(horizontal = dimensionResource(R.dimen.padding_medium))
                .padding(top = dimensionResource(R.dimen.padding_large))
        ) {
            IconButton(
                modifier = Modifier,
                onClick = onNavigateBack,
                content = {
                    Icon(
                        imageVector = Icons.Outlined.ArrowBack,
                        contentDescription = stringResource(id = R.string.button_back)
                    )
                }
            )
            Text(
                text = stringResource(R.string.top_song_title),
                style = MaterialTheme.typography.titleMedium,
                color = Silver,
                modifier = Modifier.align(Alignment.CenterVertically)
            )
        }
        LazyColumn(
            modifier = modifier.padding(horizontal = 16.dp, vertical = 16.dp),
            contentPadding = contentPadding
        ) {
            items(items = song) { data ->
                listSong.add(data)
                SongCard(
                    song = data,
                    onNavigateToPlayingSong = onNavigateToPlayingSong,
                    modifier = modifier.fillMaxWidth()
                )
            }
            sharedViewModel.getListSong(listSong)
        }
    }
}