package com.example.pelis.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
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
 * Componente reutilizable CustomCheckbox
 * Checkbox con estilos personalizados para el tema
 */
@Composable
fun CustomCheckbox(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    label: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        Checkbox(
            checked = checked,
            onCheckedChange = onCheckedChange,
            colors = CheckboxDefaults.colors(
                checkedColor = PrimaryRed,
                uncheckedColor = TextSecondary
            )
        )
        Text(
            text = label,
            color = TextPrimary,
            fontSize = 14.sp,
            modifier = Modifier.padding(start = 8.dp)
        )
    }
}
