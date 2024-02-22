package com.example.musicapp.ui

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.musicapp.R
import com.example.musicapp.data.DataSource
import com.example.musicapp.model.Song

@Composable
fun SongScreen(
    onPlaySongClicked: (Song) -> Unit,
    song: List<Song>,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = Color.Black)
            .padding(top = dimensionResource(R.dimen.padding_medium))
    ) {
        Text(
            stringResource(R.string.recommend_for_you),
            style = MaterialTheme.typography.labelSmall,
            color = Color.White
        )
        LazyColumn() {
            items(song) {
                SongItem(
                    song = it,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(dimensionResource(R.dimen.padding_small))
                        .clickable { onPlaySongClicked(it) }
                )
            }
        }
    }

}

@Composable
fun SongItem(
    song: Song,
    modifier: Modifier = Modifier
) {
    Card(modifier = modifier
        .fillMaxSize(),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Row(modifier = Modifier
            .fillMaxWidth()
        ) {
            SongImage(
                image = song.image
            )
            SongInfo(
                name = song.name,
                artist = song.artist,
                modifier = Modifier
                    .padding(
                        start = dimensionResource(R.dimen.padding_medium),
                        top = dimensionResource(R.dimen.padding_small),
                        end = dimensionResource(R.dimen.padding_medium),
                        bottom = dimensionResource(R.dimen.padding_medium)
                    )
            )
        }
    }
}

@Composable
fun SongImage(
    @DrawableRes image: Int,
    modifier: Modifier = Modifier
) {
    Image(
        painter = painterResource(id = image),
        contentDescription = null,
        modifier = modifier
            .size(dimensionResource(R.dimen.image_size_medium))
            .padding(dimensionResource(R.dimen.padding_medium))
            .clip(MaterialTheme.shapes.medium),
        contentScale = ContentScale.FillBounds
    )
}

@Composable
fun SongInfo(
    @StringRes name: Int,
    @StringRes artist: Int,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.padding(dimensionResource(R.dimen.padding_medium))) {
        Text(
            text = stringResource(id = name),
            style = MaterialTheme.typography.labelSmall
        )
        Text(
            text = stringResource(id = artist),
            style = MaterialTheme.typography.bodyLarge
        )
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun SongScreenPreview() {
    SongScreen(
        onPlaySongClicked = {},
        song = DataSource.songs
    )
}