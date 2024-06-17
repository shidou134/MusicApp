package com.example.musicapp.ui.artist.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
import com.example.musicapp.modelresponse.artist.ArtistItem
import com.example.musicapp.ui.artist.viewmodel.ArtistsUiState
import com.example.musicapp.ui.theme.DarkBackground
import com.example.musicapp.ui.theme.Silver

@Composable
fun ArtistScreen(
    artistState: ArtistsUiState,
    retryAction: () -> Unit,
    onNavigateTop50Songs: (String) -> Unit,
    onSearchSong: () -> Unit,
    isFollowed: Boolean,
    followArtist: (ArtistItem) -> Unit,
    unfollowArtist: (ArtistItem) -> Unit,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp)
) {
    when (artistState) {
        is ArtistsUiState.Loading -> LoadingScreen(modifier = modifier.fillMaxSize())
        is ArtistsUiState.Success ->
            Column(
                modifier = Modifier
                    .background(DarkBackground)
                    .fillMaxSize()
                    .padding(top = 32.dp)
            ) {
                HeaderSection("Artists", onSearchSong, Modifier.padding(horizontal = 32.dp))
                ListArtists(
                    artists = artistState.artists,
                    onNavigateTop50Songs = onNavigateTop50Songs,
                    isFollowed = isFollowed,
                    followArtist = followArtist,
                    unfollowArtist = unfollowArtist,
                    modifier = modifier.fillMaxSize()
                )
            }

        is ArtistsUiState.Error -> ErrorScreen(
            retryAction = retryAction,
            modifier = modifier.fillMaxSize()
        )
    }
}

@Composable
fun ArtistPhoto(
    artistImg: String,
    modifier: Modifier = Modifier
) {
    AsyncImage(
        model = ImageRequest.Builder(context = LocalContext.current)
            .data(artistImg)
            .crossfade(true)
            .build(),
        contentDescription = stringResource(R.string.artist_name),
        error = painterResource(R.drawable.ic_connection_error),
        placeholder = painterResource(R.drawable.loading_img),
        modifier = modifier.clip(CircleShape)
            .size(128.dp)
    )

}

@Composable
fun ArtistCard(
    artists: ArtistItem,
    onNavigateTop50Songs: (String) -> Unit,
    isFollowed: Boolean,
    followArtist: (ArtistItem) -> Unit,
    unfollowArtist: (ArtistItem) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable {
                onNavigateTop50Songs(artists.idArtist ?: "")
            }
    ) {
        ArtistPhoto(
            artistImg = artists.artistImg ?: ""
        )
        Spacer(modifier = Modifier.width(20.dp))
        Text(
            text = artists.artistName ?: "",
            style = MaterialTheme.typography.labelSmall,
            color = Silver,
            modifier = Modifier.padding(horizontal = 8.dp)
                .align(Alignment.CenterVertically)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Image(
            painter = if(isFollowed) painterResource(R.drawable.icon_like_red) else painterResource(R.drawable.ic_like),
            contentDescription = stringResource(R.string.my_music_follower_artists_title),
            modifier = Modifier.clickable {
                if(isFollowed){
                    unfollowArtist(artists)
                }else{
                    followArtist(artists)
                }
            }
                .align(Alignment.CenterVertically),
            alignment = Alignment.CenterEnd
        )
    }

}

@Composable
fun ListArtists(
    artists: List<ArtistItem>,
    onNavigateTop50Songs: (String) -> Unit,
    isFollowed: Boolean,
    followArtist: (ArtistItem) -> Unit,
    unfollowArtist: (ArtistItem) -> Unit,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp)
) {
    Column(
        modifier = Modifier
            .background(DarkBackground)
            .fillMaxSize()
            .padding(top = 16.dp)
    ) {
        LazyColumn(
            modifier = modifier.padding(horizontal = 16.dp, vertical = 16.dp),
            contentPadding = contentPadding
        ) {
            items(items = artists,) { data ->
                ArtistCard(
                    artists = data,
                    onNavigateTop50Songs = onNavigateTop50Songs,
                    isFollowed = isFollowed,
                    followArtist = followArtist,
                    unfollowArtist = unfollowArtist,
                    modifier = modifier.fillMaxWidth()
                )
            }
        }
    }
}
