package com.example.boltfax_compose.ui

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun StyledTextInput(
    keyboardType: KeyboardType = KeyboardType.Text,
    margin: PaddingValues = PaddingValues(),
    prefilledText: String = "",
    onValueChange: (String) -> Unit,
    placeholder: String,
    modifier: Modifier = Modifier
) {

    val combinedModifier =
        Modifier
            .padding(margin)
            .fillMaxWidth()
            .padding(vertical = 4.dp, horizontal = 4.dp)
            .shadow(4.dp, RoundedCornerShape(12.dp))
            .then(modifier) // append user-passed modifier

    var value by remember { mutableStateOf(prefilledText) }

    TextField(
        value = value,
        onValueChange = {
            value = it
            onValueChange.invoke(value)
        },
        placeholder = { Text(placeholder) },
        modifier = combinedModifier,
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color.White,
            unfocusedContainerColor = Color.White,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        ),
        visualTransformation = if (keyboardType == KeyboardType.Password) PasswordVisualTransformation() else VisualTransformation.None,
        shape = RoundedCornerShape(12.dp),
        keyboardOptions = KeyboardOptions(
            keyboardType = keyboardType, // or KeyboardType.Email, Number, etc.
            imeAction = ImeAction.Next
        )
    )
}

@Preview(showBackground = true)
@Composable
fun PreviewStyledTextInput() {
    BoltFax_ComposeTheme {
        StyledTextInput(placeholder = "Enter your email", onValueChange = {

        })
    }
}