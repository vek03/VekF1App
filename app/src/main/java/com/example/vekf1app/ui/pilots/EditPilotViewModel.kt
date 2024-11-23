package com.example.vekf1app.ui.pilots

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.vekf1app.models.Pilot
import com.example.vekf1app.repository.PilotRepository
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch

class EditPilotViewModel : ViewModel() {
    private val _pilot = MutableLiveData<Pilot?>()
    val pilot: LiveData<Pilot?> get() = _pilot

    private val db = FirebaseFirestore.getInstance()

    fun loadPilot(pilotId: String) {
        viewModelScope.launch {
            try {
                val fetchedPilot = PilotRepository(db).getPilot(pilotId)
                _pilot.value = fetchedPilot
            } catch (e: Exception) {
                // Trate o erro aqui se necessário
            }
        }
    }

    fun updatePilot(pilot: Pilot) {
        viewModelScope.launch {
            try {
                Log.d("EditPilotViewModel", "Atualizando piloto com ID: ${pilot.getId()}")
                PilotRepository(db).updatePilot(pilot.getId().toString(), pilot)
            } catch (e: Exception) {
                Log.e("EditPilotViewModel", "Erro ao atualizar piloto", e)
            }
        }
    }

    fun deletePilot(pilotId: String) {
        viewModelScope.launch {
            try {
                PilotRepository(db).deletePilot(pilotId)
            } catch (e: Exception) {
                Log.e("EditPilotViewModel", "Erro ao deletar piloto", e)
            }
        }
    }
}