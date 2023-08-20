package com.soham.gdsc

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.android.gms.auth.api.identity.Identity
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.soham.gdsc.firebaseAuth.GoogleAuthClient
import com.soham.gdsc.firebaseAuth.SignInViewModel
import com.soham.gdsc.firebaseDB.FirestoreViewModel
import com.soham.gdsc.firebaseDB.FirestoreRepo
import com.soham.gdsc.ui.screen.SignInScreen
import com.soham.gdsc.ui.theme.GDSCTheme
import kotlinx.coroutines.launch

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
//                    if(auth.currentUser == null){
//                        sharedPreferences.edit().putBoolean("status",false).apply()
//                    }
//                    else{
//                        sharedPreferences.edit().putBoolean("status",true).apply()
//                    }
                    LaunchedEffect(key1 = state.isSignInSuccessful){
                        if(state.isSignInSuccessful || auth.currentUser!=null){
                            Toast.makeText(applicationContext, "Sign In Successful", Toast.LENGTH_SHORT).show()
                            val intent = Intent(this@LogInSignupActivity, MainActivity::class.java)
                            startActivity(intent)
                            finish()
                        }
                    }
                    if(auth.currentUser == null){
                        SignInScreen(
                            state = state,
                            onSignInClick = {
                                lifecycleScope.launch {
                                    val signInIntent = googleAuthClient.signIn()
                                    launcher.launch(
                                        IntentSenderRequest.Builder(
                                            signInIntent ?: return@launch
                                        ).build()
                                    )
                                }
                            }
                        )
                    }
                }
            }
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