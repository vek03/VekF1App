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
            cursorColor = MaterialTheme.colorScheme.secondary,
            textSelectionColors = TextSelectionColors(
                handleColor = MaterialTheme.colorScheme.secondary,
                backgroundColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.4f)
            ),
            focusedLabelColor = MaterialTheme.colorScheme.secondary,
            unfocusedLabelColor = MaterialTheme.colorScheme.secondary,
            focusedIndicatorColor = MaterialTheme.colorScheme.secondary,
            unfocusedIndicatorColor = MaterialTheme.colorScheme.secondary,
            focusedContainerColor = MaterialTheme.colorScheme.primary,
            unfocusedContainerColor = MaterialTheme.colorScheme.primary,
            focusedTextColor = MaterialTheme.colorScheme.secondary,
            unfocusedTextColor = MaterialTheme.colorScheme.secondary,
            focusedLeadingIconColor = MaterialTheme.colorScheme.primary,
            unfocusedLeadingIconColor = MaterialTheme.colorScheme.primary,
            focusedTrailingIconColor = MaterialTheme.colorScheme.primary,
            unfocusedTrailingIconColor = MaterialTheme.colorScheme.primary,
            focusedPrefixColor = MaterialTheme.colorScheme.primary,
            unfocusedPrefixColor = MaterialTheme.colorScheme.primary,
            focusedSuffixColor = MaterialTheme.colorScheme.primary,
            unfocusedSuffixColor = MaterialTheme.colorScheme.primary,
            focusedPlaceholderColor = MaterialTheme.colorScheme.secondary,
            unfocusedPlaceholderColor = MaterialTheme.colorScheme.secondary,
            focusedSupportingTextColor = MaterialTheme.colorScheme.primary,
            unfocusedSupportingTextColor = MaterialTheme.colorScheme.primary,
            disabledLabelColor = MaterialTheme.colorScheme.secondary,
            disabledPrefixColor = MaterialTheme.colorScheme.secondary,
            disabledSuffixColor = MaterialTheme.colorScheme.secondary,
            disabledTextColor = MaterialTheme.colorScheme.secondary,
            disabledContainerColor = MaterialTheme.colorScheme.primary,
            disabledIndicatorColor = MaterialTheme.colorScheme.primary,
            disabledPlaceholderColor = MaterialTheme.colorScheme.secondary,
            disabledLeadingIconColor = MaterialTheme.colorScheme.primary,
            disabledTrailingIconColor = MaterialTheme.colorScheme.primary,
            disabledSupportingTextColor = MaterialTheme.colorScheme.primary,
            errorCursorColor = MaterialTheme.colorScheme.error,
            errorIndicatorColor = MaterialTheme.colorScheme.error,
            errorLabelColor = MaterialTheme.colorScheme.error,
            errorPrefixColor = MaterialTheme.colorScheme.error,
            errorTextColor = MaterialTheme.colorScheme.error,
            errorLeadingIconColor = MaterialTheme.colorScheme.error,
            errorTrailingIconColor = MaterialTheme.colorScheme.error,
            errorSuffixColor = MaterialTheme.colorScheme.error,
            errorPlaceholderColor = MaterialTheme.colorScheme.primary,
            errorSupportingTextColor = MaterialTheme.colorScheme.primary,
            errorContainerColor = MaterialTheme.colorScheme.error
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