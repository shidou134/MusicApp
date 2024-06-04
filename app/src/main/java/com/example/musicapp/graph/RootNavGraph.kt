package com.example.musicapp.graph

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.musicapp.MainDestinations

@Composable
fun RootNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        route = MainDestinations.Root.name,
        startDestination = MainDestinations.Auth.name
    ){
        authNavGraph(navController = navController)
        composable(route = MainDestinations.MainApp.name){
            MainScreen()
        }

    }
}