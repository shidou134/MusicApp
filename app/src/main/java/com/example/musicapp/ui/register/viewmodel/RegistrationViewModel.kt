package com.example.musicapp.ui.register.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.musicapp.common.confirmPasswordEmptyErrorState
import com.example.musicapp.common.emailEmptyErrorState
import com.example.musicapp.common.emailInvalidRegistrationErrorState
import com.example.musicapp.common.passwordEmptyErrorState
import com.example.musicapp.common.passwordInvalidErrorState
import com.example.musicapp.common.passwordMismatchErrorState
import com.example.musicapp.common.ErrorState
import com.example.musicapp.ui.register.state.RegistrationErrorState
import com.example.musicapp.ui.register.state.RegistrationState
import com.example.musicapp.ui.register.state.RegistrationUiEvent
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore

class RegistrationViewModel : ViewModel() {

    var registrationState = mutableStateOf(RegistrationState())
        private set
    private val mAuth = Firebase.auth
    private val db = Firebase.firestore

    fun onUiEvent(registrationUiEvent: RegistrationUiEvent) {
        when (registrationUiEvent) {

            // Email id changed event
            is RegistrationUiEvent.EmailChanged -> {
                registrationState.value = registrationState.value.copy(
                    emailId = registrationUiEvent.inputValue,
                    errorState = registrationState.value.errorState.copy(
                        emailIdErrorState = if (registrationUiEvent.inputValue.trim().isEmpty()) {
                            // Email id empty state
                            emailEmptyErrorState
                        } else {
                            // Valid state
                            ErrorState()
                        }

                    )
                )
            }

            is RegistrationUiEvent.PasswordChanged -> {
                registrationState.value = registrationState.value.copy(
                    password = registrationUiEvent.inputValue,
                    errorState = registrationState.value.errorState.copy(
                        passwordErrorState = if (registrationUiEvent.inputValue.trim().isEmpty()) {
                            // Password Empty state
                            passwordEmptyErrorState
                        } else {
                            // Valid state
                            ErrorState()
                        }

                    )
                )
            }

            is RegistrationUiEvent.ConfirmPasswordChanged -> {
                registrationState.value = registrationState.value.copy(
                    confirmPassword = registrationUiEvent.inputValue,
                    errorState = registrationState.value.errorState.copy(
                        confirmPasswordErrorState = when {

                            registrationUiEvent.inputValue.trim().isEmpty() -> {
                                confirmPasswordEmptyErrorState
                            }

                            registrationState.value.password.trim() != registrationUiEvent.inputValue -> {
                                passwordMismatchErrorState
                            }

                            else -> ErrorState()
                        }
                    )
                )
            }


            is RegistrationUiEvent.Submit -> {
                val inputsValidated = validateInputs()
                if (inputsValidated) {
                    registrationState.value =
                        registrationState.value.copy(isRegistrationSuccessful = true)
                }
            }
        }
    }

    private fun isEmailValid(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    private fun validateInputs(): Boolean {
        val emailString = registrationState.value.emailId.trim()
        val passwordString = registrationState.value.password.trim()
        val confirmPasswordString = registrationState.value.confirmPassword.trim()

        return when {
            emailString.isEmpty() -> {
                registrationState.value = registrationState.value.copy(
                    errorState = RegistrationErrorState(
                        emailIdErrorState = emailEmptyErrorState
                    )
                )
                false
            }

            !isEmailValid(emailString) -> {
                registrationState.value = registrationState.value.copy(
                    errorState = RegistrationErrorState(
                        emailIdErrorState = emailInvalidRegistrationErrorState
                    )
                )
                false
            }

            passwordString.isEmpty() -> {
                registrationState.value = registrationState.value.copy(
                    errorState = RegistrationErrorState(
                        passwordErrorState = passwordEmptyErrorState
                    )
                )
                false
            }

            !passwordString.matches("^(?=\\S+\$).{8,}\$".toRegex()) -> {
                registrationState.value = registrationState.value.copy(
                    errorState = RegistrationErrorState(
                        passwordErrorState = passwordInvalidErrorState
                    )
                )
                false
            }

            confirmPasswordString.isEmpty() -> {
                registrationState.value = registrationState.value.copy(
                    errorState = RegistrationErrorState(
                        confirmPasswordErrorState = confirmPasswordEmptyErrorState
                    )
                )
                false
            }

            passwordString != confirmPasswordString -> {
                registrationState.value = registrationState.value.copy(
                    errorState = RegistrationErrorState(
                        confirmPasswordErrorState = passwordMismatchErrorState
                    )
                )
                false
            }

            else -> {
                mAuth.createUserWithEmailAndPassword(emailString, passwordString)
                val user: HashMap<String, Any> = HashMap()
                user["email"] = emailString
                user["password"] = passwordString
                db.collection("users")
                    .document(mAuth.currentUser?.uid ?: "")
                    .set(user)
                    .addOnSuccessListener {
                        registrationState.value = registrationState.value.copy(
                            isRegistrationSuccessful = true
                        )
                    }
                    .addOnFailureListener {

                    }
                registrationState.value =
                    registrationState.value.copy(errorState = RegistrationErrorState())
                registrationState.value.isRegistrationSuccessful
            }
        }
    }

}