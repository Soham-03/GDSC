package com.gdsc.gdsc

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.google.firebase.auth.FirebaseAuth
import com.gdsc.gdsc.firebaseDB.FirestoreViewModel
import com.gdsc.gdsc.firebaseDB.FirestoreRepo
import com.gdsc.gdsc.ui.screen.ProfileScreen
import com.gdsc.gdsc.ui.theme.GDSCTheme
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions

class ProfileActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GDSCTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    val firestoreRepo = FirestoreRepo(this@ProfileActivity)
                    val firebaseViewModel = FirestoreViewModel(firestoreRepo)
                    var userDataList = remember { ArrayList<String?>()}
                    val currentUser = FirebaseAuth.getInstance().currentUser
                    LaunchedEffect(currentUser) {
                       firebaseViewModel.getUserData(currentUser!!.uid)
                    }
                    ProfileScreen(currentUser!!, firebaseViewModel, getGoogleLoginAuth())
                }
            }
        }
    }

    private fun getGoogleLoginAuth(): GoogleSignInClient {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.web_client_id))
            .requestEmail()
            .build()
        return GoogleSignIn.getClient(this, gso)
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview2() {
    GDSCTheme {

    }
}