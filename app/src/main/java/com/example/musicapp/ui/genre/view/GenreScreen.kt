package com.example.musicapp.ui.genre.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
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
import com.example.musicapp.modelresponse.genre.GenreItem
import com.example.musicapp.ui.genre.viewmodel.GenreUiState
import com.example.musicapp.ui.theme.DarkBackground
import com.example.musicapp.ui.theme.Silver

@Composable
fun GenreScreen(
    genreState: GenreUiState,
    retryAction: () -> Unit,
    onNavigateToTracks: (String) -> Unit,
    onSearchSong: () -> Unit,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp)
) {
    when (genreState) {
        is GenreUiState.Loading -> LoadingScreen(modifier = modifier.fillMaxSize())
        is GenreUiState.Success ->
            Column(
                modifier = Modifier
                    .background(DarkBackground)
                    .fillMaxSize()
                    .padding(top = 32.dp)
            ) {
                HeaderSection("Radio", onSearchSong, Modifier.padding(horizontal = 32.dp))
                ListGenre(
                    genre = genreState.genre,
                    onNavigateToTracks = onNavigateToTracks,
                    modifier = modifier.fillMaxSize()
                )
            }


        is GenreUiState.Error -> ErrorScreen(
            retryAction = retryAction,
            modifier = modifier.fillMaxSize()
        )
    }
}

@Composable
fun GenrePhoto(
    genreImg: String,
    modifier: Modifier = Modifier
) {
    AsyncImage(
        model = ImageRequest.Builder(context = LocalContext.current)
            .data(genreImg)
            .crossfade(true)
            .build(),
        contentDescription = stringResource(R.string.genre_name),
        error = painterResource(R.drawable.ic_connection_error),
        placeholder = painterResource(R.drawable.loading_img),
        modifier = modifier.clip(RoundedCornerShape(12.dp))
    )

}

@Composable
fun GenreCard(
    genre: GenreItem,
    onNavigateToTracks: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable {
                onNavigateToTracks(genre.idGenre ?: "")
            }
    ) {
        GenrePhoto(
            genreImg = genre.genreImg ?: ""
        )
        Spacer(modifier = Modifier.width(20.dp))
        Text(
            text = genre.genreName ?: "",
            color = Silver,
            style = MaterialTheme.typography.displaySmall,
            modifier = Modifier.padding(horizontal = 8.dp)
                .align(Alignment.CenterHorizontally)
        )
        Spacer(modifier = Modifier.width(8.dp))
    }

}

@Composable
fun ListGenre(
    genre: List<GenreItem>,
    onNavigateToTracks: (String) -> Unit,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp)
) {
    LazyVerticalGrid(
        modifier = modifier.padding(horizontal = 16.dp, vertical = 16.dp),
        columns = GridCells.Fixed(2)
    ) {
        items(items = genre) { data ->
            GenreCard(
                genre = data,
                onNavigateToTracks = onNavigateToTracks,
                modifier = modifier.fillMaxWidth()
            )
        }
    }

}