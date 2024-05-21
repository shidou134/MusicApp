package com.example.musicapp.ui.login.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import com.example.musicapp.R
import com.example.musicapp.common.EmailTextField
import com.example.musicapp.common.LoginButton
import com.example.musicapp.common.PasswordTextField
import com.example.musicapp.ui.login.state.LoginState

@Composable
fun LoginInputs(
    loginState: LoginState,
    onEmailOrMobileChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onSubmit: () -> Unit,
    onForgotPasswordClick: () -> Unit
) {

    // Login Inputs Section
    Column(modifier = Modifier.fillMaxWidth()) {

        // Email or Mobile Number
        EmailTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = dimensionResource(R.dimen.padding_large)),
            value = loginState.email,
            onValueChange = onEmailOrMobileChange,
            label = stringResource(id = R.string.registration_email_label),
            isError = loginState.errorState.emailErrorState.hasError,
            errorText = stringResource(id = loginState.errorState.emailErrorState.errorMessageStringResource)
        )


        // Password
        PasswordTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = dimensionResource(R.dimen.padding_large)),
            value = loginState.password,
            onValueChange = onPasswordChange,
            label = stringResource(id = R.string.login_password_label),
            isError = loginState.errorState.passwordErrorState.hasError,
            errorText = stringResource(id = loginState.errorState.passwordErrorState.errorMessageStringResource),
            imeAction = ImeAction.Done
        )

        // Forgot Password
        Text(
            modifier = Modifier
                .padding(top = dimensionResource(R.dimen.padding_small))
                .align(alignment = Alignment.End)
                .clickable {
                    onForgotPasswordClick.invoke()
                },
            text = stringResource(id = R.string.forgot_password),
            color = MaterialTheme.colorScheme.secondary,
            textAlign = TextAlign.End,
            style = MaterialTheme.typography.bodyMedium
        )

        LoginButton(
            modifier = Modifier.fillMaxWidth()
                .padding(top = dimensionResource(R.dimen.padding_medium)),
            text = stringResource(id = R.string.login_button_text),
            onClick = onSubmit
        )

    }
}