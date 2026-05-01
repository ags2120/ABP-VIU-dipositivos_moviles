package com.example.pelis

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.example.pelis.data.repository.DatabaseHelper
import com.example.pelis.data.repository.MovieRepository
import com.example.pelis.ui.screens.addmovie.AddMovieScreen
import com.example.pelis.ui.screens.landing.LandingScreen
import com.example.pelis.ui.screens.movies.MoviesScreen
import com.example.pelis.ui.theme.PelisTheme
import com.example.pelis.viewmodel.AddMovieViewModel
import com.example.pelis.viewmodel.MoviesViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PelisTheme {
                val context = LocalContext.current
                Scaffold(modifier = Modifier.fillMaxSize()) { _ ->

                    AppNavigation(context)
                }
            }
        }
    }
}

/**
 * Componente de navegación principal de la aplicación
 * Gestiona las transiciones entre pantallas
 */
@Composable
fun AppNavigation(context: Context) {
    // Crear instancia compartida del repositorio
    val dbHelper = remember { DatabaseHelper(context) }
    val movieRepository = remember { MovieRepository(dbHelper) }
    val currentScreen = remember { mutableStateOf("landing") }

    when (currentScreen.value) {
        "landing" -> {
            LandingScreen(
                onNavigateToMovies = { currentScreen.value = "movies" },
                onNavigateToAddMovie = { currentScreen.value = "addMovie" }
            )
        }
        "addMovie" -> {
            AddMovieScreen(
                viewModel = AddMovieViewModel(),
                onBackClicked = { currentScreen.value = "landing" },
                onMovieSaved = { title, isWatched, rating ->
                    // Guardar la película en el repositorio
                    movieRepository.addMovie(title, isWatched, rating)
                    currentScreen.value = "landing"
                }
            )
        }
        "movies" -> {
            MoviesScreen(
                viewModel = MoviesViewModel(movieRepository),
                onBackClicked = { currentScreen.value = "landing" }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LandingScreenPreview() {
    PelisTheme {
        LandingScreen()
    }
}

