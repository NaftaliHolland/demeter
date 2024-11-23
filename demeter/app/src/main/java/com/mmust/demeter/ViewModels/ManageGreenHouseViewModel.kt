package com.mmust.demeter.ViewModels

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.FirebaseFirestore
import com.mmust.demeter.ViewModels.Auth.UserData
import java.util.UUID
import com.google.firebase.firestore.ktx.toObject
import kotlinx.coroutines.launch


data class Greenhouse(
    val greenhouseId: String? = null,
    val madeAt: Long? = null,
    val sensors: List<String>? = null,
    val ownerId: String? = null)
data class DevicePayload(
    val devEui: String? = null,
    val data: Map<String, Any>? = null,
    val receivedAt: String? = null
)
class ManageGreenHouseViewModel(private val user: UserData) : ViewModel() {
    private val firestore = FirebaseFirestore.getInstance()
    private var _greenhouseIds: MutableLiveData<List<String>?> = MutableLiveData()
    val greenhouseIds:LiveData<List<String>?> = _greenhouseIds
    var errorList = emptyList<String>()

    init {
        viewModelScope.launch {
            getGreenhouseIds(
                user.userId!!,
                onSuccess = {list ->
                    _greenhouseIds.value = list
                },
                onFailure = {e->
                    errorList += e.message.toString()
                }
            )
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


    fun updateGreenhouseSensors(
        userId: String,
        greenhouseId: String,
        sensors: List<String>,
        onSuccess: () -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        val firestore = FirebaseFirestore.getInstance()

        val greenhouseRef = firestore.collection("users")
            .document(userId)
            .collection("greenhouses")
            .document(greenhouseId)

        greenhouseRef.update("sensors", sensors)
            .addOnSuccessListener {
                onSuccess()
            }
            .addOnFailureListener { exception ->
                onFailure(exception)
            }
    }

    fun getPayloadByDevEui(devEui: String, onSuccess: (DevicePayload?) -> Unit, onFailure: (Exception) -> Unit) {
        val firestore = FirebaseFirestore.getInstance()

        val deviceRef = firestore.collection("devices").document(devEui)

        deviceRef.get()
            .addOnSuccessListener { documentSnapshot ->
                if (documentSnapshot.exists()) {
                    val payload = documentSnapshot.toObject<DevicePayload>()
                    onSuccess(payload)
                } else {
                    onSuccess(null)
                }
            }
            .addOnFailureListener { exception ->
                onFailure(exception)
            }
    }


    fun addGreenhouse(
        context: Context,
        user: UserData,
        greenhouseId : String,
        greenhouseSensors: List<String> = emptyList(),
        primaryCrop : String,
        location : String
    ): String {
        val greenhouseData = hashMapOf(
            "madeAt" to System.currentTimeMillis(),
            "sensors" to greenhouseSensors,
            "ownerId" to user.userId,
            "ownerEmail" to user.mail,
            "primaryCrop" to primaryCrop,
            "location" to location
        )

        val userGreenhouseRef = firestore.collection("users")
            .document(user.userId!!)
            .collection("greenhouses")
            .document(greenhouseId)

        userGreenhouseRef.set(greenhouseData)
            .addOnSuccessListener {
                Toast.makeText(context, "$greenhouseId Added", Toast.LENGTH_LONG).show()
            }
            .addOnFailureListener { e ->
                e.printStackTrace()
                Toast.makeText(context, "${e.message} Added", Toast.LENGTH_LONG).show()
            }

        val globalGreenhouseRef = firestore.collection("globalGreenhouses").document(greenhouseId)
        globalGreenhouseRef.set(greenhouseData)
            .addOnSuccessListener {
                println("Greenhouse $greenhouseId added successfully to global collection.")
            }
            .addOnFailureListener { e ->
                e.printStackTrace()
                println("Failed to add greenhouse to global collection: ${e.message}")
            }

        val userDocRef = firestore.collection("users").document(user.userId)
        userDocRef.get()
            .addOnSuccessListener { document ->
                if (document.exists()) {
                    val existingIds = document.get("greenhouseIds") as? MutableList<String> ?: mutableListOf()
                    if (!existingIds.contains(greenhouseId)) {
                        existingIds.add(greenhouseId)
                        userDocRef.update("greenhouseIds", existingIds)
                            .addOnSuccessListener {
                                println("Greenhouse ID updated successfully in user's document.")
                            }
                            .addOnFailureListener { e ->
                                e.printStackTrace()
                                println("Failed to update greenhouse IDs: ${e.message}")
                            }
                    }
                } else {
                    userDocRef.set(hashMapOf("greenhouseIds" to listOf(greenhouseId)))
                        .addOnSuccessListener {
                            println("Greenhouse ID field created successfully in user's document.")
                        }
                        .addOnFailureListener { e ->
                            e.printStackTrace()
                            println("Failed to create greenhouse ID field: ${e.message}")
                        }
                }
            }
            .addOnFailureListener { e ->
                e.printStackTrace()
                println("Failed to retrieve user document: ${e.message}")
            }
        getGreenhouseIds(
            user.userId,
            onSuccess = {list ->
                _greenhouseIds.value = list
            },
            onFailure = {e->
                errorList += e.message.toString()
            }
        )

        return greenhouseId
    }

    fun getGreenhouseIds(
        userId: String,
        onSuccess: (List<String>?) -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        val firestore = FirebaseFirestore.getInstance()

        val userRef = firestore.collection("users").document(userId)

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
    }



}