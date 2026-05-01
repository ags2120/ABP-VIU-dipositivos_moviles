package com.example.pelis.ui.components

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.pelis.ui.theme.PrimaryRed
import com.example.pelis.ui.theme.TextPrimary

/**
 * Componente reutilizable PrimaryButton
 * Botón principal con estilos personalizados para el tema de películas
 */
@Composable
fun PrimaryButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true
) {
    Button(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        colors = ButtonDefaults.buttonColors(
            containerColor = PrimaryRed,
            contentColor = TextPrimary,
            disabledContainerColor = PrimaryRed.copy(alpha = 0.5f)
        ),
        shape = RoundedCornerShape(8.dp)
    ) {
        Text(text = text)
    }
}
