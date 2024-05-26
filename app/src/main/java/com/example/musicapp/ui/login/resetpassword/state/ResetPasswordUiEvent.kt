package com.example.musicapp.ui.login.resetpassword.state

sealed class ResetPasswordUiEvent {
    data class EmailChanged(val inputValue: String) : ResetPasswordUiEvent()
    object Submit : ResetPasswordUiEvent()
}
