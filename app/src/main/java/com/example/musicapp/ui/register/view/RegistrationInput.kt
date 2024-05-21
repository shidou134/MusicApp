package com.example.musicapp.ui.register.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import com.example.musicapp.R
import com.example.musicapp.common.EmailTextField
import com.example.musicapp.common.LoginButton
import com.example.musicapp.common.MobileNumberTextField
import com.example.musicapp.common.PasswordTextField
import com.example.musicapp.ui.register.state.RegistrationState

@Composable
fun RegistrationInputs(
    registrationState: RegistrationState,
    onEmailIdChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onConfirmPasswordChange: (String) -> Unit,
    onSubmit: () -> Unit,
) {
    // Login Inputs Section
    Column(modifier = Modifier.fillMaxWidth()) {

        // Email ID
        EmailTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = dimensionResource(R.dimen.padding_large)),
            value = registrationState.emailId,
            onValueChange = onEmailIdChange,
            label = stringResource(id = R.string.registration_email_label),
            isError = registrationState.errorState.emailIdErrorState.hasError,
            errorText = stringResource(id = registrationState.errorState.emailIdErrorState.errorMessageStringResource),
            imeAction = ImeAction.Next
        )

        // Password
        PasswordTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = dimensionResource(R.dimen.padding_large)),
            value = registrationState.password,
            onValueChange = onPasswordChange,
            label = stringResource(id = R.string.login_password_label),
            isError = registrationState.errorState.passwordErrorState.hasError,
            errorText = stringResource(id = registrationState.errorState.passwordErrorState.errorMessageStringResource),
            imeAction = ImeAction.Next
        )

        // Confirm Password
        PasswordTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = dimensionResource(R.dimen.padding_large)),
            value = registrationState.confirmPassword,
            onValueChange = onConfirmPasswordChange,
            label = stringResource(id = R.string.registration_confirm_password_label),
            isError = registrationState.errorState.confirmPasswordErrorState.hasError,
            errorText = stringResource(id = registrationState.errorState.confirmPasswordErrorState.errorMessageStringResource),
            imeAction = ImeAction.Done
        )

        // Registration Submit Button
        LoginButton(
            modifier = Modifier.fillMaxWidth()
                .padding(top = dimensionResource(R.dimen.padding_medium)),
            text = stringResource(id = R.string.registration_create_account_label),
            onClick = onSubmit
        )


    }
}