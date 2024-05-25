package com.example.musicapp.ui.login.resetpassword.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.musicapp.common.emailEmptyErrorState
import com.example.musicapp.common.emailInvalidLoginErrorState
import com.example.musicapp.ui.ErrorState
import com.example.musicapp.ui.login.resetpassword.state.ResetPasswordErrorState
import com.example.musicapp.ui.login.resetpassword.state.ResetPasswordState
import com.example.musicapp.ui.login.resetpassword.state.ResetPasswordUiEvent
import com.example.musicapp.ui.login.state.LoginUiEvent
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

class ResetPasswordViewModel: ViewModel(){

    var resetPasswordState = mutableStateOf(ResetPasswordState())
        private set
    private val mAuth = Firebase.auth

    fun onUiEvent(resetPasswordUiEvent: ResetPasswordUiEvent) {
        when (resetPasswordUiEvent) {

            // Email/Mobile changed
            is ResetPasswordUiEvent.EmailChanged -> {
                resetPasswordState.value = resetPasswordState.value.copy(
                    email = resetPasswordUiEvent.inputValue,
                    errorState = resetPasswordState.value.errorState.copy(
                        emailErrorState = if (resetPasswordUiEvent.inputValue.trim().isNotEmpty())
                            ErrorState()
                        else
                            emailEmptyErrorState
                    )
                )
            }

            // Submit Login
            is ResetPasswordUiEvent.Submit -> {
                val inputsValidated = validateInputs()
                if (inputsValidated) {
                    resetPasswordState.value = resetPasswordState.value.copy(isSuccessful = true)
                }
            }
        }
    }

    private fun validateInputs(): Boolean {
        val emailString = resetPasswordState.value.email.trim()
        return when {

            emailString.isEmpty() -> {
                resetPasswordState.value = resetPasswordState.value.copy(
                    errorState = ResetPasswordErrorState(
                        emailErrorState = emailEmptyErrorState
                    )
                )
                false
            }
            else -> {
                mAuth.sendPasswordResetEmail(emailString).addOnSuccessListener{
                    resetPasswordState.value = resetPasswordState.value.copy(
                        isSuccessful = true
                    )
                }.addOnFailureListener {
                    resetPasswordState.value = resetPasswordState.value.copy(
                        errorState = ResetPasswordErrorState(
                            emailErrorState = emailInvalidLoginErrorState
                        )
                    )
                }
                resetPasswordState.value = resetPasswordState.value.copy(errorState = ResetPasswordErrorState())
                resetPasswordState.value.isSuccessful
            }
        }
    }
}