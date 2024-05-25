package com.example.musicapp.common

import com.example.musicapp.R
import com.example.musicapp.ui.ErrorState

val emailEmptyErrorState = ErrorState(
    hasError = true,
    errorMessageStringResource = R.string.registration_error_msg_empty_email
)

val emailInvalidRegistrationErrorState = ErrorState(
    hasError = true,
    errorMessageStringResource = R.string.registration_error_msg_invalid_email
)

val confirmPasswordEmptyErrorState = ErrorState(
    hasError = true,
    errorMessageStringResource = R.string.registration_error_msg_empty_confirm_password
)

val passwordInvalidErrorState = ErrorState(
    hasError = true,
    errorMessageStringResource = R.string.registration_error_msg_invalid_password
)

val passwordMismatchErrorState = ErrorState(
    hasError = true,
    errorMessageStringResource = R.string.registration_error_msg_password_mismatch
)

val emailInvalidLoginErrorState = ErrorState(
    hasError = true,
    errorMessageStringResource = R.string.login_error_msg_email_mismatch
)

val passwordEmptyErrorState = ErrorState(
    hasError = true,
    errorMessageStringResource = R.string.login_error_msg_empty_password
)