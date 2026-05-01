package com.example.pelis.ui.screens.landing

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pelis.R
import com.example.pelis.ui.components.PrimaryButton
import com.example.pelis.ui.components.SectionTitle
import com.example.pelis.ui.theme.DarkBg
import com.example.pelis.ui.theme.PrimaryRed
import com.example.pelis.ui.theme.SecondaryGold
import com.example.pelis.ui.theme.TextPrimary
import com.example.pelis.ui.theme.TextSecondary
//import com.example.pelis.viewmodel.LandingViewModel
//import com.example.pelis.viewmodel.LandingUiState

/**
 * Pantalla Landing (Inicio)
 * Primera pantalla que ve el usuario al abrir la aplicación
 * Presenta opciones para ver películas o añadir nuevas películas
 *
 * @param viewModel ViewModel que gestiona la lógica de la pantalla
 * @param onNavigateToMovies Callback para navegar a la pantalla de películas
 * @param onNavigateToAddMovie Callback para navegar a la pantalla de añadir película
 */
@Composable
fun LandingScreen(
    //viewModel: LandingViewModel = LandingViewModel(),
    onNavigateToMovies: () -> Unit = {},
    onNavigateToAddMovie: () -> Unit = {}
) {
    // Inicializar datos al cargar la pantalla
    LaunchedEffect(Unit) {
        //viewModel.initializeLanding()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkBg)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        // Sección superior: Header
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Logo/Título principal
            Text(
                text = "🎬",
                fontSize = 60.sp,
                modifier = Modifier.padding(top = 32.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Título de bienvenida
            Text(
                text = stringResource(R.string.welcome_title),
                color = TextPrimary,
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Subtítulo
            Text(
                text = stringResource(R.string.welcome_subtitle),
                color = SecondaryGold,
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Descripción
            Text(
                text = stringResource(R.string.landing_description),
                color = TextSecondary,
                fontSize = 14.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 8.dp)
            )
        }

        // Sección central: Información estadística
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            SectionTitle(
                text = "¿Qué nos espera?",
                modifier = Modifier.padding(bottom = 16.dp)
            )

            StatisticCard(label = "Películas Disponibles", value = "150+")
            Spacer(modifier = Modifier.height(12.dp))
            StatisticCard(label = "Películas en Favoritos", value = "5")
        }

        // Sección inferior: Botones de acción
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 24.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            PrimaryButton(
                text = stringResource(R.string.view_movies_button),
                onClick = {
                    //viewModel.onExploreMoviesClicked()
                    onNavigateToMovies()
                },
                modifier = Modifier.fillMaxWidth()
            )

            PrimaryButton(
                text = stringResource(R.string.add_movies_button),
                onClick = {
                    //viewModel.onAddMovieClicked()
                    onNavigateToAddMovie()
                },
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

/**
 * Componente auxiliar para mostrar estadísticas en la landing
 * Ejemplo de componente reutilizable adicional
 */
@Composable
private fun StatisticCard(label: String, value: String) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = value,
            color = PrimaryRed,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = label,
            color = TextSecondary,
            fontSize = 12.sp
        )
    }
}
