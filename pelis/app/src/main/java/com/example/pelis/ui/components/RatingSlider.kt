package com.example.pelis.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pelis.ui.theme.PrimaryRed
import com.example.pelis.ui.theme.TextPrimary
import com.example.pelis.ui.theme.TextSecondary

/**
 * Componente reutilizable RatingSlider
 * Slider para seleccionar puntuación de 0 a 5
 */
@Composable
fun RatingSlider(
    value: Float,
    onValueChange: (Float) -> Unit,
    label: String = "Puntuación",
    modifier: Modifier = Modifier,
    enabled: Boolean = true
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = label,
                color = TextPrimary,
                fontSize = 14.sp
            )
            Text(
                text = String.format("%.1f", value),
                color = PrimaryRed,
                fontSize = 16.sp
            )
        }
        Slider(
            value = value,
            onValueChange = onValueChange,
            valueRange = 0f..5f,
            steps = 4, // Permite valores 0, 1, 2, 3, 4, 5
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            colors = SliderDefaults.colors(
                thumbColor = PrimaryRed,
                activeTrackColor = PrimaryRed,
                inactiveTrackColor = TextSecondary.copy(alpha = 0.3f)
            )
        )
    }
}
