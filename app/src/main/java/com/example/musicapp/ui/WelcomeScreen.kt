package com.example.musicapp.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.musicapp.R

@Composable
fun WelcomeScreen(
    onWelcomeClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.background(color = Color.Black),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box() {
            Image(
                painter = painterResource(R.drawable.logo),
                contentDescription = stringResource(R.string.logo),
                modifier = Modifier
                    .size(300.dp)
            )
            Image(
                painter = painterResource(R.drawable.musium),
                contentDescription = stringResource(R.string.musium),
                modifier = Modifier
                    .size(200.dp)
                    .padding(top = 100.dp)
                    .align(Alignment.BottomCenter)
            )
        }

        ElevatedButton(
            onClick = onWelcomeClick,
            modifier = Modifier
                .padding(top = 200.dp)
                .width(300.dp),
        ) {
            Text(
                text = stringResource(R.string.welcome),
                style = MaterialTheme.typography.labelSmall,
                modifier = Modifier.align(Alignment.CenterVertically)
            )
        }

    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun WelcomeScreenPreview() {
    WelcomeScreen(
        onWelcomeClick = {},
        modifier = Modifier
            .fillMaxSize()
    )
}