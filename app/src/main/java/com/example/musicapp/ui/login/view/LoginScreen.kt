package com.example.musicapp.ui.login.view

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
import com.example.musicapp.ui.login.state.LoginUiEvent
import com.example.musicapp.ui.login.viewmodel.LoginViewModel
import com.example.musicapp.ui.theme.MusicAppTheme

@Composable
fun LoginScreen(
    loginViewModel: LoginViewModel = viewModel(),
    onNavigateToRegistration: () -> Unit,
    onNavigateToForgotPassword: () -> Unit,
    onNavigateToWelcomeScreen: () -> Unit,
    onLoginWithGoogle: () -> Unit
) {

    val loginState by remember {
        loginViewModel.loginState
    }

    if (loginState.isLoginSuccessful) {
        LaunchedEffect(key1 = true) {
            onNavigateToWelcomeScreen.invoke()
        }
    } else {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .navigationBarsPadding()
                .imePadding()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

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
                    text = stringResource(id = R.string.login_button_text),
                    textAlign = TextAlign.Center
                )

                // Login Inputs Composable
                LoginInputs(
                    loginState = loginState,
                    onEmailOrMobileChange = { inputString ->
                        loginViewModel.onUiEvent(
                            loginUiEvent = LoginUiEvent.EmailChanged(
                                inputString
                            )
                        )
                    },
                    onPasswordChange = { inputString ->
                        loginViewModel.onUiEvent(
                            loginUiEvent = LoginUiEvent.PasswordChanged(
                                inputString
                            )
                        )
                    },
                    onSubmit = {
                        loginViewModel.onUiEvent(loginUiEvent = LoginUiEvent.Submit)
                    },
                    onForgotPasswordClick = onNavigateToForgotPassword
                )

            }


            // Register Section
            Row(
                modifier = Modifier.padding(dimensionResource(R.dimen.padding_medium)),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Don't have an account?
                Text(text = stringResource(id = R.string.do_not_have_account))

                //Register
                Text(
                    modifier = Modifier
                        .padding(start = dimensionResource(R.dimen.padding_extra_small))
                        .clickable {
                            onNavigateToRegistration.invoke()
                        },
                    text = stringResource(id = R.string.registration_button_text),
                    color = MaterialTheme.colorScheme.primary
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
        LoginScreen(
            onNavigateToForgotPassword = {},
            onNavigateToRegistration = {},
            onNavigateToWelcomeScreen = {},
            onLoginWithGoogle = {}
        )
    }
}