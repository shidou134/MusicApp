package com.example.musicapp.ui.register.state

import com.example.musicapp.R
import com.example.musicapp.ui.ErrorState

sealed class RegistrationUiEvent {
    data class EmailChanged(val inputValue: String) : RegistrationUiEvent()
    data class PasswordChanged(val inputValue: String) : RegistrationUiEvent()
    data class ConfirmPasswordChanged(val inputValue: String) : RegistrationUiEvent()
    object Submit : RegistrationUiEvent()
}
val emailEmptyErrorState = ErrorState(
    hasError = true,
    errorMessageStringResource = R.string.registration_error_msg_empty_email
)

val emailInvalidErrorState = ErrorState(
    hasError = true,
    errorMessageStringResource = R.string.registration_error_msg_invalid_email
)

val confirmPasswordEmptyErrorState = ErrorState(
    hasError = true,
    errorMessageStringResource = R.string.registration_error_msg_empty_confirm_password
)

val passwordInvalidErrorState =ErrorState(
    hasError = true,
    errorMessageStringResource = R.string.registration_error_msg_invalid_password
)

val passwordMismatchErrorState = ErrorState(
    hasError = true,
    errorMessageStringResource = R.string.registration_error_msg_password_mismatch
)