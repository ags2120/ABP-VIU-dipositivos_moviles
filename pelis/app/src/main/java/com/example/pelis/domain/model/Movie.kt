package com.example.pelis.domain.model

/**
 * Modelo de dominio que representa una película
 * Esta clase es parte de la capa de lógica de negocio
 * Se utiliza en todo el ViewModel y repositorios
 */
data class Movie(
    val id: Int,
    val title: String,
    val year: Int,
    val rating: Long = 0L,           // Puntuación de 0 a 5
    val director: String = "",
    val synopsis: String = "",
    val imageUrl: String = "",
    val isWatched: Boolean = false,  // ¿Ha sido visto?
    val isFavorite: Boolean = false
)
