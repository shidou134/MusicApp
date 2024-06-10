package com.example.musicapp.ui.login.resetpassword.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.musicapp.R
import com.example.musicapp.common.FooterText
import com.example.musicapp.common.LoginwithGoogleButton
import com.example.musicapp.common.MediumTitleText
import com.example.musicapp.ui.login.resetpassword.state.ResetPasswordUiEvent
import com.example.musicapp.ui.login.resetpassword.viewmodel.ResetPasswordViewModel
import com.example.musicapp.ui.login.state.LoginUiEvent
import com.example.musicapp.ui.login.view.LoginInputs
import com.example.musicapp.ui.login.viewmodel.LoginViewModel
import com.example.musicapp.ui.theme.MusicAppTheme

@Composable
fun ResetPasswordScreen(
    resetPasswordViewModel: ResetPasswordViewModel = viewModel(),
    onNavigateToLoginScreen: () -> Unit,
    onNavigateBack: () -> Unit,
    onLoginWithGoogle: () -> Unit
) {

    val resetPasswordState by remember {
        resetPasswordViewModel.resetPasswordState
    }

    if (resetPasswordState.isSuccessful) {
        LaunchedEffect(key1 = true) {
            onNavigateToLoginScreen.invoke()
        }
    } else {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .navigationBarsPadding()
                .imePadding()
                .verticalScroll(rememberScrollState())
        ) {
            IconButton(
                modifier = Modifier
                    .padding(horizontal = dimensionResource(R.dimen.padding_medium))
                    .padding(top = dimensionResource(R.dimen.padding_large)),
                onClick = onNavigateBack,
                content = {
                    Icon(
                        imageVector = Icons.Outlined.ArrowBack,
                        contentDescription = stringResource(id = R.string.button_back)
                    )
                }
            )

            Column(
                modifier = Modifier
                    .padding(horizontal = dimensionResource(R.dimen.padding_large))
                    .padding(bottom = dimensionResource(R.dimen.padding_extra_large))
            ) {

                // Heading Jetpack Compose
                MediumTitleText(
                    modifier = Modifier
                        .padding(top = dimensionResource(R.dimen.padding_large))
                        .fillMaxWidth(),
                    text = stringResource(id = R.string.forgot_password),
                    textAlign = TextAlign.Center
                )

                // Login Inputs Composable
                ResetPasswordInput(
                    resetPasswordState = resetPasswordState,
                    onEmailOrMobileChange = { inputString ->
                        resetPasswordViewModel.onUiEvent(
                            resetPasswordUiEvent = ResetPasswordUiEvent.EmailChanged(
                                inputString
                            )
                        )
                    },
                    onSubmit = {
                        resetPasswordViewModel.onUiEvent(resetPasswordUiEvent = ResetPasswordUiEvent.Submit)
                    }
                )

            }


//                FooterText(
//                    modifier = Modifier
//                        .padding(top = dimensionResource(R.dimen.padding_small))
//                        .fillMaxWidth(),
//                    text = stringResource(id = R.string.or_login_with_label),
//                    textAlign = TextAlign.Center
//                )
//                LoginwithGoogleButton(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(horizontal = dimensionResource(R.dimen.padding_large))
//                        .padding(top = dimensionResource(R.dimen.padding_medium)),
//                    onClick = onLoginWithGoogle,
//                    text = stringResource(R.string.login_with_google),
//                    imageDescription = stringResource(R.string.google),
//                    image = R.drawable.ic_google
//                )


        }

    }

}

@Preview(showBackground = true)
@Composable
fun PreviewLoginScreen() {
    MusicAppTheme {
        ResetPasswordScreen(
            onNavigateToLoginScreen = {},
            onLoginWithGoogle = {},
            onNavigateBack = {}
        )
    }
}