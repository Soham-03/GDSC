package com.soham.gdsc.ui.screen

import androidx.compose.foundation.gestures.ScrollableState
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.soham.gdsc.Greeting
import com.soham.gdsc.ui.component.EventSingleRow
import com.soham.gdsc.ui.component.SearchBar
import com.soham.gdsc.ui.theme.ProductSans
import com.soham.gdsc.ui.theme.cardBackgroundGreen
import com.soham.gdsc.ui.theme.textColorGrey
import java.net.URL

@Composable
fun EventsScreen(){
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
                        eventTime = "10:10 am - 10:59 pm"
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
    EventsScreen()
}