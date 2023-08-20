package com.soham.gdsc

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
import com.soham.gdsc.firebaseDB.FirestoreViewModel
import com.soham.gdsc.firebaseDB.FirestoreRepo
import com.soham.gdsc.ui.screen.ProfileScreen
import com.soham.gdsc.ui.theme.GDSCTheme

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
                    ProfileScreen(currentUser!!, firebaseViewModel)
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview2() {
    GDSCTheme {

    }
}