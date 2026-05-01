package com.example.pelis.ui.components

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.pelis.ui.theme.TextPrimary

/**
 * Componente reutilizable SectionTitle
 * Título de sección reutilizable en diferentes pantallas
 */
@Composable
fun SectionTitle(
    text: String,
    modifier: Modifier = Modifier
) {
    Text(
        text = text,
        color = TextPrimary,
        fontSize = 20.sp,
        fontWeight = FontWeight.Bold,
        modifier = modifier
    )
}
