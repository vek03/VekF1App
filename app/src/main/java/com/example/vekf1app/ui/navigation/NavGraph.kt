package com.example.vekf1app.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.google.firebase.auth.FirebaseAuth
import com.example.vekf1app.ui.auth.LoginScreen
import com.example.vekf1app.ui.auth.RegisterScreen
import com.example.vekf1app.ui.auth.WelcomeScreen

@Composable
fun AppNavigation(navController: NavHostController) {
    val auth = FirebaseAuth.getInstance()
    val currentUser = auth.currentUser

    NavHost(navController, startDestination = if (currentUser != null) "welcome" else "login") {
        composable("login") {
            LoginScreen(
                onLoginSuccess = {
                    navController.navigate("welcome") {
                        popUpTo("login") { inclusive = true }
                    }
                },
                onNavigateToRegister = {
                    navController.navigate("register")
                }
            )
        }
        composable("register") {
            RegisterScreen(
                onRegisterSuccess = {
                    navController.navigate("welcome") {
                        popUpTo("register") { inclusive = true }
                    }
                },
                onNavigateToLogin = {
                    navController.navigate("login")
                }
            )
        }
        composable("welcome") {
            WelcomeScreen(onLogout = {
                navController.navigate("login") {
                    popUpTo("welcome") { inclusive = true }
                }
            })
        }
    }
}
