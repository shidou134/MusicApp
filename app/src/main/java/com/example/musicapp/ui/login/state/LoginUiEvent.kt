package com.example.musicapp.ui.login.state

import com.example.musicapp.R
import com.example.musicapp.ui.ErrorState

/**
 * Login Screen Events
 */
sealed class LoginUiEvent {
    data class EmailChanged(val inputValue: String) : LoginUiEvent()
    data class PasswordChanged(val inputValue: String) : LoginUiEvent()
    object Submit : LoginUiEvent()
}
val emailEmptyErrorState = ErrorState(
    hasError = true,
    errorMessageStringResource = R.string.registration_error_msg_empty_email
)

val passwordEmptyErrorState = ErrorState(
    hasError = true,
    errorMessageStringResource = R.string.login_error_msg_empty_password
)