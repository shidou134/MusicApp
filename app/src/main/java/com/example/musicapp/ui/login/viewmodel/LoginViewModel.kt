package com.example.musicapp.ui.login.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.musicapp.common.emailEmptyErrorState
import com.example.musicapp.common.emailInvalidLoginErrorState
import com.example.musicapp.common.passwordEmptyErrorState
import com.example.musicapp.ui.ErrorState
import com.example.musicapp.ui.login.state.LoginErrorState
import com.example.musicapp.ui.login.state.LoginState
import com.example.musicapp.ui.login.state.LoginUiEvent
import com.google.firebase.Firebase
import com.google.firebase.auth.auth

class LoginViewModel : ViewModel() {

    var loginState = mutableStateOf(LoginState())
        private set
    private val mAuth = Firebase.auth
    fun onUiEvent(loginUiEvent: LoginUiEvent) {
        when (loginUiEvent) {

            is LoginUiEvent.EmailChanged -> {
                loginState.value = loginState.value.copy(
                    email = loginUiEvent.inputValue,
                    errorState = loginState.value.errorState.copy(
                        emailErrorState = if (loginUiEvent.inputValue.trim().isNotEmpty())
                            ErrorState()
                        else
                            emailEmptyErrorState
                    )
                )
            }

            is LoginUiEvent.PasswordChanged -> {
                loginState.value = loginState.value.copy(
                    password = loginUiEvent.inputValue,
                    errorState = loginState.value.errorState.copy(
                        passwordErrorState = if (loginUiEvent.inputValue.trim().isNotEmpty())
                            ErrorState()
                        else
                            passwordEmptyErrorState
                    )
                )
            }

            is LoginUiEvent.Submit -> {
                val inputsValidated = validateInputs()
                if (inputsValidated) {
                    loginState.value = loginState.value.copy(isLoginSuccessful = true)
                }
            }
        }
    }

    private fun validateInputs(): Boolean {
        val emailString = loginState.value.email.trim()
        val passwordString = loginState.value.password
        return when {

            emailString.isEmpty() -> {
                loginState.value = loginState.value.copy(
                    errorState = LoginErrorState(
                        emailErrorState = emailEmptyErrorState
                    )
                )
                false
            }

            passwordString.isEmpty() -> {
                loginState.value = loginState.value.copy(
                    errorState = LoginErrorState(
                        passwordErrorState = passwordEmptyErrorState
                    )
                )
                false
            }

            else -> {
                mAuth.signInWithEmailAndPassword(emailString, passwordString).addOnSuccessListener {
                    loginState.value = loginState.value.copy(isLoginSuccessful = true)
                }.addOnFailureListener {
                    loginState.value = loginState.value.copy(
                        isLoginSuccessful = false,
                        errorState = LoginErrorState(
                            emailErrorState = emailInvalidLoginErrorState
                        )
                    )
                }
                loginState.value = loginState.value.copy(errorState = LoginErrorState())
                loginState.value.isLoginSuccessful
            }
        }
    }

}