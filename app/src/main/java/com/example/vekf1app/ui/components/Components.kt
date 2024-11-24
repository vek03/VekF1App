package com.example.vekf1app.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.example.vekf1app.models.Team
import com.example.vekf1app.ui.theme.CustomColorScheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBar(
    title: String,
    canNavigateBack: Boolean,
    modifier: Modifier = Modifier,
    scrollBehavior: TopAppBarScrollBehavior? = null,
    navigateUp: () -> Unit = {}
) {
    CenterAlignedTopAppBar(
        title = { Text(title) },
        modifier = modifier,
        scrollBehavior = scrollBehavior,
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Return"
                    )
                }
            }
        },
        colors = TopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
            navigationIconContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
            actionIconContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
            scrolledContainerColor = MaterialTheme.colorScheme.primaryContainer
        )
    )
}

@Composable
fun CustomTextField(
    value: String,
    label: String,
    onValueChange: (String) -> Unit,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    modifier: Modifier = Modifier,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    readOnly: Boolean = false
){
    OutlinedTextField(
        value = value,
        visualTransformation = visualTransformation,
        modifier = modifier,
        readOnly = readOnly,
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


@Composable
fun TeamDropdown(
    options: List<Team>,
    selectedItem: Team?,
    onItemSelected: (Team) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    val selectedName = selectedItem?.getNome() ?: "Selecione um time"

    Column {
        CustomTextField(
            value = selectedName,
            onValueChange = {  },
            readOnly = true,
            label = "Selecione uma Equipe",
            modifier = Modifier
                .fillMaxWidth()
                .clickable { expanded = !expanded }
                .padding(0.dp)
        )

        DropdownMenu(
            modifier = Modifier.fillMaxWidth().padding(10.dp),
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            options.forEach { team ->
                DropdownMenuItem(
                    text = { Text(team.getNome()) },
                    onClick = {
                        onItemSelected(team)
                        expanded = false
                    }
                )
            }
        }
    }
}

@Composable
fun DialogButton(
    label: String,
    modifier: Modifier = Modifier,
    title: String, message: String,
    icon: @Composable (() -> Unit)? = null,
    onConfirm: () -> Unit,
    onDismiss: (() -> Unit)? = null
){
    var showDialog by remember { mutableStateOf(false) }

    Button(
        onClick = { showDialog = true },
        modifier = modifier
    ) {
        Text(label)
    }

    if(showDialog){
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text(title) },
            text = { Text(message) },
            icon = icon,
            containerColor = MaterialTheme.colorScheme.secondaryContainer,
            titleContentColor = MaterialTheme.colorScheme.onSecondaryContainer,
            textContentColor = MaterialTheme.colorScheme.onSecondaryContainer,
            iconContentColor = MaterialTheme.colorScheme.onSecondaryContainer,
            confirmButton = {
                TextButton(
                    onClick = {
                        onConfirm()
                        showDialog = false
                    }
                ) {
                    Text("Sim", color = MaterialTheme.colorScheme.secondary)
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        onDismiss?.invoke()
                        showDialog = false
                    }
                ) {
                    Text("Não", color = MaterialTheme.colorScheme.secondary)
                }
            }
        )
    }
}