package com.soham.gdsc

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.soham.gdsc.firebaseDB.FirestoreRepo
import com.soham.gdsc.firebaseDB.FirestoreViewModel
import com.soham.gdsc.ui.screen.EventInfoScreen
import com.soham.gdsc.ui.theme.GDSCTheme

class EventInfoActivity : ComponentActivity() {
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
                    EventInfoScreen(
                        eventId = intent.getStringExtra("eventId").toString(),
                        eventName = intent.getStringExtra("eventName").toString(),
                        eventDate = intent.getStringExtra("eventDate").toString(),
                        eventTime = intent.getStringExtra("eventTime").toString(),
                        eventImage = intent.getStringExtra("eventImage").toString(),
                        eventTags = intent.getStringExtra("eventTags").toString(),
                        eventAbout = intent.getStringExtra("eventAbout").toString(),
                        quizStatus = intent.getBooleanExtra("quizStatus",false),
                        viewModel = viewModel
                    )
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview3() {
    GDSCTheme {

    }
}