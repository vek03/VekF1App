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
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
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
import com.example.vekf1app.ui.components.DialogButton
import com.example.vekf1app.ui.components.TeamDropdown
import com.example.vekf1app.ui.components.TopAppBar
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditPilotScreen(
    onNavigateToListPilots: () -> Unit,
    pilotId: String,
    modifier: Modifier = Modifier
) {
    val user = FirebaseAuth.getInstance().currentUser
    val db = Firebase.firestore

    user?.let {
        val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
        var pilot by remember { mutableStateOf<Pilot?>(null) }

        LaunchedEffect(Unit) {
            pilot = PilotRepository(db).getPilot(pilotId)
        }

        if (pilot != null) {
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
                EditPilotBody(db, pilot, onNavigateToListPilots, innerPadding)
            }
        }
    }
}

@Composable
fun EditPilotBody(
    db: FirebaseFirestore,
    pilot: Pilot?,
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
        PilotEditInputForm(db, onNavigateToListPilots, pilot)
    }
}

@Composable
fun PilotEditInputForm(
    db: FirebaseFirestore,
    onNavigateToListPilots: () -> Unit,
    pilot: Pilot? = null,
    modifier: Modifier = Modifier
) {
    var nome by remember { mutableStateOf(pilot?.getNome() ?: "") }
    var idade by remember { mutableStateOf(pilot?.getIdade() ?: "") }
    var pais by remember { mutableStateOf(pilot?.getPais() ?: "") }

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_medium)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CustomTextField(
            value = nome,
            onValueChange = { nome = it },
            label = "Nome",
            modifier = Modifier.fillMaxWidth()
        )

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

        val coroutineScope = rememberCoroutineScope()
        val context = LocalContext.current

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                if(nome.isEmpty() || idade.isEmpty() || pais.isEmpty()){
                    Toast.makeText(context, "Preencha todos os Campos!", Toast.LENGTH_SHORT).show()
                    return@Button
                }

                coroutineScope.launch {
                    try {
                        pilot?.setNome(nome)
                        pilot?.setIdade(idade)
                        pilot?.setPais(pais)

                        val result = PilotRepository(db).updatePilot(pilot?.getId().toString(), pilot)

                        Toast.makeText(context, "Resultado: $result", Toast.LENGTH_SHORT).show()
                        onNavigateToListPilots()
                    } catch (e: Exception) {
                        Toast.makeText(context, "Erro: ${e.message}", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        ) {
            Text(text = "Salvar", color = MaterialTheme.colorScheme.secondary)
        }

        DialogButton(
            label = "Deletar",
            title = "Confirmar Exclusão",
            message = "Você tem certeza de que deseja deletar este item?",
            icon = {
                Icon(
                    androidx.compose.material.icons.Icons.Filled.Delete,
                    contentDescription = "Delete",
                    tint = MaterialTheme.colorScheme.secondary
                )
            },
            onConfirm = {
                coroutineScope.launch {
                    try {
                        val result = PilotRepository(db).deletePilot(pilot?.getId().toString())
                        Toast.makeText(context, "Resultado: $result", Toast.LENGTH_SHORT).show()
                        onNavigateToListPilots()
                    } catch (e: Exception) {
                        Toast.makeText(context, "Erro: ${e.message}", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        )
    }
}