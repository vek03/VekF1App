package com.example.vekf1app.ui.components

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldColors
import androidx.compose.runtime.Composable
import com.example.vekf1app.ui.theme.CustomColorScheme


@Composable
fun CustomTextField(value: String, label: String, onValueChange: (String) -> Unit, keyboardOptions: KeyboardOptions){
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(text = label) },
        keyboardOptions = keyboardOptions,
        colors = TextFieldColors(
            cursorColor = CustomColorScheme.secondary,
            textSelectionColors = TextSelectionColors(
                handleColor = CustomColorScheme.secondary,
                backgroundColor = CustomColorScheme.primary.copy(alpha = 0.4f)
            ),
            focusedLabelColor = CustomColorScheme.secondary,
            unfocusedLabelColor = CustomColorScheme.secondary,
            focusedIndicatorColor = CustomColorScheme.secondary,
            unfocusedIndicatorColor = CustomColorScheme.secondary,
            focusedContainerColor = CustomColorScheme.primary,
            unfocusedContainerColor = CustomColorScheme.primary,
            focusedTextColor = CustomColorScheme.secondary,
            unfocusedTextColor = CustomColorScheme.secondary,
            focusedLeadingIconColor = CustomColorScheme.primary,
            unfocusedLeadingIconColor = CustomColorScheme.primary,
            focusedTrailingIconColor = CustomColorScheme.primary,
            unfocusedTrailingIconColor = CustomColorScheme.primary,
            focusedPrefixColor = CustomColorScheme.primary,
            unfocusedPrefixColor = CustomColorScheme.primary,
            focusedSuffixColor = CustomColorScheme.primary,
            unfocusedSuffixColor = CustomColorScheme.primary,
            focusedPlaceholderColor = CustomColorScheme.secondary,
            unfocusedPlaceholderColor = CustomColorScheme.secondary,
            focusedSupportingTextColor = CustomColorScheme.primary,
            unfocusedSupportingTextColor = CustomColorScheme.primary,
            disabledLabelColor = CustomColorScheme.secondary,
            disabledPrefixColor = CustomColorScheme.secondary,
            disabledSuffixColor = CustomColorScheme.secondary,
            disabledTextColor = CustomColorScheme.secondary,
            disabledContainerColor = CustomColorScheme.primary,
            disabledIndicatorColor = CustomColorScheme.primary,
            disabledPlaceholderColor = CustomColorScheme.secondary,
            disabledLeadingIconColor = CustomColorScheme.primary,
            disabledTrailingIconColor = CustomColorScheme.primary,
            disabledSupportingTextColor = CustomColorScheme.primary,
            errorCursorColor = CustomColorScheme.error,
            errorIndicatorColor = CustomColorScheme.error,
            errorLabelColor = CustomColorScheme.error,
            errorPrefixColor = CustomColorScheme.error,
            errorTextColor = CustomColorScheme.error,
            errorLeadingIconColor = CustomColorScheme.error,
            errorTrailingIconColor = CustomColorScheme.error,
            errorSuffixColor = CustomColorScheme.error,
            errorPlaceholderColor = CustomColorScheme.primary,
            errorSupportingTextColor = CustomColorScheme.primary,
            errorContainerColor = CustomColorScheme.error
        )
    )
}