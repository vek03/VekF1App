package com.example.vekf1app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import com.example.vekf1app.ui.theme.VekF1AppTheme
import com.example.vekf1app.ui.navigation.AppNavigation

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            VekF1AppTheme {
                // Use a NavController to navigate between screens
                val navController = rememberNavController()
                AppNavigation(navController)
            }
        }
    }
}
