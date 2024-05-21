package com.example.musicapp.ui.register.view

import androidx.compose.foundation.layout.Column
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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.musicapp.R
import com.example.musicapp.common.FooterText
import com.example.musicapp.common.LoginwithGoogleButton
import com.example.musicapp.common.TitleText
import com.example.musicapp.ui.register.state.RegistrationUiEvent
import com.example.musicapp.ui.register.viewmodel.RegistrationViewModel
import com.example.musicapp.ui.theme.MusicAppTheme

@Composable
fun RegistrationScreen(
    registrationViewModel: RegistrationViewModel = viewModel(),
    onNavigateBack: () -> Unit,
    onNavigateToLoginScreen: () -> Unit,
    onLoginWithGoogle: () -> Unit
) {

    val registrationState by remember {
        registrationViewModel.registrationState
    }

    if (registrationState.isRegistrationSuccessful) {
        LaunchedEffect(key1 = true) {
            onNavigateToLoginScreen.invoke()
        }
    } else {
        // Full Screen Content
        Column(
            modifier = Modifier
                .fillMaxSize()
                .navigationBarsPadding()
                .imePadding()
                .verticalScroll(rememberScrollState())
        ) {

            // Back Button Icon
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

                // Heading Registration
                TitleText(
                    modifier = Modifier.padding(top = dimensionResource(R.dimen.padding_large)),
                    text = stringResource(id = R.string.registration_heading_text)
                )
                RegistrationInputs(
                    registrationState = registrationState,
                    onEmailIdChange = { inputString ->
                        registrationViewModel.onUiEvent(
                            registrationUiEvent = RegistrationUiEvent.EmailChanged(
                                inputValue = inputString
                            )
                        )
                    },
                    onPasswordChange = { inputString ->
                        registrationViewModel.onUiEvent(
                            registrationUiEvent = RegistrationUiEvent.PasswordChanged(
                                inputValue = inputString
                            )
                        )
                    },
                    onConfirmPasswordChange = { inputString ->
                        registrationViewModel.onUiEvent(
                            registrationUiEvent = RegistrationUiEvent.ConfirmPasswordChanged(
                                inputValue = inputString
                            )
                        )
                    },
                    onSubmit = {
                        registrationViewModel.onUiEvent(registrationUiEvent = RegistrationUiEvent.Submit)
                    }
                )
                FooterText(
                    modifier = Modifier
                        .padding(top = dimensionResource(R.dimen.padding_large))
                        .fillMaxWidth(),
                    text = stringResource(id = R.string.or_login_with_label),
                    textAlign = TextAlign.Center
                )
                LoginwithGoogleButton(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = dimensionResource(R.dimen.padding_medium)),
                    onClick = onLoginWithGoogle,
                    text = stringResource(R.string.login_with_google),
                    imageDescription = stringResource(R.string.google),
                    image = R.drawable.ic_google
                )
            }

        }
    }

}

@Preview(showBackground = true)
@Composable
fun PreviewRegistrationScreen() {
    MusicAppTheme {
        RegistrationScreen(onNavigateBack = {},
            onLoginWithGoogle = {},
            onNavigateToLoginScreen = {}
        )
    }
}