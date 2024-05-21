package com.example.musicapp.ui.login.resetpassword.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import com.example.musicapp.R
import com.example.musicapp.common.EmailTextField
import com.example.musicapp.common.LoginButton
import com.example.musicapp.ui.login.resetpassword.state.ResetPasswordState

@Composable
fun ResetPasswordInput(
    resetPasswordState: ResetPasswordState,
    onEmailOrMobileChange: (String) -> Unit,
    onSubmit: () -> Unit
) {

    // Login Inputs Section
    Column(modifier = Modifier.fillMaxWidth()) {

        // Email or Mobile Number
        EmailTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = dimensionResource(R.dimen.padding_large)),
            value = resetPasswordState.emailOrMobile,
            onValueChange = onEmailOrMobileChange,
            label = stringResource(id = R.string.login_email_id_or_phone_label),
            isError = resetPasswordState.errorState.emailOrMobileErrorState.hasError,
            errorText = stringResource(id = resetPasswordState.errorState.emailOrMobileErrorState.errorMessageStringResource)
        )

        LoginButton(
            modifier = Modifier.fillMaxWidth()
                .padding(top = dimensionResource(R.dimen.padding_medium)),
            text = stringResource(id = R.string.reset_password),
            onClick = onSubmit
        )

    }
}