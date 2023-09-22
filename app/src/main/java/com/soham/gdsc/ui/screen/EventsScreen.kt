package com.soham.gdsc.ui.screen

import android.content.Intent
import android.content.Intent.FilterComparison
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.ScrollableState
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Done
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.soham.gdsc.EventInfoActivity
import com.soham.gdsc.Greeting
import com.soham.gdsc.firebaseDB.FirebaseState
import com.soham.gdsc.firebaseDB.FirestoreRepo
import com.soham.gdsc.firebaseDB.FirestoreViewModel
import com.soham.gdsc.model.Event
import com.soham.gdsc.navigation.BottomBarScreen
import com.soham.gdsc.ui.component.EventSingleRow
import com.soham.gdsc.ui.component.SearchBar
import com.soham.gdsc.ui.theme.*
import kotlinx.coroutines.launch
import java.net.URL

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun EventsScreen(
    viewModel: FirestoreViewModel
){
    val context = LocalContext.current
    val state by viewModel.state.collectAsState()
    var list by remember {
        mutableStateOf(ArrayList<Event>())
    }
    list = state.isEventsSuccess
    val coroutineScope = rememberCoroutineScope()
    val pullRefreshState = rememberPullRefreshState(refreshing = state.isEventRefreshing, onRefresh = {
        coroutineScope.launch{
            viewModel.getAllEventsRefresh()
        }
    })
    Box(
        modifier = Modifier
            .pullRefresh(pullRefreshState)
    ){
        Column()
        {
            Text(
                text = "Our Events",
                color = textColorGrey,
                fontSize = 24.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(top = 12.dp)
                    .fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(20.dp))
            SearchBar()
            Spacer(modifier = Modifier.height(8.dp))
            var selectedUpcoming by remember{ mutableStateOf(true) }
            var selectedPast by remember{ mutableStateOf(false) }
            Row(
                modifier = Modifier
                    .padding(horizontal = 16.dp, vertical = 6.dp)
                    .wrapContentWidth()
                    .align(Alignment.End)
            ){
                Card(
                    backgroundColor = if(selectedUpcoming){ Yellow }else{ LightBlue },
                    shape = RoundedCornerShape(20.dp),
                    border = BorderStroke(2.dp, Color.Black),
                    modifier = Modifier
                        .clip(RoundedCornerShape(20.dp))
                        .clickable {
                            if (!selectedUpcoming) {
                                list = filterUpcoming(state)
                                selectedUpcoming = true
                                selectedPast = false
                            }
                        }
                        .padding(start = 16.dp)
                        .wrapContentWidth()
                )
                {
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    )
                    {
                        AnimatedVisibility(visible = selectedUpcoming) {
                            Image(
                                imageVector = Icons.Outlined.Done,
                                contentDescription = "Chip Selected",
                                modifier = Modifier
                                    .padding(start = 8.dp)
                                    .size(24.dp)
                            )
                        }
                        Text(
                            text = "Upcoming Events",
                            fontSize = 14.sp,
                            modifier = Modifier
                                .padding(8.dp)
                        )
                    }
                }
                //2
                Card(
                    backgroundColor = if(selectedPast){ Yellow }else{ LightBlue },
                    shape = RoundedCornerShape(20.dp),
                    border = BorderStroke(2.dp, Color.Black),
                    modifier = Modifier
                        .clip(RoundedCornerShape(20.dp))
                        .clickable {
                            if (!selectedPast) {
                                list = filterPast(state)
                                selectedPast = true
                                selectedUpcoming = false
                            }
                        }
                        .padding(start = 16.dp)
                        .wrapContentWidth()
                )
                {
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    )
                    {
                        AnimatedVisibility(visible = selectedPast) {
                            Image(
                                imageVector = Icons.Outlined.Done,
                                contentDescription = "Chip Selected",
                                modifier = Modifier
                                    .padding(start = 8.dp)
                                    .size(24.dp)
                            )
                        }
                        Text(
                            text = "Past Events",
                            fontSize = 14.sp,
                            modifier = Modifier
                                .padding(8.dp)
                        )
                    }
                }
            }
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(14.dp),
                modifier = Modifier
                    .padding(horizontal = 16.dp)
            )
            {
                if(selectedPast){
                    val pastList = filterPast(state)
                    if(pastList.isNotEmpty()){
                        for(event in pastList){
                            item {
                                EventSingleRow(
                                    backgroundColor = cardBackgroundGreen,
                                    eventImage = event.eventImage,
                                    eventName = event.eventName,
                                    eventDate = event.eventDate,
                                    eventTime = event.eventTime,
                                    onEventClick = {
                                        Toast.makeText(context, "Event is over now.", Toast.LENGTH_SHORT).show()
                                    }
                                )
                            }
                        }
                    }
                    else{
                        item {
                            Text(
                                text = "No Events to show here :(",
                                fontSize = 24.sp,
                                color = textColorGrey,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
                else{
                    val upcomingList = filterUpcoming(state)
                    if(upcomingList.isNotEmpty()){
                        for(event in upcomingList){
                            item {
                                EventSingleRow(
                                    backgroundColor = cardBackgroundGreen,
                                    eventImage = event.eventImage,
                                    eventName = event.eventName,
                                    eventDate = event.eventDate,
                                    eventTime = event.eventTime,
                                    onEventClick = {
                                        val intent = Intent(context, EventInfoActivity::class.java)
                                        event.apply{
                                            intent.putExtra("eventId",eventId)
                                            intent.putExtra("eventName",eventName)
                                            intent.putExtra("eventImage",eventImage)
                                            intent.putExtra("eventDate",eventDate)
                                            intent.putExtra("eventTime",eventTime)
                                            intent.putExtra("eventTags",eventTags)
                                            intent.putExtra("eventAbout",eventAbout)
                                            intent.putExtra("quizStatus",quizStatus)
                                            intent.putExtra("upcoming",upcoming)
                                            intent.putExtra("eventLink",eventLink)
                                        }
                                        context.startActivity(intent)
                                    }
                                )
                            }
                        }
                    }
                    else{
                        item {
                            Text(
                                text = "No Events to show here :(",
                                fontSize = 24.sp,
                                color = textColorGrey,
                                fontWeight = FontWeight.Bold,
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }

//            for (i in 0..10){
//                item {
//                    EventSingleRow(
//                        backgroundColor = cardBackgroundGreen,
//                        eventImage = "",
//                        eventName = "Bit N Builds",
//                        eventDate = "24-26 November",
//                        eventTime = "10:10 am - 10:59 pm",
//                        onEventClick = {
//                            val intent = Intent(context, EventInfoActivity::class.java)
//                            context.startActivity(intent)
////                            navController.navigate(BottomBarScreen.EventInfo.route)
//                        }
//                    )
//                }
//            }
                item {
                    Spacer(modifier = Modifier.height(100.dp))
                }
            }
        }
        PullRefreshIndicator(
            refreshing = state.isEventRefreshing,
            state = pullRefreshState,
            modifier = Modifier.align(Alignment.TopCenter),
            backgroundColor = Color.White,
            contentColor = Color.Black
        )
    }

}

fun filterPast(state: FirebaseState): ArrayList<Event> {
    val pastList = ArrayList<Event>()
    if(state.isEventsSuccess.isNotEmpty()){
        for(event in state.isEventsSuccess){
            if(!event.upcoming){
                pastList.add(event)
            }
        }
    }
    return pastList
}

fun filterUpcoming(state: FirebaseState): ArrayList<Event> {
    val upcomingList = ArrayList<Event>()
    if(state.isEventsSuccess.isNotEmpty()){
        for(event in state.isEventsSuccess){
            if(event.upcoming){
                upcomingList.add(event)
            }
        }
    }
    return upcomingList
}

@Preview
@Composable
fun EventsScreenPreview(){
//    EventsScreen(navController = rememberNavController())
}