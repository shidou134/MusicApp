package com.example.musicapp.ui.register.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.musicapp.ui.ErrorState
import com.example.musicapp.ui.login.state.passwordEmptyErrorState
import com.example.musicapp.ui.register.state.RegistrationErrorState
import com.example.musicapp.ui.register.state.RegistrationState
import com.example.musicapp.ui.register.state.RegistrationUiEvent
import com.example.musicapp.ui.register.state.confirmPasswordEmptyErrorState
import com.example.musicapp.ui.register.state.emailEmptyErrorState
import com.example.musicapp.ui.register.state.emailInvalidErrorState
import com.example.musicapp.ui.register.state.passwordInvalidErrorState
import com.example.musicapp.ui.register.state.passwordMismatchErrorState
import com.google.firebase.auth.FirebaseAuth

class RegistrationViewModel : ViewModel() {

    var registrationState = mutableStateOf(RegistrationState())
        private set
    private var mAuth = FirebaseAuth.getInstance()

    /**
     * Function called on any login event [RegistrationUiEvent]
     */
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

            // Password changed event
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

            // Confirm Password changed event
            is RegistrationUiEvent.ConfirmPasswordChanged -> {
                registrationState.value = registrationState.value.copy(
                    confirmPassword = registrationUiEvent.inputValue,
                    errorState = registrationState.value.errorState.copy(
                        confirmPasswordErrorState = when {

                            // Empty state of confirm password
                            registrationUiEvent.inputValue.trim().isEmpty() -> {
                                confirmPasswordEmptyErrorState
                            }

                            // Password is different than the confirm password
                            registrationState.value.password.trim() != registrationUiEvent.inputValue -> {
                                passwordMismatchErrorState
                            }

                            // Valid state
                            else -> ErrorState()
                        }
                    )
                )
            }


            // Submit Registration event
            is RegistrationUiEvent.Submit -> {
                val inputsValidated = validateInputs()
                if (inputsValidated) {
                    // TODO Trigger registration in authentication flow
                    registrationState.value =
                        registrationState.value.copy(isRegistrationSuccessful = true)
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
        val emailString = registrationState.value.emailId.trim()
        val passwordString = registrationState.value.password.trim()
        val confirmPasswordString = registrationState.value.confirmPassword.trim()

//        if(emailString.matches("${"a-zA-Z0-9._-} +@[a-z]+\\.+[a-z]+"))

        if (emailString.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\$".toRegex())) {
                if (passwordString.matches("^(?=\\S+\$).{8,}\$".toRegex())) {
                    mAuth.createUserWithEmailAndPassword(emailString, passwordString).addOnSuccessListener{
                        registrationState.value = registrationState.value.copy(isRegistrationSuccessful = true)
                    }.addOnFailureListener{
                        registrationState.value = registrationState.value.copy(isRegistrationSuccessful = false)
                    }

                } else {
                    registrationState.value = registrationState.value.copy(
                        errorState = RegistrationErrorState(
                            passwordErrorState = passwordInvalidErrorState
                        )
                    )
                    return false
                }
        } else {
            registrationState.value = registrationState.value.copy(
                errorState = RegistrationErrorState(
                    emailIdErrorState = emailInvalidErrorState
                )
            )
            return false
        }

        return when {
            // Email empty
            emailString.isEmpty() -> {
                registrationState.value = registrationState.value.copy(
                    errorState = RegistrationErrorState(
                        emailIdErrorState = emailEmptyErrorState
                    )
                )
                false
            }

            //Password Empty
            passwordString.isEmpty() -> {
                registrationState.value = registrationState.value.copy(
                    errorState = RegistrationErrorState(
                        passwordErrorState = passwordEmptyErrorState
                    )
                )
                false
            }

            //Confirm Password Empty
            confirmPasswordString.isEmpty() -> {
                registrationState.value = registrationState.value.copy(
                    errorState = RegistrationErrorState(
                        confirmPasswordErrorState = confirmPasswordEmptyErrorState
                    )
                )
                false
            }

            // Password and Confirm Password are different
            passwordString != confirmPasswordString -> {
                registrationState.value = registrationState.value.copy(
                    errorState = RegistrationErrorState(
                        confirmPasswordErrorState = passwordMismatchErrorState
                    )
                )
                false
            }

            // No errors
            else -> {
                // Set default error state
                registrationState.value =
                    registrationState.value.copy(errorState = RegistrationErrorState())
                true
            }
        }
    }
}