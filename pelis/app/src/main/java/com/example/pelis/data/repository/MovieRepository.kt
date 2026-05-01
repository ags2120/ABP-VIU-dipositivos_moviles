/*package com.example.pelis.data.repository

import com.example.pelis.domain.model.Movie
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

/**
 * Repositorio de películas
 * Actúa como intermediario entre la capa de datos y la lógica de negocio
 * Gestiona el almacenamiento de películas en memoria
 * En versiones futuras puede ser extendido para usar SQLite o API REST
 */
class MovieRepository (private val dbHelper: DatabaseHelper) {

    // Datos iniciales de ejemplo
    private val initialMovies = listOf(
        Movie(
            id = 1,
            title = "The Dark Knight",
            year = 2008,
            rating = 5L,
            director = "Christopher Nolan",
            synopsis = "When the menace known as the Joker emerges from his mysterious past.",
            isWatched = true,
            isFavorite = true
        ),
        Movie(
            id = 2,
            title = "Inception",
            year = 2010,
            rating = 4L,
            director = "Christopher Nolan",
            synopsis = "A skilled thief who steals corporate secrets through the use of dream-sharing technology.",
            isWatched = true
        ),
        Movie(
            id = 3,
            title = "The Matrix",
            year = 1999,
            rating = 5L,
            director = "Lana Wachowski, Lilly Wachowski",
            synopsis = "A computer hacker learns from mysterious rebels about the true nature of his reality.",
            isWatched = true
        )
    )

    // MutableStateFlow para películas - permite agregar nuevas películas
    private val _movies = MutableStateFlow(initialMovies.toMutableList())
    val movies: Flow<List<Movie>> = _movies.asStateFlow()

    private var nextId = 4

    /**
     * Obtiene todas las películas
     */
    suspend fun getAllMovies(): List<Movie> {
        return _movies.value
    }

    /**
     * Obtiene una película por ID
     */
    suspend fun getMovieById(id: Int): Movie? {
        return _movies.value.find { it.id == id }
    }

    /**
     * Obtiene películas favoritas
     */
    suspend fun getFavoriteMovies(): List<Movie> {
        return _movies.value.filter { it.isFavorite }
    }

    /**
     * Obtiene películas vistas
     */
    suspend fun getWatchedMovies(): List<Movie> {
        return _movies.value.filter { it.isWatched }
    }

    /**
     * Añade una nueva película
     */
    fun addMovie(title: String, isWatched: Boolean, rating: Long) {
        val newMovie = Movie(
            id = nextId++,
            title = title,
            year = java.util.Calendar.getInstance().get(java.util.Calendar.YEAR),
            rating = rating,
            director = "",
            synopsis = "",
            isWatched = isWatched,
            isFavorite = false
        )
        _movies.update { currentList ->
            currentList.toMutableList().apply {
                add(newMovie)
            }
        }
    }

    /**
     * Añade una película a favoritos
     */
    suspend fun addToFavorites(movieId: Int) {
        _movies.update { currentList ->
            currentList.map { movie ->
                if (movie.id == movieId) movie.copy(isFavorite = true) else movie
            }.toMutableList()
        }
    }

    /**
     * Elimina una película de favoritos
     */
    suspend fun removeFromFavorites(movieId: Int) {
        _movies.update { currentList ->
            currentList.map { movie ->
                if (movie.id == movieId) movie.copy(isFavorite = false) else movie
            }.toMutableList()
        }
    }

    /**
     * Elimina una película
     */
    fun deleteMovie(movieId: Int) {
        _movies.update { currentList ->
            currentList.filterNot { it.id == movieId }.toMutableList()
        }
    }
}
*/
package com.example.pelis.data.repository

import com.example.pelis.domain.model.Movie
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.Calendar

class MovieRepository(private val dbHelper: DatabaseHelper) {

    private val _movies = MutableStateFlow<List<Movie>>(emptyList())
    val movies: Flow<List<Movie>> = _movies.asStateFlow()

    /**
     * Cargar películas desde SQLite
     */
    fun loadMovies() {
        _movies.value = dbHelper.getAllMovies()
    }

    /**
     * Obtener todas (desde memoria ya sincronizada)
     */
    suspend fun getAllMovies(): List<Movie> {
        return _movies.value
    }

    /**
     * Obtener por ID
     */
    suspend fun getMovieById(id: Int): Movie? {
        return _movies.value.find { it.id == id }
    }

    /**
     * Favoritas
     */
    suspend fun getFavoriteMovies(): List<Movie> {
        return _movies.value.filter { it.isFavorite }
    }

    /**
     * Vistas
     */
    suspend fun getWatchedMovies(): List<Movie> {
        return _movies.value.filter { it.isWatched }
    }

    /**
     * Añadir película (ahora en BD)
     */
    fun addMovie(title: String, isWatched: Boolean, rating: Long) {
        val movie = Movie(
            id = 0, // SQLite lo autogenera
            title = title,
            year = Calendar.getInstance().get(Calendar.YEAR),
            rating = rating,
            director = "",
            synopsis = "",
            isWatched = isWatched,
            isFavorite = false
        )

        dbHelper.insertMovie(movie)
        loadMovies() // refresca el Flow
    }

    /**
     * Añadir a favoritos
     */
    suspend fun addToFavorites(movieId: Int) {
        dbHelper.updateFavorite(movieId, true)
        loadMovies()
    }

    /**
     * Quitar de favoritos
     */
    suspend fun removeFromFavorites(movieId: Int) {
        dbHelper.updateFavorite(movieId, false)
        loadMovies()
    }

    /**
     * Eliminar película
     */
    fun deleteMovie(movieId: Int) {
        dbHelper.deleteMovie(movieId)
        loadMovies()
    }
}