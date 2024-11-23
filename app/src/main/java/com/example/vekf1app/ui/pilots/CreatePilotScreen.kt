package com.example.vekf1app.ui.pilots

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.vekf1app.R
import com.example.vekf1app.models.Pilot
import com.example.vekf1app.models.Team
import com.example.vekf1app.repository.PilotRepository
import com.example.vekf1app.repository.TeamRepository
import com.example.vekf1app.ui.components.CustomTextField
import com.example.vekf1app.ui.components.TeamDropdown
import com.example.vekf1app.ui.components.TopAppBar
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreatePilotScreen(
    onNavigateToListPilots: () -> Unit,
    modifier: Modifier = Modifier
) {
    val user = FirebaseAuth.getInstance().currentUser
    val db = Firebase.firestore

    user?.let {
        val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
        var teams by remember { mutableStateOf<List<Team>>(emptyList()) }

        LaunchedEffect(Unit) {
            teams = TeamRepository(db).getTeams()
        }

        Scaffold(
            modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
            topBar = {
                TopAppBar(
                    title = stringResource(id = R.string.app_name),
                    canNavigateBack = true,
                    scrollBehavior = scrollBehavior,
                    navigateUp = onNavigateToListPilots
                )
            },
            contentColor = MaterialTheme.colorScheme.secondary,
            containerColor = MaterialTheme.colorScheme.background
        ) { innerPadding ->
            CreatePilotBody(db, teams, onNavigateToListPilots, innerPadding)
        }
    }
}

@Composable
fun CreatePilotBody(
    db: FirebaseFirestore,
    teams: List<Team>,
    onNavigateToListPilots: () -> Unit,
    contentPadding: PaddingValues = PaddingValues(0.dp),
    modifier: Modifier = Modifier
){
    Column(
        verticalArrangement = Arrangement.spacedBy(20.dp),
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
            .padding(contentPadding)
    ) {
        PilotInputForm(db, teams, onNavigateToListPilots)
    }
}

@Composable
fun PilotInputForm(
    db: FirebaseFirestore,
    teams: List<Team>,
    onNavigateToListPilots: () -> Unit,
    pilot: Pilot? = null,
    modifier: Modifier = Modifier
) {
    var nome by remember { mutableStateOf(pilot?.getNome() ?: "") }
    var equipeId by remember { mutableStateOf(pilot?.getEquipeId() ?: "") }
    var idade by remember { mutableStateOf(pilot?.getIdade() ?: "") }
    var pais by remember { mutableStateOf(pilot?.getPais() ?: "") }

    var selectedTeam by remember { mutableStateOf<Team?>(null) }

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_medium))
    ) {
        CustomTextField(
            value = nome,
            onValueChange = { nome = it },
            label = "Nome",
            modifier = Modifier.fillMaxWidth()
        )

        if(pilot === null){
            TeamDropdown(
                options = teams,
                selectedItem = selectedTeam,
                onItemSelected = { team ->
                    selectedTeam = team
                    equipeId = team.getId().toString()
                }
            )
        }

        CustomTextField(
            value = idade,
            onValueChange = { idade = it },
            label = "Idade",
            modifier = Modifier.fillMaxWidth()
        )

        CustomTextField(
            value = pais,
            onValueChange = { pais = it },
            label = "País",
            modifier = Modifier.fillMaxWidth()
        )

        val coroutineScope = rememberCoroutineScope()  // Cria o CoroutineScope para a corrotina
        val context = LocalContext.current

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                if(nome.isEmpty() || idade.isEmpty() || pais.isEmpty()){
                    Toast.makeText(context, "Preencha todos os Campos!", Toast.LENGTH_SHORT).show()
                    return@Button
                }

                if(selectedTeam == null){
                    Toast.makeText(context, "Selecione uma equipe", Toast.LENGTH_SHORT).show()
                    return@Button
                }

                coroutineScope.launch {
                    try {
                        val pilot = Pilot(null, nome, equipeId, idade, pais)
                        val result = PilotRepository(db).addPilot(pilot)

                        Toast.makeText(context, "Resultado: $result", Toast.LENGTH_SHORT).show()
                        onNavigateToListPilots()
                    } catch (e: Exception) {
                        Toast.makeText(context, "Erro: ${e.message}", Toast.LENGTH_SHORT).show()
                    }
                }
            },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text(text = "Salvar", color = MaterialTheme.colorScheme.secondary)
        }
    }
}