package com.example.boltfax_compose.ui

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ButtonCustom(
    modifier: Modifier = Modifier,
    text: String,
    onClick: () -> Unit
) {

    val combinedModifier = Modifier
        .padding(top=24.dp)
        .fillMaxWidth()

        .then(modifier)

    Button(
        onClick = onClick,
        modifier = combinedModifier,
        shape = RoundedCornerShape(4.dp)
    ) { Text(text) }
}