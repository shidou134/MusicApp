package com.example.musicapp.ui.radio.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
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
import com.example.musicapp.modelresponse.radio.Data
import com.example.musicapp.ui.radio.viewmodel.RadiosUiState
import com.example.musicapp.ui.theme.DarkBackground
import com.example.musicapp.ui.theme.Silver

@Composable
fun RadioScreen(
    radioState: RadiosUiState,
    retryAction: () -> Unit,
    onNavigateToTracks: (Long) -> Unit,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp)
) {
    when (radioState) {
        is RadiosUiState.Loading -> LoadingScreen(modifier = modifier.fillMaxSize())
        is RadiosUiState.Success -> ListRadios(
            radios = radioState.radio.data,
            onNavigateToTracks = onNavigateToTracks,
            modifier = modifier.fillMaxSize()
        )

        is RadiosUiState.Error -> ErrorScreen(
            retryAction = retryAction,
            modifier = modifier.fillMaxSize()
        )
    }
}

@Composable
fun RadioPhoto(
    radioImg: String,
    modifier: Modifier = Modifier
) {
    AsyncImage(
        model = ImageRequest.Builder(context = LocalContext.current)
            .data(radioImg)
            .crossfade(true)
            .build(),
        contentDescription = stringResource(R.string.radio_name),
        error = painterResource(R.drawable.ic_connection_error),
        placeholder = painterResource(R.drawable.loading_img),
        modifier = modifier.clip(CircleShape)
    )

}

@Composable
fun RadioCard(
    radio: Data,
    onNavigateToTracks: (Long) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable {
                onNavigateToTracks(radio.id ?: 0)
            }
    ) {
        RadioPhoto(
            radioImg = radio.pictureMedium ?: ""
        )
        Spacer(modifier = Modifier.width(20.dp))
        Text(
            text = radio.title ?: "",
            style = MaterialTheme.typography.labelSmall,
            color = Silver,
            modifier = Modifier.padding(horizontal = 8.dp)
                .align(Alignment.CenterVertically)
        )
        Spacer(modifier = Modifier.width(8.dp))
    }

}

@Composable
fun ListRadios(
    radios: List<Data?>?,
    onNavigateToTracks: (Long) -> Unit,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp)
) {
    Column(
        modifier = Modifier
            .background(DarkBackground)
            .fillMaxSize()
            .padding(top = 32.dp)
    ) {
        HeaderSection("Radio", Modifier.padding(horizontal = 32.dp))
        LazyColumn(
            modifier = modifier.padding(horizontal = 16.dp, vertical = 16.dp),
            contentPadding = contentPadding
        ) {
            items(items = radios ?: emptyList()) { data ->
                RadioCard(
                    radio = data!!,
                    onNavigateToTracks = onNavigateToTracks,
                    modifier = modifier.fillMaxWidth()
                )
            }
        }
    }
}