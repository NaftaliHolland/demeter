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
import com.mmust.demeter.Models.Auth.SignInState
import com.mmust.demeter.R
import com.mmust.demeter.Views.Routes.MainRoutes
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlin.coroutines.cancellation.CancellationException

data class SignInResult(val data: UserData?, val errorMessage: String?)
data class UserData(val userId: String?, val username: String?, val profilePictureUrl: String?)
class AuthViewModel(context: Context) : ViewModel(){
    private val auth = FirebaseAuth.getInstance()
    private val _authstate = MutableLiveData<AuthState>()
    val authState : LiveData<AuthState> = _authstate

    init {
        checkAuthState()
    }
    fun checkAuthState(){
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

    fun signInWithGoogle(context: Context, navigate:NavController) {
        viewModelScope.launch {
            try {
                val result = credentialManager.getCredential(context = context, request = request)
                val credential = result.credential
                val googleIdTokenCredential = GoogleIdTokenCredential
                    .createFrom(credential.data)
                val googleIdToken = googleIdTokenCredential.idToken
                val googleCredentials = GoogleAuthProvider.getCredential(googleIdToken, null)


                val user = auth.signInWithCredential(googleCredentials).await().user
                SignInResult(
                    data = user?.run {
                        UserData(
                            userId = uid,
                            username = displayName,
                            profilePictureUrl = photoUrl?.toString()
                        )
                    },
                    errorMessage = null
                )
                Toast.makeText(
                    context,"Welcome "+user?.displayName, Toast.LENGTH_LONG
                ).show()
                _authstate.value = AuthState.Authorised
                navigate.navigate(MainRoutes.Home.route){
                    popUpTo(MainRoutes.Home.route){
                        inclusive = true
                    }
                }
            }
            catch (e: Exception) {
                    e.printStackTrace()
                    if (e is CancellationException) throw e
                    SignInResult(
                        data = null,
                        errorMessage = e.message
                    )

            } catch (e: GoogleIdTokenParsingException) {

                Toast.makeText(
                    context,e.message, Toast.LENGTH_LONG
                ).show()
            }
        }
    }
    fun getSignedInUser(): UserData? = auth.currentUser?.run {
        UserData(
            userId = uid,
            username = displayName,
            profilePictureUrl = photoUrl?.toString()
        )
    }
    fun signInWithEmail(email : String, pwd : String,context: Context){
        _authstate.value = AuthState.Loading
        viewModelScope.launch {
            auth.createUserWithEmailAndPassword(email,pwd)
                .addOnCompleteListener { task ->
                    if(task.isSuccessful){
                        _authstate.value = AuthState.Authorised
                        Toast.makeText(
                            context,
                            "Sign Up Successful",
                            Toast.LENGTH_LONG
                        ).show()
                        _authstate.value = AuthState.Authorised
                    }else{
                        _authstate.value = AuthState.Unauthorised
                        Toast.makeText(
                            context,
                            task.exception?.message,
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
        }
    }
    fun logInWithEmail(email : String, pwd : String,context: Context,navigate: NavController){
        _authstate.value = AuthState.Loading
        viewModelScope.launch {
            auth.signInWithEmailAndPassword(email,pwd)
                .addOnCompleteListener { task ->
                    if(task.isSuccessful){
                        _authstate.value = AuthState.Authorised
                        Toast.makeText(
                            context,
                            "Login in Successful",
                            Toast.LENGTH_LONG
                        ).show()
                    }else{
                        _authstate.value = AuthState.Unauthorised
                        Toast.makeText(
                            context,
                            task.exception?.message,
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            if (authState.value is AuthState.Authorised){
                navigate.navigate(MainRoutes.Home.route) {
                    popUpTo(MainRoutes.Auth.route) {
                        inclusive = true
                    }
                }
            }
        }

    }
    fun signOut(){
        auth.signOut()
        _authstate.value = AuthState.Unauthorised
    }


}
sealed class AuthState{
    object Authorised : AuthState()
    object  Unauthorised : AuthState()
    object Loading : AuthState()
    data class Error(val msg : String) : AuthState()
}