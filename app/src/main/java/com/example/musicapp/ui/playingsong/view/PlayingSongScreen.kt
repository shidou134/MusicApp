package com.example.musicapp.ui.playingsong.view


import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.musicapp.R
import com.example.musicapp.model.Song
import com.example.musicapp.ui.theme.Silver
import com.example.musicapp.ultis.ConvertTime

//@Composable
//fun PlayingSongScreen(
//    currentSong: Data,
//    isPlaying: Boolean,
//    isLooping: Boolean,
//    isShuffle: Boolean,
//    onLoopSong: (List<Data>) -> Unit,
//    onShuffleSong: (List<Data>) -> Unit,
//    onClick: () -> Unit,
//    currentPosition: Float,
//    duration: Float,
//    onValueChange: (Float) -> Unit,
//    onValueChangeFinish: () -> Unit,
//    onNextSong: (List<Data>) -> Unit,
//    onPreviousSong: (List<Data>) -> Unit,
//    modifier: Modifier = Modifier
//) {
//    Surface(
//        modifier = modifier,
//        color = Color.Black,
//        contentColor = Color.White
//    ) {
//        Column(
//            modifier = Modifier
//                .fillMaxSize(),
//            horizontalAlignment = Alignment.CenterHorizontally,
//            verticalArrangement = Arrangement.Center
//        ) {
//            SongInfomation(
//                song= currentSong
//            )
//            Row(
//                horizontalArrangement = Arrangement.SpaceBetween
//            ){
//                Image(
//                    painter = painterResource(R.drawable.ic_playlist),
//                    contentDescription = stringResource(R.string.my_music_your_playlists_title),
//                    modifier = Modifier.clickable {
//
//                    }
//                )
//                Spacer(modifier = Modifier.weight(1f))
//                Image(
//                    painter = painterResource(R.drawable.ic_like),
//                    contentDescription = stringResource(R.string.my_music_liked_songs_title),
//                    modifier = Modifier.clickable {
//
//                    }
//                )
//            }
//            Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_small)))
//            Slider(
//                value = currentPosition / 1000f,
//                onValueChange = { onValueChange(it) },
//                valueRange = 0f..duration.coerceAtLeast(0f),
//                onValueChangeFinished = { onValueChangeFinish() },
//                modifier = Modifier.padding(
//                    start = dimensionResource(R.dimen.padding_medium),
//                    end = dimensionResource(R.dimen.padding_medium),
//                    top = dimensionResource(R.dimen.padding_small)
//                )
//            )
//            Row(
//                horizontalArrangement = Arrangement.SpaceBetween
//            ) {
//                Text(
//                    text = ConvertTime.convertTimestamp(currentPosition.toLong()),
//                    modifier = Modifier.padding(start = dimensionResource(R.dimen.padding_medium))
//                )
//                Spacer(modifier = Modifier.weight(1f))
//                Text(
//                    text = ConvertTime.convertTimestamp((duration * 1000f).toLong()),
//                    modifier = Modifier.padding(
//                        end = dimensionResource(R.dimen.padding_medium)
//                    )
//                )
//            }
//            Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_small)))
//            SongController(
//                onClick = onClick,
//                isPlaying = isPlaying,
//                isLooping = isLooping,
//                isShuffle = isShuffle,
//                onLoopSong = onLoopSong,
//                onShuffleSong = onShuffleSong,
//                onNextSong = onNextSong,
//                onPreviousSong = onPreviousSong
//            )
//
//        }
//    }
//
//}
//
//@Composable
//fun SongInfomation(
//    song:Data,
//    modifier: Modifier = Modifier
//) {
//    Column(
//        modifier = modifier
//            .padding(dimensionResource(R.dimen.padding_medium)),
//        horizontalAlignment = Alignment.CenterHorizontally,
//        verticalArrangement = Arrangement.Center
//    ) {
//        SongImage(
//            songImg = song.album?.coverBig ?:""
//        )
//        Spacer(modifier = Modifier.padding(dimensionResource(R.dimen.padding_medium)))
//        Text(
//            text = song.title ?: "",
//            style = MaterialTheme.typography.labelSmall,
//            color = Silver,
//            modifier = Modifier
//        )
//        Text(
//            text = song.artist?.name ?: "",
//            style = MaterialTheme.typography.titleMedium,
//            color = Silver,
//            modifier = Modifier
//        )
//    }
//}
//
//@Composable
//fun SongImage(
//    songImg:String,
//    modifier: Modifier = Modifier
//){
//    AsyncImage(
//        model = ImageRequest.Builder(context = LocalContext.current)
//            .data(songImg)
//            .crossfade(true)
//            .build(),
//        contentDescription = stringResource(R.string.top_song_content_description),
//        error = painterResource(R.drawable.ic_connection_error),
//        placeholder = painterResource(R.drawable.loading_img),
//        modifier = modifier.clip(RoundedCornerShape(12.dp))
//    )
//}
//
//@Composable
//fun SongController(
//    onClick: () -> Unit,
//    onNextSong: (List<Data>) -> Unit,
//    onPreviousSong: (List<Data>) -> Unit,
//    isLooping: Boolean,
//    isPlaying: Boolean,
//    isShuffle: Boolean,
//    onLoopSong: (List<Data>) -> Unit,
//    onShuffleSong: (List<Data>) -> Unit,
//    modifier: Modifier = Modifier
//) {
//    Row(
//        modifier = modifier
//            .fillMaxWidth(),
//        verticalAlignment = Alignment.CenterVertically,
//        horizontalArrangement = Arrangement.SpaceEvenly
//    ) {
//        Icon(
//            painter = painterResource(R.drawable.shuffle),
//            contentDescription = null,
//            modifier = Modifier.size(50.dp)
//                .padding(10.dp)
//                .clickable { onShuffleSong(listOf()) },
//            tint = if (isShuffle) MaterialTheme.colorScheme.surfaceVariant else Color.White
//        )
//        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_medium)))
//        Icon(
//            painter = painterResource(R.drawable.skip_back),
//            contentDescription = null,
//            modifier = Modifier.size(50.dp)
//                .clickable { onPreviousSong(listOf()) }
//                .padding(10.dp)
//        )
//        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_medium)))
//
//        Icon(
//            painter = if (isPlaying) painterResource(R.drawable.pause) else painterResource(R.drawable.play),
//            contentDescription = null,
//            modifier = Modifier.size(50.dp)
//                .clickable { onClick() }
//                .padding(10.dp)
//        )
//
//        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_medium)))
//        Icon(
//            painter = painterResource(R.drawable.skip_forward),
//            contentDescription = null,
//            modifier = Modifier.size(50.dp)
//                .clickable { onNextSong(listOf()) }
//                .padding(10.dp)
//        )
//        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_medium)))
//        Icon(
//            painter = painterResource(R.drawable.repeat),
//            contentDescription = null,
//            modifier = Modifier.size(50.dp)
//                .padding(10.dp)
//                .clickable { onLoopSong(listOf()) },
//            tint = if (isLooping) MaterialTheme.colorScheme.surfaceVariant else Color.White
//        )
//        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_small)))
//    }
//}

