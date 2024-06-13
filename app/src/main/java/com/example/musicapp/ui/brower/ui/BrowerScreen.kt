package com.example.musicapp.ui.brower.ui

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.ui.layout.ContentScale
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
import com.example.musicapp.modelresponse.playlist.PlaylistItem
import com.example.musicapp.modelresponse.topic.TopicItem
import com.example.musicapp.ui.brower.viewmodel.BrowserUiState
import com.example.musicapp.ui.theme.DarkBackground
import com.example.musicapp.ui.theme.DarkBackgroundOpacity
import com.example.musicapp.ui.theme.Silver

@Composable
fun BrowserScreen(
    browserUiState: BrowserUiState,
    onNavigateToPlaylist: (String) -> Unit,
    onNavigateToGenreScreen: (String) -> Unit,
    retryAction: () -> Unit,
    onSearchSong: () -> Unit,
    modifier: Modifier = Modifier
) {
    when (browserUiState) {
        is BrowserUiState.Loading -> LoadingScreen(modifier = modifier.fillMaxSize())
        is BrowserUiState.Success -> {
            Column(
                modifier = Modifier
                    .background(DarkBackground)
                    .fillMaxSize()
                    .padding(vertical = 32.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                HeaderSection("Browser", onSearchSong, Modifier.padding(horizontal = 32.dp))
                SectionHeader(title = "Trending", subtitle = "Music", action = "See All")
                TrendingSection()
                SectionHeader(title = "Hot", subtitle = "Topic")
                TopicSection(
                    topic = browserUiState.topic,
                    onNavigateToGenreScreen = onNavigateToGenreScreen
                )
                SectionHeader(title = "Top", subtitle = "Playlist")
                PlaylistSection(
                    playlist = browserUiState.playlist,
                    onNavigateTop50Songs = onNavigateToPlaylist,
                )
            }
        }

        is BrowserUiState.Error -> ErrorScreen(
            retryAction = retryAction,
            modifier = modifier.fillMaxSize()
        )
    }
}

@Composable
fun TrendingSection() {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.padding(horizontal = 32.dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.shadmehr),
            contentDescription = "First trending music",
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .weight(2f)
                .padding(4.dp)
                .clip(RoundedCornerShape(16.dp))
        )
        Column(
            verticalArrangement = Arrangement.SpaceAround,
            modifier = Modifier
                .weight(1f)
        ) {
            Image(
                painter = painterResource(id = R.drawable.zedbazi),
                contentDescription = "Second trending music",
                modifier = Modifier
                    .padding(4.dp)
                    .clip(RoundedCornerShape(16.dp))
            )
            Image(
                painter = painterResource(id = R.drawable.donya),
                contentDescription = "Third trending music",
                modifier = Modifier
                    .padding(4.dp)
                    .clip(RoundedCornerShape(16.dp))
            )
        }
    }
}


@Composable
fun PlaylistSection(
    playlist: List<PlaylistItem>,
    onNavigateTop50Songs: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyRow(
        contentPadding = PaddingValues(horizontal = 32.dp)
    ) {
        items(items = playlist) { item ->
            PlaylistItem(
                playlist = item,
                onNavigateTop50Songs = onNavigateTop50Songs
            )
        }
    }
}

@Composable
fun PlaylistItem(
    playlist: PlaylistItem,
    onNavigateTop50Songs: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.padding(horizontal = 8.dp)
    ) {
        Box(
            modifier = Modifier.size(128.dp)
                .clickable {
                    onNavigateTop50Songs(playlist.idPlaylist ?: "")
                }
        ) {
            PlaylistPhoto(
                playlistImg = playlist.img ?: ""
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
            text = playlist.name ?: "",
            color = Silver,
            style = MaterialTheme.typography.displaySmall,
            modifier = Modifier.padding(top = 4.dp)
        )
    }
}

@Composable
fun PlaylistPhoto(
    playlistImg: String,
    modifier: Modifier = Modifier
) {
    AsyncImage(
        model = ImageRequest.Builder(context = LocalContext.current)
            .data(playlistImg)
            .crossfade(true)
            .build(),
        contentDescription = stringResource(R.string.playlist_name),
        error = painterResource(R.drawable.ic_connection_error),
        placeholder = painterResource(R.drawable.loading_img),
        modifier = modifier.clip(CircleShape)
    )
}

@Composable
fun TopicSection(
    topic: List<TopicItem>,
    onNavigateToGenreScreen: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyRow(
        modifier = modifier,
        contentPadding = PaddingValues(horizontal = 32.dp)
    ) {
        items(items = topic) { item ->
            TopicItem(
                topic = item,
                onNavigateToGenreScreen = onNavigateToGenreScreen
            )
        }
    }
}

@Composable
fun TopicItem(
    topic: TopicItem,
    onNavigateToGenreScreen: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth()
            .padding(horizontal = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier.size(128.dp)
                .clickable {
                    onNavigateToGenreScreen(topic.idTopic ?: "")
                }
        ) {
            TopicPhoto(
                topicImg = topic.topicImg ?: ""
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
            text = topic.topicName ?: "",
            color = Silver,
            style = MaterialTheme.typography.labelSmall,
            modifier = Modifier.padding(horizontal = 8.dp)
                .align(Alignment.CenterHorizontally)
        )
    }
}

@Composable
fun TopicPhoto(
    topicImg: String,
    modifier: Modifier = Modifier
) {
    AsyncImage(
        model = ImageRequest.Builder(context = LocalContext.current)
            .data(topicImg)
            .crossfade(true)
            .build(),
        contentDescription = stringResource(R.string.topic_name),
        error = painterResource(R.drawable.ic_connection_error),
        placeholder = painterResource(R.drawable.loading_img),
        modifier = modifier.clip(CircleShape)
    )

}