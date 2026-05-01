package com.example.pelis.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pelis.data.repository.MovieRepository
import kotlinx.coroutines.launch

/**
 * ViewModel para la pantalla de Películas
 * Gestiona el estado y la lógica de presentación para mostrar el listado de películas
 * Implementa el patrón MVVM separando la lógica de vista de la interfaz
 */
/*class MoviesViewModel(private val movieRepository: MovieRepository = MovieRepository(dbHelper:DatabaseHelper)) :
    ViewModel() {

    // Estado del listado de películas
    val moviesState: StateFlow<List<Movie>> = movieRepository.movies
        .stateIn(
            scope = viewModelScope,
            started = kotlinx.coroutines.flow.SharingStarted.Lazily,
            initialValue = emptyList()
        )

    /**
     * Obtener todas las películas
     */
    fun getAllMovies() {
        viewModelScope.launch {
            // Las películas se actualizan automáticamente a través de StateFlow
        }
    }

    /**
     * Eliminar una película
     */
    fun deleteMovie(movieId: Int) {
        movieRepository.deleteMovie(movieId)
    }

    /**
     * Agregar película a favoritos
     */
    fun addToFavorites(movieId: Int) {
        viewModelScope.launch {
            movieRepository.addToFavorites(movieId)
        }
    }

    /**
     * Eliminar película de favoritos
     */
    fun removeFromFavorites(movieId: Int) {
        viewModelScope.launch {
            movieRepository.removeFromFavorites(movieId)
        }
    }

    /**
     * Agregar una nueva película
     */
    fun addNewMovie(title: String, isWatched: Boolean, rating: Long) {
        movieRepository.addMovie(title, isWatched, rating)
    }
}*/
class MoviesViewModel(
    private val movieRepository: MovieRepository
) : ViewModel() {

    val moviesState = movieRepository.movies

    init {
        movieRepository.loadMovies() // 👈 ESTA ES LA PIEZA QUE TE FALTA
    }
    fun deleteMovie(movieId: Int) {
        movieRepository.deleteMovie(movieId)
    }

    fun addToFavorites(movieId: Int) {
        viewModelScope.launch {
            movieRepository.addToFavorites(movieId)
        }
    }

    fun removeFromFavorites(movieId: Int) {
        viewModelScope.launch {
            movieRepository.removeFromFavorites(movieId)
        }
    }

    fun addNewMovie(title: String, isWatched: Boolean, rating: Long) {
        movieRepository.addMovie(title, isWatched, rating)
    }
}