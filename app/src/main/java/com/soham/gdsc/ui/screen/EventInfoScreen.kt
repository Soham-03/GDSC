package com.soham.gdsc.ui.screen

import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ModifierInfo
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController
import com.google.firebase.auth.FirebaseAuth
import com.soham.gdsc.MainActivity
import com.soham.gdsc.R
import com.soham.gdsc.firebaseDB.FirestoreRepo
import com.soham.gdsc.firebaseDB.FirestoreViewModel
import com.soham.gdsc.model.Event
import com.soham.gdsc.ui.component.EventSingleRow
import com.soham.gdsc.ui.component.RSVPButton
import com.soham.gdsc.ui.component.RSVPButtonPreview
import com.soham.gdsc.ui.theme.cardBackgroundGreen
import com.soham.gdsc.ui.theme.textColorGrey

@Composable
fun EventInfoScreen(
    eventId: String,
    eventName: String,
    eventDate: String,
    eventTime: String,
    eventImage: String,
    eventAbout: String,
    eventTags: String,
    quizStatus: Boolean,
    viewModel: FirestoreViewModel
){
    val context = LocalContext.current
    val userId = FirebaseAuth.getInstance().currentUser!!.uid
    val state by viewModel.state.collectAsState()
    LaunchedEffect(Unit){
        viewModel.checkEventInRegisteredList(eventId, userId)
    }
    LaunchedEffect(key1 = state.registeredEventExistStatus){
        viewModel.checkStatus(eventId, userId)
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState(), true)
    )
    {
        Box() {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
            )
            {
                Image(
                    painter = painterResource(id = R.drawable.ic_back),
                    contentDescription = "back btn",
                    modifier = Modifier
                        .size(64.dp)
                        .clickable {
                            val intent = Intent(context, MainActivity::class.java)
                            intent.flags =
                                Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
                            context.startActivity(intent)
                        }
                )
                Image(
                    painter = painterResource(id = R.drawable.ic_question),
                    contentDescription = "question ic",
                    modifier = Modifier
                        .size(30.dp)
                )
            }
            Text(
                text = "The Event",
                color = textColorGrey,
                fontSize = 24.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .align(Alignment.Center)
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Box(
            modifier = Modifier
                .padding(horizontal =  20.dp)
        )
        {
            EventSingleRow(
                backgroundColor = cardBackgroundGreen,
                eventImage = eventImage,
                eventName = eventName,
                eventDate = eventDate,
                eventTime = eventTime,
                onEventClick = {
                }
            )
        }
        Text(
            text = "About the event",
            color = textColorGrey,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(16.dp)
        )
        Text(
            text = eventAbout,
            color =  textColorGrey,
            fontSize = 12.sp,
            modifier = Modifier
                .padding(start = 16.dp, end = 16.dp)
                .height(200.dp)
                .verticalScroll(rememberScrollState(), true)
        )
        Text(
            text = "When and Where",
            color = textColorGrey,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(16.dp)
        )
        //when and where
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
        )
        {
            Image(
                painter = painterResource(id = R.drawable.outline_schedule_black_24dp),
                contentDescription = "When Icon",
                modifier = Modifier
                    .padding(16.dp)
                    .size(42.dp)
            )
            Text(
                text = eventDate,
                color = textColorGrey,
                fontSize = 16.sp,
            )
            Image(
                imageVector = Icons.Outlined.LocationOn,
                contentDescription = "Where Icon",
                modifier = Modifier
                    .padding(16.dp)
                    .size(42.dp)
            )
            Text(
                text = "Fr Agnel Ashram\n" +
                        "Bandstand Promenade\n" +
                        "W, Maharashtra, 400050",
                color = textColorGrey,
                fontSize = 16.sp,
                modifier = Modifier
                    .padding(end = 16.dp)
            )
        }
        Text(
            text = "Participation Tags",
            color = textColorGrey,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(16.dp)
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth()
        )
        {
            Text(
                text = "You'll get $eventTags GDSC Tags for attending the event\nWhat a Win!",
                color = textColorGrey,
                fontSize = 16.sp,
                modifier = Modifier
                    .weight(2f)
            )
            Image(
                painter = painterResource(id = R.drawable.gdsc_logo),
                contentDescription = "gdsc logo",
                modifier = Modifier
                    .size(64.dp)
                    .weight(1f)
            )
            Text(
                text = "'s",
                color = cardBackgroundGreen,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(start=4.dp)
                    .weight(0.2f)
            )
        }
        RSVPButton(viewModel, state, eventId, userId, eventName)
    }
}

@Preview
@Composable
fun EventInfoPreview(){
    EventInfoScreen(eventId = "",eventName = "", eventDate = "", eventTime = "", eventImage = "", eventAbout = "", eventTags = "", quizStatus = false, FirestoreViewModel(
        FirestoreRepo(context = LocalContext.current)
    ))
}