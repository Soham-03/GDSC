package com.gdsc.gdsc.firebaseAuth

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.IntentSender
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import com.gdsc.gdsc.R
import com.gdsc.gdsc.firebaseDB.FirestoreRepo
import com.gdsc.gdsc.firebaseDB.FirestoreViewModel
import com.google.android.gms.auth.api.credentials.CredentialPickerConfig.Prompt.SIGN_UP
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.BeginSignInRequest.GoogleIdTokenRequestOptions
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.tasks.await
import java.util.concurrent.CancellationException


class GoogleAuthClient(
    private val context: Context,
    private val oneTapClient: SignInClient
){
    private val auth = FirebaseAuth.getInstance()

    suspend fun signIn(): IntentSender? {
        val result = try{
            oneTapClient.beginSignIn(
                buildSignInRequest()
            ).await()
        }catch (e: java.lang.Exception){
            println("Inside Sign In Function")
            e.printStackTrace()
            if(e is CancellationException) throw e
            if(e is ApiException){
                val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestEmail()
                    .build()
                val mGoogleSignInClient = GoogleSignIn.getClient(context, gso)
                val conj = context as Activity
                conj.startActivityForResult(mGoogleSignInClient.signInIntent, SIGN_UP)
            }
            null
        }
        return result?.pendingIntent?.intentSender
    }


    suspend fun signInWithIntent(intent: Intent, phoneNumber: String, collegeName: String): SignInResult{
        val credential = oneTapClient.getSignInCredentialFromIntent(intent)
        val googleIdToken = credential.googleIdToken
        val googleCredentials = GoogleAuthProvider.getCredential(googleIdToken, null)
        return try{
            val user = auth.signInWithCredential(googleCredentials).await().user
            val repo = FirestoreRepo(context)
            val viewModel = FirestoreViewModel(repo)
            if (user != null) {
                viewModel.setUserData(user, collegeName, phoneNumber)
            }
            SignInResult(
                userData = user?.run {
                    UserData(
                        userId = uid,
                        userName = displayName,
                        profilePictureUrl = photoUrl?.toString(),
                        userEmail = email,
                        userPhoneNumber = phoneNumber
                    )
                },
                errorMessage = null
            )
        }catch (e: Exception){
            e.message
            println("Inside Second Function")
            if(e is CancellationException) throw e
            SignInResult(
                userData = null,
                errorMessage = e.message
            )
        }
    }

     private fun buildSignInRequest(): BeginSignInRequest{
         return BeginSignInRequest.Builder()
             .setGoogleIdTokenRequestOptions(
                 GoogleIdTokenRequestOptions.builder()
                     .setSupported(true)
                     .setFilterByAuthorizedAccounts(false)
                     .setServerClientId(context.getString(R.string.web_client_id))
                     .build()
             )
             .setAutoSelectEnabled(false)
             .build()
     }
}
