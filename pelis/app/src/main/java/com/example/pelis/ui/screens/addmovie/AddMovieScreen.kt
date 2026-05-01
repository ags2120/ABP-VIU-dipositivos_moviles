package com.example.pelis.ui.screens.addmovie

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
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
import com.example.pelis.ui.components.CustomCheckbox
import com.example.pelis.ui.components.CustomTextField
import com.example.pelis.ui.components.PrimaryButton
import com.example.pelis.ui.components.RatingSlider
import com.example.pelis.ui.theme.DarkBg
import com.example.pelis.ui.theme.TextPrimary
import com.example.pelis.ui.theme.TextSecondary
import com.example.pelis.viewmodel.AddMovieViewModel

/**
 * Pantalla de Añadir Película
 * Permite al usuario crear una nueva película con título, estado de visualización y puntuación
 *
 * @param viewModel ViewModel que gestiona la lógica de la pantalla
 * @param onBackClicked Callback para volver a la pantalla anterior
 * @param onMovieSaved Callback al guardar la película con los datos creados
 */
@Composable
fun AddMovieScreen(
    viewModel: AddMovieViewModel = viewModel(),
    onBackClicked: () -> Unit = {},
    onMovieSaved: (String, Boolean, Long) -> Unit = { _, _, _ -> }
) {
    // Collect states desde StateFlow
    val titleState by viewModel.titleMovieState.collectAsState()
    val isWatchedState by viewModel.isWatchedState.collectAsState()
    val ratingState by viewModel.ratingState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkBg)
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Top
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
                text = stringResource(R.string.add_movies_button),
                color = TextPrimary,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 8.dp)
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Campo de título
        CustomTextField(
            value = titleState,
            onValueChange = { viewModel.onTitleChanged(it) },
            label = stringResource(R.string.movie_title),
            placeholder = "Ingresa el título de la película"
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Checkbox de "Visto"
        CustomCheckbox(
            checked = isWatchedState,
            onCheckedChange = { viewModel.onWatchedChanged(it) },
            label = stringResource(R.string.movie_watched)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Slider de puntuación (solo visible si está marcado como visto)
        if (isWatchedState) {
            RatingSlider(
                value = ratingState,
                onValueChange = { viewModel.onRatingChanged(it) },
                label = stringResource(R.string.movie_rating),
                enabled = true
            )
        } else {
            // Mostrar mensaje si no está visto
            Text(
                text = "Marca la película como 'Visto' para añadir una puntuación",
                color = TextSecondary,
                fontSize = 12.sp,
                modifier = Modifier.padding(vertical = 16.dp)
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Resumen de datos
        Text(
            text = stringResource(R.string.summary),
            color = TextPrimary,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 12.dp)
        )

        Text(
            text = "Título: ${titleState.ifBlank { "(Sin especificar)" }}",
            color = TextSecondary,
            fontSize = 12.sp,
            modifier = Modifier.padding(vertical = 4.dp)
        )

        Text(
            text = "Visto: ${if (isWatchedState) "Sí" else "No"}",
            color = TextSecondary,
            fontSize = 12.sp,
            modifier = Modifier.padding(vertical = 4.dp)
        )

        if (isWatchedState) {
            Text(
                text = "Puntuación: ${ratingState.toLong()}/5",
                color = TextSecondary,
                fontSize = 12.sp,
                modifier = Modifier.padding(vertical = 4.dp)
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Botones de acción
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            PrimaryButton(
                text = stringResource(R.string.cancel),
                onClick = {
                    viewModel.clearForm()
                    onBackClicked()
                },
                modifier = Modifier.weight(1f)
            )

            PrimaryButton(
                text = stringResource(R.string.save),
                onClick = {
                    val movieData = viewModel.saveMovie()
                    if (movieData != null) {
                        onMovieSaved(movieData.title, movieData.isWatched, movieData.rating)
                        viewModel.clearForm()
                        onBackClicked()
                    }
                },
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(modifier = Modifier.height(24.dp))
    }
}
