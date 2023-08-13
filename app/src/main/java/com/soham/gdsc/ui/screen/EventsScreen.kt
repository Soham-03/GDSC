package com.soham.gdsc.ui.screen

import android.content.Intent
import android.content.Intent.FilterComparison
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.ScrollableState
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Done
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.soham.gdsc.EventInfoActivity
import com.soham.gdsc.Greeting
import com.soham.gdsc.navigation.BottomBarScreen
import com.soham.gdsc.ui.component.EventSingleRow
import com.soham.gdsc.ui.component.SearchBar
import com.soham.gdsc.ui.theme.*
import java.net.URL

@Composable
fun EventsScreen(
    navController : NavHostController
){
    val context = LocalContext.current
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
            for (i in 0..10){
                item {
                    EventSingleRow(
                        backgroundColor = cardBackgroundGreen,
                        eventImage = "",
                        eventName = "Bit N Builds",
                        eventDate = "24-26 November",
                        eventTime = "10:10 am - 10:59 pm",
                        onEventClick = {
                            val intent = Intent(context, EventInfoActivity::class.java)
                            context.startActivity(intent)
//                            navController.navigate(BottomBarScreen.EventInfo.route)
                        }
                    )
                }
            }
            item { 
                Spacer(modifier = Modifier.height(100.dp))
            }
        }
    }
}

@Preview
@Composable
fun EventsScreenPreview(){
    EventsScreen(navController = rememberNavController())
}