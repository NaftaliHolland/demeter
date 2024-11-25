package com.mmust.demeter.ViewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.auth.FirebaseAuth
import com.mmust.demeter.Models.GreenHouse
import com.mmust.demeter.network.GreenHouseApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class GreenHouseViewModel : ViewModel() {
    private val _greenHouses: MutableStateFlow<List<GreenHouse>> = MutableStateFlow(listOf())
    val greenHouses: StateFlow<List<GreenHouse>> = _greenHouses

    private val _isLoading: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _error: MutableStateFlow<String?> = MutableStateFlow(null)
    val error: StateFlow<String?> = _error

    private val firestore = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()

    init {
        getGreenHouses()
    }

    private fun getGreenHouses() {
        val currentUser = auth.currentUser
        if (currentUser != null) {
            val userId = currentUser.uid
            fetchGreenHouses(userId)
        } else {
            _error.value = "User not authenticated"
        }
    }
    private fun fetchGreenHouses(userId: String) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val snapshot = firestore.collection("users")
                    .document(userId)
                    .collection("greenhouses")
                    .get()
                    .await()
                val greenHousesJson = snapshot.documents.map { document ->
                    document.data
                }
                println(greenHousesJson)

                val greenHouses = snapshot.toObjects(GreenHouse::class.java)
                _greenHouses.value = greenHouses

            } catch (e: Exception) {
                _error.value = e.message
            } finally {
                _isLoading.value = false
            }
        }
    }
}