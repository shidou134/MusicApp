package com.example.musicapp.ui.register.state

import com.example.musicapp.ui.ErrorState

data class RegistrationState(
    val emailId: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val errorState: RegistrationErrorState = RegistrationErrorState(),
    val isRegistrationSuccessful: Boolean = false
)

/**
 * Error state in registration holding respective
 * text field validation errors
 */
data class RegistrationErrorState(
    val emailIdErrorState: ErrorState = ErrorState(),
    val passwordErrorState: ErrorState = ErrorState(),
    val confirmPasswordErrorState: ErrorState = ErrorState()
)