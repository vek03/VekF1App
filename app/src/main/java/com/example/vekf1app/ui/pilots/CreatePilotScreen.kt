package com.example.vekf1app.ui.pilots

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.firebase.auth.FirebaseAuth

@Composable
fun CreatePilotScreen(onNavigateToListPilots: () -> Unit) {
    val user = FirebaseAuth.getInstance().currentUser
    user?.let {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Listagem de Itens")
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = {
                //
            }) {
                Text("Criar Item")
            }
        }
    }
}
