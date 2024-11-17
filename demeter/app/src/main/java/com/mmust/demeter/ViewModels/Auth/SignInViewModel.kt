import android.content.Context
import android.widget.Toast
import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.android.libraries.identity.googleid.GoogleIdTokenParsingException
import com.google.firebase.Firebase
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.auth
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
class SignInViewModel(context: Context) : ViewModel(){

    private val _state = MutableStateFlow(SignInState())
    val state = _state.asStateFlow()

    fun onSignInResult(result: SignInResult) {
        _state.update {
            it.copy(
                isSignInSuccessful = result.data != null,
                signInError = result.errorMessage
            )
        }
    }

    private val auth = Firebase.auth

    fun resetState() {
        _state.update { SignInState() }
    }
    val credentialManager = CredentialManager.create(context)

    val getGoogleIdOption = GetGoogleIdOption.Builder()
        .setFilterByAuthorizedAccounts(false)
        .setServerClientId(context.getString(R.string.client))
        .setNonce("")
        .build()

    val request = GetCredentialRequest.Builder()
        .addCredentialOption(getGoogleIdOption)
        .build()

    fun signin(context: Context,navigate:NavController) {
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
                navigate.navigate(MainRoutes.Home.route) {
                    popUpTo(MainRoutes.Auth.route) {
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
}