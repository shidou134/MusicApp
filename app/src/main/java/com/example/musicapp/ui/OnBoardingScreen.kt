package com.example.musicapp.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.musicapp.R
import com.example.musicapp.common.LoginButton
import com.example.musicapp.common.LoginwithGoogleButton
import com.example.musicapp.common.RegisterButton
import com.example.musicapp.common.SubcomposeRow

@Composable
fun OnBroadingScreen(
    onLoginClick: () -> Unit,
    onRegisterClick: () -> Unit,
    onLoginWithGoogleClick: () -> Unit,
    modifier: Modifier = Modifier
) {

    Column(
        modifier = Modifier.fillMaxSize()
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box {
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

        SubcomposeRow(
            modifier = Modifier.fillMaxWidth()
                .padding(top = 36.dp),
            paddingBetween = 16.dp
        ) {
            RegisterButton(
                onClick = onRegisterClick,
                text = stringResource(R.string.registration_button_text)
            )
            LoginButton(
                onClick = onLoginClick,
                text = stringResource(R.string.login_button_text)
            )
        }
        Spacer(modifier = Modifier.padding(vertical = 16.dp))
        LoginwithGoogleButton(
            onClick = onLoginWithGoogleClick,
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(R.string.login_with_google),
            imageDescription = stringResource(R.string.google),
            image = R.drawable.ic_google
        )
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun OnBroadingScreenPreview() {
    OnBroadingScreen(
        onLoginClick = {},
        onRegisterClick = {},
        onLoginWithGoogleClick = {},
        modifier = Modifier
            .fillMaxSize()
    )
}