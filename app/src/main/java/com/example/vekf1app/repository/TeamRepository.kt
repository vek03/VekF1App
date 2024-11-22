package com.example.vekf1app.repository

import com.example.vekf1app.models.Pilot
import com.example.vekf1app.models.Team
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class TeamRepository(private val db: FirebaseFirestore) {
    private val teamsCollection = db.collection("teams")

    suspend fun getTeams(): List<Team> {
        return try {
            val snapshot = teamsCollection.get().await()
            snapshot.documents.map { document ->
                Team(
                    id = document.id,
                    nome = document.getString("nome") ?: "",
                    carro = document.getString("carro") ?: "",
                    imagem = document.getString("imagem") ?: "",
                    corPrimaria = document.getString("corPrimaria") ?: "",
                    corSecundaria = document.getString("corSecundaria") ?: ""
                )
            }
        } catch (e: Exception) {
            emptyList()
        }
    }

    suspend fun getTeam(id: String): Team? {
        return try {
            val document = teamsCollection.document(id).get().await()
            if (document.exists()) {
                Team(
                    id = document.id,
                    nome = document.getString("nome") ?: "",
                    carro = document.getString("carro") ?: "",
                    imagem = document.getString("imagem") ?: "",
                    corPrimaria = document.getString("corPrimaria") ?: "",
                    corSecundaria = document.getString("corSecundaria") ?: ""
                )
            } else {
                null
            }
        } catch (e: Exception) {
            null
        }
    }
}