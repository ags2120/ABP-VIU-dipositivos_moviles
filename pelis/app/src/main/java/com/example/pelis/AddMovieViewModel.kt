package com.example.pelis.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * ViewModel para la pantalla de añadir película
 * Gestiona el estado y la lógica de presentación para crear nuevas películas
 * Implementa el patrón MVVM separando la lógica de vista de la interfaz
 */
class AddMovieViewModel : ViewModel() {

    // Estado del título de la película usando StateFlow
    private val _titleMovieState = MutableStateFlow("")
    val titleMovieState: StateFlow<String> = _titleMovieState.asStateFlow()

    // Estado del checkbox "Visto"
    private val _isWatchedState = MutableStateFlow(false)
    val isWatchedState: StateFlow<Boolean> = _isWatchedState.asStateFlow()

    // Estado de la puntuación (0-5)
    private val _ratingState = MutableStateFlow(0f)
    val ratingState: StateFlow<Float> = _ratingState.asStateFlow()

    /**
     * Actualizar el título de la película
     */
    fun onTitleChanged(newTitle: String) {
        _titleMovieState.value = newTitle
    }

    /**
     * Actualizar el estado de "Visto"
     */
    fun onWatchedChanged(isWatched: Boolean) {
        _isWatchedState.value = isWatched
        // Si no está visto, resetear la puntuación
        if (!isWatched) {
            _ratingState.value = 0f
        }
    }

    /**
     * Actualizar la puntuación
     */
    fun onRatingChanged(newRating: Float) {
        _ratingState.value = newRating
    }

    /**
     * Validar que el título no esté vacío
     */
    fun isFormValid(): Boolean {
        return _titleMovieState.value.isNotBlank()
    }

    /**
     * Guardar la película y retornar los datos
     */
    fun saveMovie(): MovieCreateData? {
        if (!isFormValid()) return null

        return MovieCreateData(
            title = _titleMovieState.value.trim(),
            isWatched = _isWatchedState.value,
            rating = _ratingState.value.toLong()
        )
    }

    /**
     * Limpiar el formulario
     */
    fun clearForm() {
        _titleMovieState.value = ""
        _isWatchedState.value = false
        _ratingState.value = 0f
    }
}

/**
 * Clase de datos para representar los datos de una película a crear
 */
data class MovieCreateData(
    val title: String,
    val isWatched: Boolean,
    val rating: Long
)

