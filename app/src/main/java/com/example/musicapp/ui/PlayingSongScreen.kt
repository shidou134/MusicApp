package com.example.musicapp.ui


import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
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
import com.example.musicapp.convertTime.ConvertTime
import com.example.musicapp.data.DataSource
import com.example.musicapp.model.Song

@Composable
fun PlayingSongScreen(
    isPlaying: Boolean,
    onClick: () -> Unit,
    value: Float,
    duration: Float,
    onValueChange: (Float) -> Unit,
    onValueChangeFinish: () -> Unit,
    currentSong: Song,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier,
        color = Color.Black,
        contentColor = Color.White
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            SongInfomation(
                image = currentSong.image,
                name = currentSong.name,
                artist = currentSong.artist
            )
            Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_small)))
            Slider(
                value = value.coerceAtLeast(0f),
                onValueChange = { onValueChange(it) },
                valueRange = 0f..duration.coerceAtLeast(0f),
                onValueChangeFinished = onValueChangeFinish,
                modifier = Modifier.padding(
                    start = dimensionResource(R.dimen.padding_medium),
                    end = dimensionResource(R.dimen.padding_medium),
                    top = dimensionResource(R.dimen.padding_small)
                )
            )
            Row(
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = ConvertTime.convertToTime(value.toLong()),
                    modifier = Modifier.padding(start = dimensionResource(R.dimen.padding_medium))
                )
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = ConvertTime.convertToTime(duration.toLong()),
                    modifier = Modifier.padding(
                        end = dimensionResource(R.dimen.padding_medium)
                    )
                )
            }
            Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_small)))
            SongController(
                onClick = onClick,
                isPlaying = isPlaying
            )

        }
    }

}

@Composable
fun SongInfomation(
    @DrawableRes image: Int,
    @StringRes name: Int,
    @StringRes artist: Int,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .padding(dimensionResource(R.dimen.padding_medium)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Card(modifier = Modifier) {
            Image(
                painter = painterResource(id = image),
                contentDescription = null,
                modifier = modifier
                    .clip(MaterialTheme.shapes.medium),
                contentScale = ContentScale.Fit
            )
        }
        Spacer(modifier = Modifier.padding(dimensionResource(R.dimen.padding_medium)))
        Text(
            text = stringResource(id = name),
            style = MaterialTheme.typography.bodyLarge
        )
        Spacer(modifier = Modifier.padding(dimensionResource(R.dimen.padding_small)))
        Text(
            text = stringResource(id = artist),
            style = MaterialTheme.typography.labelSmall
        )
    }
}

@Composable
fun SongController(
    onClick: () -> Unit,
    isPlaying: Boolean,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Icon(
            painter = painterResource(R.drawable.shuffle),
            contentDescription = null,
            modifier = Modifier.size(50.dp)
        )
        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_medium)))
        Icon(
            painter = painterResource(R.drawable.skip_back),
            contentDescription = null,
            modifier = Modifier.size(50.dp)
        )
        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_medium)))

        Icon(
            painter = if (isPlaying) painterResource(R.drawable.pause) else painterResource(R.drawable.play),
            contentDescription = null,
            modifier = Modifier.size(50.dp)
                .clickable { onClick() }
        )

        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_medium)))
        Icon(
            painter = painterResource(R.drawable.skip_forward),
            contentDescription = null,
            modifier = Modifier.size(50.dp)
        )
        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_medium)))
        Icon(
            painter = painterResource(R.drawable.repeat),
            contentDescription = null,
            modifier = Modifier.size(50.dp)
        )
        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_small)))
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PlayingSongScreenPreview() {
    PlayingSongScreen(
        isPlaying = true,
        value = 0f,
        onValueChange = {},
        onClick = {},
        currentSong = Song(
            artist = R.string.thanh_thao,
            name = R.string.co_quen_duoc_dau,
            image = R.drawable.co_quen_duoc_dau,
            duration = 254,
            url = "https://firebasestorage.googleapis.com/v0/b/music-app-2524d.appspot.com/o/music%2FCoQuenDuocDau-ThanhThao-8969228.mp3?alt=media&token=f7acdfb9-d0eb-4703-ac69-86719e76cec9"
        ),
        onValueChangeFinish = {},
        duration = 0f
    )
}