package com.example.musicapp.ui.login.resetpassword.state

import com.example.musicapp.ui.ErrorState
import com.example.musicapp.ui.login.state.LoginErrorState

data class ResetPasswordState(
    val email: String = "",
    val errorState: ResetPasswordErrorState = ResetPasswordErrorState(),
    val isSuccessful: Boolean = false
)

data class ResetPasswordErrorState(
    val emailErrorState: ErrorState = ErrorState()
)