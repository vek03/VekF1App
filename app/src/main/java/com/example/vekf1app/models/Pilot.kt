package com.example.vekf1app.models

import com.example.vekf1app.repository.TeamRepository
import com.google.firebase.firestore.FirebaseFirestore

data class Pilot(
    private var id: String? = null,
    private var nome: String,
    private var equipeId: String,
    private var idade: String,
    private var pais: String
) {
    fun getId(): String? {
        return id
    }

    fun setId(novoId: String) {
        id = novoId
    }

    fun getNome(): String {
        return nome
    }

    fun setNome(novoNome: String) {
        nome = novoNome
    }

    fun getEquipeId(): String {
        return equipeId
    }

    fun setEquipeId(novaEquipeId: String) {
        equipeId = novaEquipeId
    }

    fun getIdade(): String {
        return idade
    }

    fun setIdade(novaIdade: String) {
        idade = novaIdade
    }

    fun getPais(): String {
        return pais
    }

    fun setPais(novoPais: String) {
        pais = novoPais
    }

    suspend fun team(db: FirebaseFirestore): Team? {
        if (equipeId.isEmpty()) {
            return null
        }

        return TeamRepository(db).getTeam(equipeId)
    }
}