package com.example.musicapp.ui.login.resetpassword.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.musicapp.ui.ErrorState
import com.example.musicapp.ui.login.resetpassword.state.ResetPasswordErrorState
import com.example.musicapp.ui.login.resetpassword.state.ResetPasswordState
import com.example.musicapp.ui.login.resetpassword.state.ResetPasswordUiEvent
import com.example.musicapp.ui.login.state.LoginUiEvent
import com.example.musicapp.ui.login.state.emailEmptyErrorState

class ResetPasswordViewModel: ViewModel(){

    var resetPasswordState = mutableStateOf(ResetPasswordState())
        private set

    /**
     * Function called on any login event [LoginUiEvent]
     */
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
                    // TODO Trigger login in authentication flow
                    resetPasswordState.value = resetPasswordState.value.copy(isSuccessful = true)
                }
            }
        }
    }

    /**
     * Function to validate inputs
     * Ideally it should be on domain layer (usecase)
     * @return true -> inputs are valid
     * @return false -> inputs are invalid
     */
    private fun validateInputs(): Boolean {
        val emailOrMobileString = resetPasswordState.value.email.trim()
        return when {

            // Email/Mobile empty
            emailOrMobileString.isEmpty() -> {
                resetPasswordState.value = resetPasswordState.value.copy(
                    errorState = ResetPasswordErrorState(
                        emailErrorState = emailEmptyErrorState
                    )
                )
                false
            }
            // No errors
            else -> {
                // Set default error state
                resetPasswordState.value = resetPasswordState.value.copy(errorState = ResetPasswordErrorState())
                true
            }
        }
    }
}