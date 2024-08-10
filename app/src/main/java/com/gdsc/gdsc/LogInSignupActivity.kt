package com.gdsc.gdsc

import android.app.Activity
import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.traceEventEnd
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.gdsc.gdsc.firebaseAuth.GoogleAuthClient
import com.gdsc.gdsc.firebaseAuth.SignInViewModel
import com.gdsc.gdsc.firebaseDB.FirestoreRepo
import com.gdsc.gdsc.firebaseDB.FirestoreViewModel
import com.gdsc.gdsc.ui.screen.SignInScreen
import com.gdsc.gdsc.ui.theme.GDSCTheme
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.auth.api.signin.GoogleSignInOptionsExtension
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlin.math.log


class LogInSignupActivity : ComponentActivity() {
    private val googleAuthClient by lazy{
        GoogleAuthClient(
            context = applicationContext,
            oneTapClient = Identity.getSignInClient(applicationContext)
        )
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FirebaseApp.initializeApp(this)
            GDSCTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {

                        val auth = FirebaseAuth.getInstance()
                        val viewModel = viewModel<SignInViewModel>()
                        val firestoreRepo = FirestoreRepo(this@LogInSignupActivity)
                        val firebaseViewModel = FirestoreViewModel(firestoreRepo)
                        val state by viewModel.state.collectAsState()
                        val sharedPreferences = this@LogInSignupActivity.getSharedPreferences("Login",
                            MODE_PRIVATE)
                        val launcher = rememberLauncherForActivityResult(
                            contract = ActivityResultContracts.StartIntentSenderForResult(),
                            onResult = {
                                if(it.resultCode == RESULT_OK){
                                    lifecycleScope.launch {
                                        val signInResult = googleAuthClient.signInWithIntent(
                                            it.data ?: return@launch,
                                            phoneNumber = Global.phoneNumber.toString(),
                                            collegeName = Global.collegeName.toString()
                                        )
                                        viewModel.onSignInResult(signInResult)
                                    }
                                }
                            }
                        )

                    val startForResult =
                        rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
                            if (result.resultCode == Activity.RESULT_OK) {
                                val intent = result.data
                                if (result.data != null) {
                                    lifecycleScope.launch {
                                        val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(intent)
                                        handleSignInResult(task, Global.login)
                                    }
                                }
                            }
                        }


//                    if(auth.currentUser == null){
//                        sharedPreferences.edit().putBoolean("status",false).apply()
//                    }
//                    else{
//                        sharedPreferences.edit().putBoolean("status",true).apply()
//                    }
                        LaunchedEffect(key1 = auth.currentUser!=null){
                            if(auth.currentUser!=null){
//                                Toast.makeText(applicationContext, "Sign In Successful", Toast.LENGTH_SHORT).show()
                                val intent = Intent(this@LogInSignupActivity, MainActivity::class.java)
                                startActivity(intent)
                                finish()
                            }
                        }
                        if(auth.currentUser == null){
                            SignInScreen(
                                state = state,
                                onSignInClick = {
                                    if(FirebaseAuth.getInstance().currentUser == null){
                                        startForResult.launch(googleSignInClient.signInIntent)
                                    }
                                    else{
                                        println("User:"+FirebaseAuth.getInstance().currentUser!!.uid)
                                    }
//                                    lifecycleScope.launch {
//                                        val signInIntent = googleAuthClient.signIn()
//                                        launcher.launch(
//                                            IntentSenderRequest.Builder(
//                                                signInIntent ?: return@launch
//                                            ).build()
//                                        )
//                                    }
                                }
                            )
                        }
                    }
                }
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        if(intent.getBooleanExtra("logout",false)){
            finishAffinity()
        }
        else{
            super.onBackPressed()
        }
    }

    private val googleSignInClient: GoogleSignInClient by lazy {
        getGoogleLoginAuth()
    }
    private fun getGoogleLoginAuth(): GoogleSignInClient {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.web_client_id))
            .requestEmail()
            .build()
        return GoogleSignIn.getClient(this, gso)
    }

    private suspend fun handleSignInResult(completedTask: Task<GoogleSignInAccount>, login:Boolean) {
        try {
            val account = completedTask.getResult(ApiException::class.java)
            // Now, you can authenticate with Firebase using the GoogleSignInAccount's ID token.
            if (account != null) {
                val idToken = account.idToken
                val credential = GoogleAuthProvider.getCredential(idToken, null)

                FirebaseAuth.getInstance().signInWithCredential(credential)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            // Sign-in successful. You can access the Firebase user here.
                            val user = FirebaseAuth.getInstance().currentUser
                            if (user != null) {
//                                val doc = FirebaseFirestore.getInstance().collection("users").document(user.uid).get()
//                                val exists = doc.result.exists()
//                                if(!exists){
                                if(!login){
                                    val hashMap = HashMap<String, Any>()
                                    hashMap["userClass"] = Global.collegeName.toString()
                                    hashMap["tags"] = 0
                                    hashMap["userName"] = user.displayName.toString()
                                    hashMap["userImage"] = user.photoUrl.toString()
                                    FirebaseFirestore.getInstance().collection("users").document(user.uid)
                                        .set(hashMap).addOnSuccessListener {
                                            Toast.makeText(this, "Done", Toast.LENGTH_SHORT).show()
                                        }
                                }
//                                }
                                println("Global:"+Global.collegeName)
                                println("Current User: ${user.uid}")
                                val intent = Intent(this@LogInSignupActivity, MainActivity::class.java)
                                startActivity(intent)
                                finish()
                            }
                        } else {
                            // Sign-in failed.
                            Log.w(TAG, "signInWithCredential:failure", task.exception)
                        }
                    }
                    .await()
            } else {
                // Handle the case where the GoogleSignInAccount is null.
            }
        } catch (e: ApiException) {
            // Handle ApiException here.
            Log.w(TAG, "signInResult:failed code=" + e.statusCode)
        }
    }

}



@Preview(showBackground = true)
@Composable
fun DefaultPreview4() {
    GDSCTheme {
//        SignInScreen()
    }
}