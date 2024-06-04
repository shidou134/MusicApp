package com.example.musicapp.graph

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.musicapp.MainDestinations
import com.example.musicapp.ui.OnBroadingScreen
import com.example.musicapp.ui.home.WelcomeScreen
import com.example.musicapp.ui.login.resetpassword.view.ResetPasswordScreen
import com.example.musicapp.ui.login.view.LoginScreen
import com.example.musicapp.ui.register.view.RegistrationScreen
import com.google.firebase.auth.FirebaseAuth

fun NavGraphBuilder.authNavGraph(navController: NavHostController) {
    val mAuth = FirebaseAuth.getInstance()
    navigation(
        startDestination = if (mAuth.currentUser == null) MainDestinations.OnBoarding.name else MainDestinations.Welcome.name,
        route = MainDestinations.Auth.name
    ) {
        composable(route = MainDestinations.OnBoarding.name) {
            OnBroadingScreen(
                onLoginClick = {
                    navController.navigate(route = MainDestinations.Login.name)
                },
                onRegisterClick = {
                    navController.navigate(route = MainDestinations.Register.name)
                },
                onLoginWithGoogleClick = {},
                modifier = Modifier.fillMaxSize()
            )
        }
        composable(route = MainDestinations.Login.name) {
            LoginScreen(
                onNavigateToRegistration = {
                    navController.navigate(route = MainDestinations.Register.name)
                },
                onLoginWithGoogle = {},
                onNavigateToForgotPassword = {
                    navController.navigate(route = MainDestinations.ResetPassword.name)
                },
                onNavigateToWelcomeScreen = {
                    navController.navigate(route = MainDestinations.Welcome.name)
                }
            )
        }
        composable(route = MainDestinations.Register.name) {
            RegistrationScreen(
                onLoginWithGoogle = {},
                onNavigateBack = {
                    navController.popBackStack()
                },
                onNavigateToLoginScreen = {
                    navController.navigate(route = MainDestinations.Login.name)
                }
            )
        }
        composable(route = MainDestinations.ResetPassword.name) {
            ResetPasswordScreen(
                onNavigateBack = {
                    navController.popBackStack()
                },
                onLoginWithGoogle = {},
                onNavigateToLoginScreen = {
                    navController.navigate(route = MainDestinations.Login.name)

                }
            )
        }
        composable(route = MainDestinations.Welcome.name) {
            WelcomeScreen(
                onWelcomeClick = {
                    navController.navigate(route = MainDestinations.MainApp.name) {
                        popUpTo(MainDestinations.Welcome.name) {
                            inclusive = true
                        }
                    }
                },
                modifier = Modifier.fillMaxSize()
            )
        }

    }
}