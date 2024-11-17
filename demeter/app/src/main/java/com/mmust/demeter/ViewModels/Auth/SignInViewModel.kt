import android.content.Context
import android.widget.Toast
import androidx.compose.ui.platform.LocalContext
import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.android.libraries.identity.googleid.GoogleIdTokenParsingException
import com.mmust.demeter.Models.Auth.SignInState
import com.mmust.demeter.R
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SignInViewModel(context: Context) : ViewModel(){

    private val _state = MutableStateFlow(SignInState())
    val state = _state.asStateFlow()

    fun onSignInResult() {
        _state.update {
            it.copy(
                isSignInSuccessful = true
            )
        }
    }


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

    fun signin(context: Context){
        viewModelScope.launch {
            try {
                val result = credentialManager.getCredential(context = context,request = request)
                val credential = result.credential
                val googleIdTokenCredential = GoogleIdTokenCredential
                    .createFrom(credential.data)
                val googleIdToken = googleIdTokenCredential.idToken
                onSignInResult()
                Toast.makeText(
                    context,"Signed in", Toast.LENGTH_LONG
                ).show()
            } catch (e: GoogleIdTokenParsingException) {

                Toast.makeText(
                    context,e.message, Toast.LENGTH_LONG
                ).show()
            }
        }
    }
}