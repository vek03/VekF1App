package com.example.vekf1app.viewModels

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
    private val _pilots = MutableLiveData<List<Pilot>>()
    val pilots: LiveData<List<Pilot>> get() = _pilots

    private val _teams = MutableLiveData<List<Team>>()
    val teams: LiveData<List<Team>> get() = _teams

    private val db = FirebaseFirestore.getInstance()

    init {
        loadPilots()
        loadTeams()
    }

    private fun loadPilots() {
        viewModelScope.launch {
            _pilots.value = PilotRepository(db).getPilots()
        }
    }

    private fun loadTeams() {
        viewModelScope.launch {
            _teams.value = TeamRepository(db).getTeams()
        }
    }
}
