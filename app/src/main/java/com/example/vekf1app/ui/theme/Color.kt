package com.example.vekf1app.ui.theme

import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color

val Purple80 = Color(0xFFD0BCFF)
val PurpleGrey80 = Color(0xFFCCC2DC)
val Pink80 = Color(0xFFEFB8C8)

val Purple40 = Color(0xFF6650a4)
val PurpleGrey40 = Color(0xFF625b71)
val Pink40 = Color(0xFF7D5260)

val glitterGold = Color(0xFFFFD700)
val redF1 = Color(0xFF800303)
val darkTheme = Color(0xFF49473F)
val darkLightTheme = Color(0xFF55544C)
val darkBlack = Color(0xFF000000)

val CustomColorScheme = lightColorScheme(
    primary = darkLightTheme,
    onPrimary = glitterGold,
    primaryContainer = darkTheme,
    onPrimaryContainer = glitterGold,
    secondaryContainer = darkLightTheme,
    onSecondaryContainer = glitterGold,
    secondary = glitterGold,
    onSecondary = darkLightTheme,
    error = Color.Red,
    onError = Color.White,
    errorContainer = Color.Red,
    background = darkBlack,
)