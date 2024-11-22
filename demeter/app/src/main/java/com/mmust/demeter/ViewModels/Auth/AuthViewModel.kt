package com.mmust.demeter.ViewModels.Auth

import android.content.Context
import android.widget.Toast
import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.android.libraries.identity.googleid.GoogleIdTokenParsingException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.FirebaseFirestore
import com.mmust.demeter.R
import com.mmust.demeter.Views.Routes.MainRoutes
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlin.coroutines.cancellation.CancellationException

data class SignInResult(val data: UserData?, val errorMessage: String?)
data class UserData(
    val userId: String?,
    val username: String?,
    val profilePictureUrl: String?,
    val initial: String? = null,
    val mail: String? = null
)
class AuthViewModel(context: Context) : ViewModel(){
    private val auth = FirebaseAuth.getInstance()
    private val firestore = FirebaseFirestore.getInstance()
    private val _authstate = MutableLiveData<AuthState>()
    val authState : LiveData<AuthState> = _authstate

    init {
        checkAuthState()
    }
    private fun checkAuthState(){
        if(auth.currentUser == null){
            _authstate.value = AuthState.Unauthorised
        }else{
            _authstate.value = AuthState.Authorised
        }
    }

    private val credentialManager = CredentialManager.create(context)

    private val getGoogleIdOption = GetGoogleIdOption.Builder()
        .setFilterByAuthorizedAccounts(false)
        .setServerClientId(context.getString(R.string.client))
        .setNonce("")
        .build()

    private val request = GetCredentialRequest.Builder()
        .addCredentialOption(getGoogleIdOption)
        .build()

    fun signInWithGoogle(context: Context, navigate: NavController) {
        viewModelScope.launch {
            try {
                val result = credentialManager.getCredential(context, request)
                val credential = GoogleIdTokenCredential.createFrom(result.credential.data)
                val googleCredentials = GoogleAuthProvider.getCredential(credential.idToken, null)
                val user = auth.signInWithCredential(googleCredentials).await().user

                user?.let {
                    createUserDocument(it.uid,it.email)
                    _authstate.value = AuthState.Authorised
                    Toast.makeText(context, "Welcome ${it.displayName}", Toast.LENGTH_LONG).show()
                    navigate.navigate(MainRoutes.Home.route) {
                        popUpTo(MainRoutes.Home.route) { inclusive = true }
                    }
                } ?: throw Exception("Authentication failed. User is null.")
            } catch (e: CancellationException) {
                throw e // Let coroutine handle this.
            } catch (e: GoogleIdTokenParsingException) {
                handleError(context, e.message)
            } catch (e: Exception) {
                handleError(context, e.localizedMessage ?: "An unknown error occurred.")
            }
        }
    }

    private fun handleError(context: Context, message: String?) {
        _authstate.value = AuthState.Error(message ?: "An error occurred.")
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }
    private fun createUserDocument(userId: String, email: String?) {
        val userCollection = firestore.collection("users").document(userId)
        val defaultData = hashMapOf(
            "createdAt" to System.currentTimeMillis(),
            "userId" to userId,
            "email" to email,
            "profileSetupComplete" to false
        )

        userCollection.set(defaultData)
            .addOnSuccessListener {

            }
            .addOnFailureListener { e ->
                e.printStackTrace()
            }
    }
    fun getSignedInUser(): UserData? = auth.currentUser?.run {
        UserData(
            userId = uid,
            username = displayName,
            profilePictureUrl = photoUrl?.toString(),
            initial = email?.get(0).toString(),
            mail = email
        )
    }
    fun signInWithEmail(email: String, pwd: String, context: Context, navigate: NavController) {
        viewModelScope.launch {
            try {
                val result = auth.createUserWithEmailAndPassword(email, pwd).await()
                val user = result.user
                user?.let {
                    createUserDocument(it.uid, email)
                    _authstate.value = AuthState.Authorised
                    Toast.makeText(context, "Sign-Up Successful", Toast.LENGTH_LONG).show()
                    navigate.navigate(MainRoutes.Home.route) {
                        popUpTo(MainRoutes.Auth.route) { inclusive = true }
                    }
                } ?: throw Exception("Authentication failed. User is null.")
            } catch (e: Exception) {
                handleError(context, e.message)
            }
        }
    }
    fun logInWithEmail(email: String, pwd: String, context: Context, navigate: NavController) {
        viewModelScope.launch {
            try {
                val result = auth.signInWithEmailAndPassword(email, pwd).await()
                val user = result.user

                user?.let {
                    _authstate.value = AuthState.Authorised
                    Toast.makeText(context, "Login Successful", Toast.LENGTH_LONG).show()

                    navigate.navigate(MainRoutes.Home.route) {
                        popUpTo(MainRoutes.Auth.route) { inclusive = true }
                    }
                } ?: throw Exception("Authentication failed. User is null.")
            } catch (e: Exception) {
                handleError(context, e.message)
            }
        }
    }
    fun signOut(){
        auth.signOut()
        _authstate.value = AuthState.Unauthorised
    }


}
sealed class AuthState{
    data object Authorised : AuthState()
    data object  Unauthorised : AuthState()
    data object Loading : AuthState()
    data class Error(val msg : String) : AuthState()
}