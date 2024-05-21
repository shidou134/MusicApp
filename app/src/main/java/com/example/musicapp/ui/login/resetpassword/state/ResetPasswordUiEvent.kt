package com.example.musicapp.ui.login.resetpassword.state

import com.example.musicapp.R
import com.example.musicapp.ui.ErrorState
import com.example.musicapp.ui.login.state.LoginUiEvent

sealed class ResetPasswordUiEvent {
    data class EmailOrMobileChanged(val inputValue: String) : ResetPasswordUiEvent()
    object Submit : ResetPasswordUiEvent()
}
val emailOrMobileEmptyErrorState = ErrorState(
    hasError = true,
    errorMessageStringResource = R.string.login_error_msg_empty_email_mobile
)
