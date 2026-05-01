package com.example.pelis.ui.screens.movies

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.pelis.R
import com.example.pelis.domain.model.Movie
import com.example.pelis.ui.theme.DarkBg
import com.example.pelis.ui.theme.PrimaryRed
import com.example.pelis.ui.theme.SecondaryGold
import com.example.pelis.ui.theme.TextPrimary
import com.example.pelis.ui.theme.TextSecondary
import com.example.pelis.viewmodel.MoviesViewModel

/**
 * Pantalla de Películas
 * Muestra un listado de todas las películas que el usuario ha añadido
 * Permite eliminar y marcar como favoritas
 *
 * @param viewModel ViewModel que gestiona la lógica de la pantalla
 * @param onBackClicked Callback para volver a la pantalla anterior
 */
@Composable
fun MoviesScreen(
    viewModel: MoviesViewModel = viewModel(),
    onBackClicked: () -> Unit = {}
) {
    val moviesState by viewModel.moviesState.collectAsState(initial = emptyList())

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkBg)
            .padding(16.dp)
    ) {
        // Header con botón volver
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 12.dp, bottom = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onBackClicked, modifier = Modifier.then(Modifier)) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Volver",
                    tint = TextPrimary
                )
            }
            Text(
                text = stringResource(R.string.view_movies_button),
                color = TextPrimary,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 8.dp)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Mostrar cantidad de películas
        Text(
            text = "Total: ${moviesState.size} película${if (moviesState.size != 1) "s" else ""}",
            color = TextSecondary,
            fontSize = 12.sp,
            modifier = Modifier.padding(bottom = 12.dp)
        )

        // Lista de películas
        if (moviesState.isEmpty()) {
            // Mostrar mensaje si no hay películas
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 32.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "📽️",
                    fontSize = 48.sp,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
                Text(
                    text = "No hay películas",
                    color = TextPrimary,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Añade tu primera película para comenzar",
                    color = TextSecondary,
                    fontSize = 14.sp,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
        } else {
            LazyColumn(
                contentPadding = PaddingValues(bottom = 16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(moviesState, key = { movie -> movie.id }) { movie ->
                    MovieListItem(
                        movie = movie,
                        onDeleteClick = { viewModel.deleteMovie(movie.id) },
                        onFavoriteClick = {
                            if (movie.isFavorite) {
                                viewModel.removeFromFavorites(movie.id)
                            } else {
                                viewModel.addToFavorites(movie.id)
                            }
                        }
                    )
                }
            }
        }
    }
}

/**
 * Componente que representa un elemento de película en la lista
 */
@Composable
private fun MovieListItem(
    movie: Movie,
    onDeleteClick: () -> Unit = {},
    onFavoriteClick: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Top
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = movie.title,
                    color = TextPrimary,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Año: ${movie.year}",
                    color = TextSecondary,
                    fontSize = 12.sp,
                    modifier = Modifier.padding(top = 4.dp)
                )
                if (movie.isWatched) {
                    Text(
                        text = "Puntuación: ${movie.rating}/5 ⭐",
                        color = SecondaryGold,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                } else {
                    Text(
                        text = "No visto",
                        color = TextSecondary,
                        fontSize = 12.sp,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }
            }

            // Botones de acción
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onFavoriteClick) {
                    Icon(
                        imageVector = if (movie.isFavorite) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                        contentDescription = if (movie.isFavorite) "Quitar de favoritos" else "Agregar a favoritos",
                        tint = if (movie.isFavorite) PrimaryRed else TextSecondary,
                        modifier = Modifier.then(Modifier)
                    )
                }
                IconButton(onClick = onDeleteClick) {
                    Icon(
                        imageVector = Icons.Filled.Delete,
                        contentDescription = "Eliminar",
                        tint = PrimaryRed
                    )
                }
            }
        }
    }
}
