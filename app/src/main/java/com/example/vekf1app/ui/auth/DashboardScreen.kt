package com.example.vekf1app.ui.auth

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
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
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import com.example.vekf1app.R
import com.example.vekf1app.ui.components.TopAppBar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(
    onLogout: () -> Unit,
    onNavigateToListPilots: () -> Unit,
    onNavigateToListTeams: () -> Unit,
    modifier: Modifier = Modifier
) {
    val user = FirebaseAuth.getInstance().currentUser
    user?.let {
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
            DashboardBody(user, onLogout, onNavigateToListPilots, onNavigateToListTeams, innerPadding)
        }
    }
}

@Composable
fun DashboardBody(
    user: FirebaseUser,
    onLogout: () -> Unit,
    onNavigateToListPilots: () -> Unit,
    onNavigateToListTeams: () -> Unit,
    contentPadding: PaddingValues = PaddingValues(0.dp)
){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
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
                    text = "DASHBOARD",
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
                Text("Bem-vindo, ${user.email}!")

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    modifier = Modifier
                        .size(300.dp, 60.dp)
                        .padding(8.dp),
                    shape = RoundedCornerShape(12.dp),
                    onClick = onNavigateToListPilots
                ) {
                    Text("Pilotos")
                }

                Button(
                    modifier = Modifier
                        .size(300.dp, 60.dp)
                        .padding(8.dp),
                    shape = RoundedCornerShape(12.dp),
                    onClick = onNavigateToListTeams
                ){
                    Text("Equipes")
                }

                Spacer(modifier = Modifier.height(16.dp))

                LogoutButton(onLogout)
            }
        }
    }
}

@Composable
fun LogoutButton(onLogout: () -> Unit) {
    var showDialog by remember { mutableStateOf(false) }

    Button(onClick = {
        showDialog = true
    }) {
        Text("Logout")
    }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text(text = "Confirmar Logout") },
            text = { Text("Você tem certeza de que deseja sair?") },
            confirmButton = {
                TextButton(
                    onClick = {
                        FirebaseAuth.getInstance().signOut()
                        onLogout()
                        showDialog = false
                    }
                ) {
                    Text("Sim")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = { showDialog = false }
                ) {
                    Text("Não")
                }
            }
        )
    }
}