package com.example.musicapp.ui.login.state

/**
 * Login Screen Events
 */
sealed class LoginUiEvent {
    data class EmailChanged(val inputValue: String) : LoginUiEvent()
    data class PasswordChanged(val inputValue: String) : LoginUiEvent()
    object Submit : LoginUiEvent()
}

