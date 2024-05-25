package com.example.musicapp.ui.login.resetpassword.state

import com.example.musicapp.R
import com.example.musicapp.ui.ErrorState
import com.example.musicapp.ui.login.state.LoginUiEvent

sealed class ResetPasswordUiEvent {
    data class EmailChanged(val inputValue: String) : ResetPasswordUiEvent()
    object Submit : ResetPasswordUiEvent()
}
