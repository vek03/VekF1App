package com.example.vekf1app.ui.pilots

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.vekf1app.models.Pilot
import com.example.vekf1app.models.Team
import com.example.vekf1app.repository.PilotRepository
import com.example.vekf1app.repository.TeamRepository
import com.example.vekf1app.ui.components.TopAppBar
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListPilotsScreen(
    onNavigateToDashboard: () -> Unit,
    onNavigateToCreatePilot: () -> Unit,
    onNavigateToPilotUpdate: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val user = FirebaseAuth.getInstance().currentUser
    val db = Firebase.firestore

    user?.let {
        val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
        var pilots by remember { mutableStateOf<List<Pilot>>(emptyList()) }
        var teams by remember { mutableStateOf<List<Team>>(emptyList()) }

        LaunchedEffect(Unit) {
            pilots = PilotRepository(db).getPilots();
            teams = TeamRepository(db).getTeams()
        }

        Scaffold(
            modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
            topBar = {
                TopAppBar(
                    title = stringResource(id = com.example.vekf1app.R.string.app_name),
                    canNavigateBack = true,
                    scrollBehavior = scrollBehavior,
                    navigateUp = onNavigateToDashboard
                )
            },
            contentColor = MaterialTheme.colorScheme.secondary,
            containerColor = MaterialTheme.colorScheme.background,
            floatingActionButton = {
                FloatingActionButton(
                    onClick = onNavigateToCreatePilot,
                    shape = MaterialTheme.shapes.medium,
                    modifier = Modifier.padding(20.dp),
                    contentColor = MaterialTheme.colorScheme.onSecondaryContainer,
                    containerColor = MaterialTheme.colorScheme.secondaryContainer
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Add Pilot",
                        tint = MaterialTheme.colorScheme.onSecondaryContainer
                    )
                }
            }
        ) { innerPadding ->
            ListPilotsBody(
                db,
                pilots,
                teams,
                onItemClick = onNavigateToPilotUpdate,
                contentPadding = innerPadding,
            )
        }
    }
}


@Composable
private fun ListPilotsBody(
    db: FirebaseFirestore,
    pilots: List<Pilot>,
    teams: List<Team>,
    onItemClick: (String) -> Unit,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp),
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier,
    ) {
        if (pilots.isEmpty()) {
            Text(
                text = "Nenhum Piloto Cadastrado...",
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(contentPadding),
                color = MaterialTheme.colorScheme.secondary
            )
        } else {
            InventoryList(
                db,
                pilots,
                onItemClick = { it.getId()?.let { it1 -> onItemClick(it1) } },
                contentPadding = contentPadding,
                modifier = Modifier.padding(horizontal = 8.dp)
            )
        }
    }
}

@Composable
private fun InventoryList(
    db: FirebaseFirestore,
    pilots: List<Pilot>,
    onItemClick: (Pilot) -> Unit,
    contentPadding: PaddingValues,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = contentPadding
    ) {
        items(
            count = pilots.size,
            itemContent = { index ->
                val pilot = pilots[index]
                InventoryItem(
                    db,
                    pilot,
                    modifier = Modifier
                        .padding(8.dp)
                        .clickable { onItemClick(pilot) }
                )
            }
        )
    }
}

@Composable
private fun InventoryItem(
    db: FirebaseFirestore,
    pilot: Pilot,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardColors(
            containerColor = MaterialTheme.colorScheme.secondaryContainer,
            contentColor = MaterialTheme.colorScheme.onSecondaryContainer,
            disabledContainerColor = MaterialTheme.colorScheme.primaryContainer,
            disabledContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
        )
    ) {
        Column(
            modifier = Modifier.padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = pilot.getNome(),
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onSecondaryContainer
                )
                Spacer(Modifier.weight(1f))
                Text(
                    text = pilot.getIdade(),
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSecondaryContainer
                )
            }
            Text(
                text = pilot.getIdade() + " anos",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSecondaryContainer
            )
        }
    }
}