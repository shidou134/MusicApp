package com.example.musicapp.ui.mymusic.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.musicapp.R
import com.example.musicapp.common.HeaderSection
import com.example.musicapp.ui.mymusic.ActivityItemData
import com.example.musicapp.ui.theme.DarkBackground
import com.example.musicapp.ui.theme.Silver

@Composable
fun MyMusicScreen() {
    Column(
        modifier = Modifier
            .background(DarkBackground)
            .fillMaxSize()
            .padding(vertical = 32.dp)
            .verticalScroll(rememberScrollState())
    ) {
        HeaderSection("My Music", Modifier.padding(horizontal = 32.dp))
        SubtitleSection("Your Activities")
        ActivitySection()
    }
}

@Composable
private fun SubtitleSection(title: String) {
    Text(
        text = title,
        color = Color.LightGray,
        fontWeight = FontWeight(FontWeight.Thin.weight),
        modifier = Modifier.padding(vertical = 16.dp, horizontal = 32.dp)
    )
}


@Composable
private fun ActivitySection() {
    activityList().run {
        forEachIndexed { index, item ->
            ActivityItem(icon = item.icon, title = item.title)
            if (index != lastIndex) {
                Divider(
                    color = Silver.copy(alpha = 0.12f),
                    thickness = 1.dp,
                    modifier = Modifier.padding(top = 8.dp, bottom = 8.dp)
                )
            }
        }
    }
}

@Composable
private fun ActivityItem(icon: Int, title: String) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.padding(vertical = 16.dp, horizontal = 32.dp)
    ) {
        Icon(
            painter = painterResource(id = icon),
            contentDescription = title,
            tint = Silver
        )
        Text(
            text = title,
            color = Silver,
            modifier = Modifier
                .weight(1f)
                .padding(start = 16.dp)
        )
        Icon(
            painter = painterResource(id = R.drawable.ic_right_chevron),
            contentDescription = title,
            tint = Silver,
            modifier = Modifier.size(12.dp)
        )
    }
}

@Composable
private fun activityList(): List<ActivityItemData> {
    return listOf(
        ActivityItemData(stringResource(R.string.my_music_your_playlists_title), R.drawable.ic_playlist),
        ActivityItemData(stringResource(R.string.my_music_liked_songs_title), R.drawable.ic_like),
        ActivityItemData(stringResource(R.string.my_music_follower_artists_title), R.drawable.ic_artist),
        ActivityItemData(stringResource(R.string.my_music_history_title), R.drawable.ic_history),
        ActivityItemData(stringResource(R.string.my_music_log_out_title), androidx.media3.ui.R.drawable.exo_ic_fullscreen_exit)
    )
}

@Composable
@Preview
fun PreviewMyMusicScreen() {
    MyMusicScreen()
}