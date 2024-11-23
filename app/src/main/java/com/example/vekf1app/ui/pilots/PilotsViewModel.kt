package com.example.vekf1app.ui.pilots

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.vekf1app.models.Pilot
import com.example.vekf1app.models.Team
import com.example.vekf1app.repository.PilotRepository
import com.example.vekf1app.repository.TeamRepository
import kotlinx.coroutines.launch
import com.google.firebase.firestore.FirebaseFirestore

class PilotsViewModel : ViewModel() {
    // LiveData para armazenar a lista de pilotos e equipes
    private val _pilots = MutableLiveData<List<Pilot>>()
    val pilots: LiveData<List<Pilot>> get() = _pilots  // Acesso público

    private val _teams = MutableLiveData<List<Team>>()
    val teams: LiveData<List<Team>> get() = _teams  // Acesso público

    private val db = FirebaseFirestore.getInstance()  // Instância do Firestore

    // Funções para carregar dados dos pilotos e equipes
    init {
        loadPilots()
        loadTeams()
    }

    private fun loadPilots() {
        viewModelScope.launch {
            // Carregar pilotos de maneira assíncrona
            _pilots.value = PilotRepository(db).getPilots()  // Supondo que getPilots() seja uma função suspend
        }
    }

    private fun loadTeams() {
        viewModelScope.launch {
            // Carregar equipes de maneira assíncrona
            _teams.value = TeamRepository(db).getTeams()  // Supondo que getTeams() seja uma função suspend
        }
    }
}
