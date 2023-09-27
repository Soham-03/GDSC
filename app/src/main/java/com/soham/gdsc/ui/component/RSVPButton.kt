package com.soham.gdsc.ui.component

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.browser.customtabs.CustomTabsIntent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat.startActivity
import com.soham.gdsc.GoogleRSVPActivity
import com.soham.gdsc.firebaseDB.FirebaseState
import com.soham.gdsc.firebaseDB.FirestoreViewModel
import com.soham.gdsc.ui.theme.Yellow
import com.soham.gdsc.ui.theme.textColorGrey
import kotlinx.coroutines.launch

@Composable
fun RSVPButton(
    viewModel: FirestoreViewModel,
    state: FirebaseState,
    eventId: String,
    uid: String,
    eventName: String,
    eventTags: String,
    eventLink: String,
    context: Context,
    openLink: () -> Unit
){    Box(modifier = Modifier.padding(16.dp))
    {
        Box(
            modifier = Modifier
                .padding(top = 8.dp)
                .fillMaxWidth()
                .height(50.dp)
                .border(2.dp, Color.Black, RoundedCornerShape(30.dp))
                .background(Yellow, RoundedCornerShape(30.dp))
        )
        var text by remember { mutableStateOf("Register For the event") }
        val launcher: ManagedActivityResultLauncher<Intent, ActivityResult> = rememberLauncherForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { _ ->
            // Handle the result if needed
        }
        val coroutineScope = rememberCoroutineScope()
        val getLocationOnClick: () -> Unit = {
            coroutineScope.launch {
                if(text == "Confirmed"){
                    Toast.makeText(context, "Your seat is confirmed. See you at the event :)", Toast.LENGTH_SHORT).show()
                }
                else if(text == "Waiting for confirmation"){
                    Toast.makeText(context, "Have you confirmed your RSVP from the link, If done just sit back and relax :)", Toast.LENGTH_SHORT).show()
                    val intent = Intent(context, GoogleRSVPActivity::class.java)
                    intent.putExtra("url",eventLink)
                    context.startActivity(intent)
                }
                else{
                    viewModel.registerForEvent(eventId, uid, eventName = eventName, eventTags)
                    openLink.invoke()

                }
            }
        }
            if(state.eventRegistrationStatus == "Waiting for confirmation"){
                text = state.eventRegistrationStatus
            }
            else if(state.eventRegistrationStatus == "Confirmed"){
                text = state.eventRegistrationStatus
            }
//        }

        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .background(Color.White, RoundedCornerShape(30.dp))
                .border(2.dp, Color.Black, RoundedCornerShape(30.dp))
                .clickable {
                    text = "Waiting for confirmation"
//                    text = if (!state.registeredEventExistStatus) {
//                        "Register for the Event"
//                    } else {
//                        state.eventRegistrationStatus
//                    }
                    getLocationOnClick.invoke()
                }
        )
        {
            Text(
                text = text,
                color = textColorGrey,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
            )
        }
    }
}

@Preview
@Composable
fun RSVPButtonPreview(){
//    RSVPButton()
}