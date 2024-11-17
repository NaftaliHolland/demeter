import android.content.Context
import android.widget.Toast
import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.android.libraries.identity.googleid.GoogleIdTokenParsingException
import com.mmust.demeter.R


class GoogleAuthUiClient(context: Context){
    val credentialManager = CredentialManager.create(context)

    val getGoogleIdOption = GetGoogleIdOption.Builder()
        .setFilterByAuthorizedAccounts(false)
        .setServerClientId(context.getString(R.string.client))
        .setNonce("")
        .build()

    val request = GetCredentialRequest.Builder()
        .addCredentialOption(getGoogleIdOption)
        .build()

    suspend fun signin(context: Context){
        try {
            val result = credentialManager.getCredential(context = context,request = request)
            val credential = result.credential
            val googleIdTokenCredential = GoogleIdTokenCredential
                .createFrom(credential.data)
            val googleIdToken = googleIdTokenCredential.idToken

            Toast.makeText(
                context,"Signed in",Toast.LENGTH_LONG
            ).show()
        } catch (e: GoogleIdTokenParsingException) {
            Toast.makeText(
                context,e.message,Toast.LENGTH_LONG
            ).show()
        }
    }
}