package com.example.vekf1app.ui.auth

import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import com.example.vekf1app.R
import com.example.vekf1app.ui.components.CustomTextField
import com.example.vekf1app.ui.components.TopAppBar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseAuthUserCollisionException

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(onRegisterSuccess: () -> Unit, onNavigateToLogin: () -> Unit, modifier: Modifier = Modifier) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBar(
                title = stringResource(id = R.string.app_name),
                canNavigateBack = false,
                scrollBehavior = scrollBehavior,
            )
        },
        contentColor = MaterialTheme.colorScheme.secondary,
        containerColor = MaterialTheme.colorScheme.background
    ) { innerPadding ->
        RegisterBody(onRegisterSuccess, onNavigateToLogin, innerPadding)
    }
}

@Composable
fun RegisterBody(onRegisterSuccess: () -> Unit, onNavigateToLogin: () -> Unit, contentPadding: PaddingValues = PaddingValues(0.dp)){
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var password_confirmation by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(contentPadding)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(contentPadding),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "CADASTRO",
                color = MaterialTheme.colorScheme.secondary,
                style = MaterialTheme.typography.headlineMedium,
                fontSize = TextUnit(value = 40F, TextUnitType.Sp),
                modifier = Modifier.padding(contentPadding)
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(contentPadding),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CustomTextField(
                value = email,
                onValueChange = { email = it },
                label = "E-mail",
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
            )

            Spacer(modifier = Modifier.height(10.dp))

            CustomTextField(
                value = password,
                onValueChange = { password = it },
                label = "Senha",
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
            )

            Spacer(modifier = Modifier.height(10.dp))

            CustomTextField(
                value = password_confirmation,
                onValueChange = { password_confirmation = it },
                label = "Confirmar Senha",
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
            )

            Spacer(modifier = Modifier.height(30.dp))

            Button(
                onClick = {
                    if (email.isEmpty() || password.isEmpty()) {
                        errorMessage = "Preencha todos os campos!"
                        return@Button
                    }

                    if (password.length < 6 || password_confirmation.length < 6) {
                        errorMessage = "A senha deve ter no mínimo 6 caracteres!"
                        return@Button
                    }

                    if (password != password_confirmation) {
                        errorMessage = "As senhas não coincidem!"
                        return@Button
                    }

                    FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                onRegisterSuccess()
                            } else {
                                Log.i(TAG, "createUserWithEmail:failure - Email: $email, Password: $password", task.exception)

                                errorMessage =  when (task.exception) {
                                    is FirebaseAuthInvalidUserException -> "Usuário Não Encontrado!"
                                    is FirebaseAuthInvalidCredentialsException -> "Credenciais Inválidas!"
                                    is FirebaseAuthUserCollisionException -> "Usuário Já Cadastrado!"
                                    else -> task.exception?.localizedMessage ?: "Erro Desconhecido"
                                }
                            }
                        }
                }
            ) {
                Text("Registrar-se")
            }

            Spacer(modifier = Modifier.height(20.dp))

            if (errorMessage.isNotEmpty()) {
                Text(errorMessage, color = MaterialTheme.colorScheme.error, textAlign = TextAlign.Center)
                Spacer(modifier = Modifier.height(10.dp))
            }

            TextButton(onClick = onNavigateToLogin) {
                Text("Já tem uma conta? Faça Login")
            }
        }
    }
}