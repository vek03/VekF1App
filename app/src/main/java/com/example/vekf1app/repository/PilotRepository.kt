package com.example.vekf1app.repository

import com.example.vekf1app.models.Pilot
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class PilotRepository(private val db: FirebaseFirestore) {
    private val pilotsCollection = db.collection("pilots")

    suspend fun addPilot(pilot: Pilot): Boolean {
        return try {
            val newPilotRef = pilotsCollection.add(
                mapOf(
                    "nome" to pilot.getNome(),
                    "equipeId" to pilot.getEquipeId(),
                    "idade" to pilot.getIdade(),
                    "pais" to pilot.getPais()
                )
            ).await()
            true
        } catch (e: Exception) {
            false
        }
    }

    suspend fun getPilots(): List<Pilot> {
        return try {
            val snapshot = pilotsCollection.get().await()
            snapshot.documents.map { document ->
                Pilot(
                    id = document.id,
                    nome = document.getString("nome") ?: "",
                    equipeId = document.getString("equipeId") ?: "",
                    idade = document.getString("idade") ?: "",
                    pais = document.getString("pais") ?: ""
                )
            }
        } catch (e: Exception) {
            emptyList()
        }
    }

    suspend fun getPilot(id: String): Pilot? {
        return try {
            val document = pilotsCollection.document(id).get().await() // Recupera o piloto pelo ID
            if (document.exists()) {
                Pilot(
                    nome = document.getString("nome") ?: "",
                    equipeId = document.getString("equipeKey") ?: "",
                    idade = document.getString("idade") ?: "",
                    pais = document.getString("pais") ?: ""
                )
            } else {
                null
            }
        } catch (e: Exception) {
            null
        }
    }

    suspend fun updatePilot(id: String, pilot: Pilot): Boolean {
        return try {
            val documentRef = pilotsCollection.document(id)
            documentRef.update(
                "nome", pilot.getNome(),
                "equipeId", pilot.getEquipeId(),
                "idade", pilot.getIdade(),
                "pais", pilot.getPais()
            ).await()
            true
        } catch (e: Exception) {
            false
        }
    }

    suspend fun deletePilot(id: String): Boolean {
        return try {
            pilotsCollection.document(id).delete().await()
            true
        } catch (e: Exception) {
            false
        }
    }
}