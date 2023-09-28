package com.gdsc.gdsc

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.gdsc.gdsc.firebaseDB.FirestoreRepo
import com.gdsc.gdsc.firebaseDB.FirestoreViewModel
import com.gdsc.gdsc.ui.component.BottomNavigation
import com.gdsc.gdsc.ui.theme.GDSCTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GDSCTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    val repo = FirestoreRepo(context = LocalContext.current)
                    val viewModel = FirestoreViewModel(repo)
                    val state by viewModel.state.collectAsState()
                    var show by remember { mutableStateOf(true) }
//                    AnimatedVisibility(visible = state.isFlagShipEventSuccess.isNotEmpty()) {
                        BottomNavigation(viewModel = viewModel)
//                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    GDSCTheme {
        Greeting("Android")
    }
}