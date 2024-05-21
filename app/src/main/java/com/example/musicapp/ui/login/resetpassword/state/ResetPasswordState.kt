package com.example.musicapp.ui.login.resetpassword.state

import com.example.musicapp.ui.ErrorState
import com.example.musicapp.ui.login.state.LoginErrorState

data class ResetPasswordState(
    val emailOrMobile: String = "",
    val errorState: ResetPasswordErrorState = ResetPasswordErrorState(),
    val isSuccessful: Boolean = false
)

data class ResetPasswordErrorState(
    val emailOrMobileErrorState: ErrorState = ErrorState()
)