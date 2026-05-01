package com.example.pelis.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

// Esquema de colores oscuro (principal para la app de películas)
private val DarkColorScheme = darkColorScheme(
    primary = PrimaryRed,
    secondary = SecondaryGold,
    tertiary = PrimaryDarkRed,
    background = DarkBg,
    surface = CardBg,
    onPrimary = TextPrimary,
    onSecondary = TextPrimary,
    onBackground = TextPrimary,
    onSurface = TextSecondary
)

// Esquema de colores claro (alternativo)
private val LightColorScheme = lightColorScheme(
    primary = PrimaryRed,
    secondary = SecondaryGold,
    tertiary = PrimaryDarkRed
)

@Composable
fun PelisTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = false,  // Deshabilitado para mantener consistencia con tema de películas
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}