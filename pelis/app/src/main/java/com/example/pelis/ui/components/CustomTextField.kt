package com.example.pelis.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.pelis.ui.theme.CardBg
import com.example.pelis.ui.theme.PrimaryRed
import com.example.pelis.ui.theme.TextPrimary
import com.example.pelis.ui.theme.TextSecondary

/**
 * Componente reutilizable CustomTextField
 * Campo de texto con estilos personalizados para el tema
 */
@Composable
fun CustomTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    modifier: Modifier = Modifier,
    keyboardType: KeyboardType = KeyboardType.Text,
    maxLines: Int = 1,
    placeholder: String = ""
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label, color = TextSecondary) },
        placeholder = { Text(placeholder, color = TextSecondary) },
        modifier = modifier.fillMaxWidth(),
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        maxLines = maxLines,
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = PrimaryRed,
            unfocusedBorderColor = TextSecondary.copy(alpha = 0.5f),
            focusedTextColor = TextPrimary,
            unfocusedTextColor = TextPrimary,
            cursorColor = PrimaryRed
        ),
        shape = RoundedCornerShape(8.dp)
    )
}
