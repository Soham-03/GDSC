package com.soham.gdsc.ui.screen

import android.content.Intent
import android.content.Intent.FilterComparison
import android.text.TextUtils
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
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Done
import androidx.compose.material.icons.outlined.Search
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
import androidx.compose.ui.text.input.TextFieldValue
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
    val filteredEvents = remember { mutableStateOf(ArrayList<Event>()) }
    var change by remember{
        mutableStateOf(false)
    }
    val coroutineScope = rememberCoroutineScope()
    val pullRefreshState = rememberPullRefreshState(refreshing = state.isEventRefreshing, onRefresh = {
        coroutineScope.launch{
            viewModel.getAllEventsRefresh()
        }
    })
    var text by remember{ mutableStateOf(TextFieldValue(""))}
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
//            SearchBar()
            Box(modifier = Modifier.padding(horizontal = 16.dp))
            {
                filteredEvents.value = if(text.text.isEmpty() || text.text.isBlank()){
                    state.isEventsSuccess
                } else{
                    search(text, list)
                }
                Box(modifier = Modifier
                    .padding(top = 8.dp)
                    .fillMaxWidth()
                    .height(50.dp)
                    .border(2.dp, Color.Black, RoundedCornerShape(30.dp))
                    .background(Yellow, RoundedCornerShape(30.dp))
                )
                TextField(
                    value = text,
                    onValueChange = { te->
                        text = te
                        filteredEvents.value = if(text.text.isEmpty() || text.text.isBlank()){
                            change = false
                            list
                        } else{
                            change = true
                            search(text, list)
                        }
//                        list = filteredEvents.value
                    },
                    leadingIcon = {
                        Image(
                            imageVector = Icons.Outlined.Search,
                            contentDescription = "Search Image",
                            modifier = Modifier.align(
                                Alignment.Center
                            )
                                .clickable {
                                    list = search(text, list)
                                }
                        )
                    },
                    placeholder = {
                        Text(text = "Search", color = Color.Black)
                    },
                    colors = TextFieldDefaults.textFieldColors(
                        textColor = textColorGrey,
                        backgroundColor = Color.White,
                        leadingIconColor = Color.Black,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        cursorColor = Color.Black
                    ),
                    shape = RoundedCornerShape(30.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .border(2.dp, Color.Black, RoundedCornerShape(30.dp))
                )
            }
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
                    var printedOnce = false
                    if(change) {
                        for(event in pastList){
                            if(event.eventName.contains(text.text, true)){
                                printedOnce = true
                                filteredEvents.value.add(event)
                                println("Filtered: "+event.eventName)
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
                            else{
                                if(!printedOnce){
                                    printedOnce = true
                                    item {
                                        Text(
                                            text = "Error 404, Not found",
                                            fontSize = 24.sp,
                                            color = textColorGrey,
                                            fontWeight = FontWeight.Bold,
                                            textAlign = TextAlign.Center
                                        )
                                    }
                                }
                            }
                        }
                    }
                    else{
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
                }
                else if(selectedUpcoming){
                    val upcomingList = filterUpcoming(state)
                    var printedOnce = false
                    if(change) {
                            for(event in upcomingList){
                                if(event.eventName.contains(text.text, true)){
                                    printedOnce = true
                                    filteredEvents.value.add(event)
                                    println("Filtered: "+event.eventName)
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
                                else{
                                    if(!printedOnce){
                                        printedOnce = true
                                        item {
                                            Text(
                                                text = "Error 404, Not found",
                                                fontSize = 24.sp,
                                                color = textColorGrey,
                                                fontWeight = FontWeight.Bold,
                                                textAlign = TextAlign.Center
                                            )
                                        }
                                    }
                                }
                            }
                    }
                    else {
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

fun search(textFieldValue: TextFieldValue, listOfEvents: ArrayList<Event>): ArrayList<Event> {
    var list = ArrayList<Event>()
    if(!TextUtils.isEmpty(textFieldValue.text)){
        for(event in listOfEvents){
            if(event.eventName.contains(textFieldValue.text, true)){
                list.add(event)
            }
        }
    }
    else{
        list = listOfEvents
    }
    println(list)
    return list
}

@Preview
@Composable
fun EventsScreenPreview(){
//    EventsScreen(navController = rememberNavController())
}