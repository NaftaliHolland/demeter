package com.mmust.demeter.ViewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.FirebaseFirestore
import com.mmust.demeter.ViewModels.Auth.UserData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ProfileViewModel(user: UserData? = null,) : ViewModel() {
    private val firestore = FirebaseFirestore.getInstance()
    private var _greenhouseIds: MutableStateFlow<List<String>?> = MutableStateFlow(emptyList())
    val greenhouseIds: StateFlow<List<String>?> = _greenhouseIds
    var errorList = emptyList<String>()

    init {
        viewModelScope.launch {
            getGreenhouseIds(
                user?.userId!!,
                onSuccess = {list ->
                    _greenhouseIds.value = list
                },
                onFailure = {e->
                    errorList += e.message.toString()
                }
            )
        }
    }
    private fun getGreenhouseIds(
        userId: String,
        onSuccess: (List<String>?) -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        val firestore = FirebaseFirestore.getInstance()

        val userRef = firestore.collection("users").document(userId)

        try {
            userRef.get()
                .addOnSuccessListener { document ->
                    if (document.exists()) {
                        val greenhouseIds = document.get("greenhouseIds") as? List<String>
                        onSuccess(greenhouseIds)
                    } else {
                        onSuccess(null)
                    }
                }
                .addOnFailureListener { exception ->
                    onFailure(exception)
                }
        }catch(e : Exception){
            println(e.message)
        }
    }

    fun getGreenhouseSensors(
        userId: String,
        greenhouseId: String,
        onSuccess: (List<String>?) -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        val firestore = FirebaseFirestore.getInstance()

        val greenhouseRef = firestore.collection("users")
            .document(userId)
            .collection("greenhouses")
            .document(greenhouseId)

        greenhouseRef.get()
            .addOnSuccessListener { document ->
                if (document.exists()) {
                    val sensors = document.get("sensors") as? List<String>
                    onSuccess(sensors)
                } else {
                    onSuccess(null)
                }
            }
            .addOnFailureListener { exception ->
                onFailure(exception)
            }
    }
}