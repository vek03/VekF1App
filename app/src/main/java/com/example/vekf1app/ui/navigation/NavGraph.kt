package com.example.vekf1app.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.google.firebase.auth.FirebaseAuth
import com.example.vekf1app.ui.auth.LoginScreen
import com.example.vekf1app.ui.auth.RegisterScreen
import com.example.vekf1app.ui.auth.DashboardScreen
import com.example.vekf1app.ui.pilots.CreatePilotScreen
import com.example.vekf1app.ui.pilots.EditPilotScreen
import com.example.vekf1app.ui.pilots.ListPilotsScreen

@Composable
fun AppNavigation(navController: NavHostController) {
    val auth = FirebaseAuth.getInstance()
    val currentUser = auth.currentUser

    NavHost(navController, startDestination = if (currentUser != null) "dashboard" else "login") {
        composable("login") {
            LoginScreen(
                onLoginSuccess = {
                    navController.navigate("dashboard") {
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
                    navController.navigate("dashboard") {
                        popUpTo("register") { inclusive = true }
                    }
                },
                onNavigateToLogin = {
                    navController.navigate("login")
                }
            )
        }
        composable("dashboard") {
            DashboardScreen(
                onLogout = {
                    navController.navigate("login") {
                        popUpTo("dashboard") { inclusive = true }
                    }
                },
                onNavigateToListPilots = {
                    navController.navigate("listPilots")
                }
            )
        }
        composable("listPilots") {
            ListPilotsScreen(
                onNavigateToDashboard = {
                    navController.navigate("dashboard") {
                        popUpTo("dashboard") { inclusive = true }
                    }
                },
                onNavigateToCreatePilot = {
                    navController.navigate("createPilot") {
                        popUpTo("createPilot") { inclusive = true }
                    }
                },
                onNavigateToPilotUpdate = { pilotId ->
                    navController.navigate("editPilot/$pilotId")
                }
            )
        }
        composable("createPilot") {
            CreatePilotScreen(
                onNavigateToListPilots = {
                    navController.navigate("listPilots") {
                        popUpTo("listPilots") { inclusive = true }
                    }
                }
            )
        }
        composable("editPilot/{pilotId}") {
            EditPilotScreen(
                onNavigateToListPilots = {
                    navController.navigate("listPilots") {
                        popUpTo("listPilots") { inclusive = true }
                    }
                },
                pilotId = it.arguments?.getString("pilotId") ?: ""
            )
        }
    }
}