package com.soham.gdsc.ui.screen

import android.content.ClipData.Item
import android.content.Intent
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.soham.gdsc.Greeting
import com.soham.gdsc.ProfileActivity
import com.soham.gdsc.R
import com.soham.gdsc.ui.component.FlagshipEventSingleRow
import com.soham.gdsc.ui.component.TopMonthRankGraph
import com.soham.gdsc.ui.theme.LightBlue
import com.soham.gdsc.ui.theme.LightRed
import com.soham.gdsc.ui.theme.Yellow
import com.soham.gdsc.ui.theme.textColorGrey

@Composable
fun HomeScreen(){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .verticalScroll(rememberScrollState())
    )
    {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
        )
        {
            Column()
            {
                Text(
                    text = "Welcome\nBack",
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    fontSize = 36.sp,
                    modifier = Modifier
                        .padding(top = 30.dp, start = 20.dp, end = 20.dp)
                )
                Text(
                    text = "UserName",
                    fontWeight = FontWeight.Bold,
                    color = textColorGrey,
                    fontSize = 20.sp,
                    modifier = Modifier
                        .padding(start = 20.dp, end = 20.dp)
                )
            }
            val context = LocalContext.current
            Image(
                painter = painterResource(id = R.drawable.ic_user),
                contentDescription = "profile icon",
                modifier = Modifier
                    .padding(horizontal = 20.dp)
                    .size(64.dp)
                    .clickable {
                        val intent = Intent(context,ProfileActivity::class.java)
                        context.startActivity(intent)
                    }
            )
        }
        
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(top = 20.dp, start = 16.dp, end = 16.dp, bottom = 14.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = "Flagship Events",
                fontSize = 24.sp,
            )
            Text(
                text = "See More",
                fontSize = 16.sp,
                color = Color.Blue,
                textDecoration = TextDecoration.Underline
            )
        }
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier
                .padding(start = 16.dp, bottom = 16.dp)
        )
        {
            for(i in 0..6){
                item {
                    FlagshipEventSingleRow(eventName = "Compose Camp")
                }
            }
            item {
                Spacer(modifier = Modifier.width(10.dp))
            }
        }
    // Best of month
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(top = 20.dp, start = 16.dp, end = 16.dp, bottom = 14.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = "Best of Month",
                fontSize = 24.sp,
            )
            Text(
                text = "See More",
                fontSize = 16.sp,
                color = Color.Blue,
                textDecoration = TextDecoration.Underline
            )
        }
        Card(
            shape = RoundedCornerShape(30.dp),
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth()
                .height(240.dp)
        )
        {
            Image(
                painter = painterResource(id = R.drawable.grid_bg),
                contentDescription = "Grid",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxSize()
            )
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Bottom,
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxSize()
            )
            {
                Column(horizontalAlignment = Alignment.CenterHorizontally)
                {
                    Image(
                        painter = painterResource(id = R.drawable.ic_launcher_background),
                        contentDescription = "Profile Second",
                        modifier = Modifier
                            .padding(top = 8.dp, bottom = 8.dp)
                            .size(64.dp)
                            .clip(CircleShape)
                    )
                    TopMonthRankGraph(foreground = LightBlue, points = "69", subtraction = 32)   
                }
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_launcher_background),
                        contentDescription = "Profile Second",
                        modifier = Modifier
                            .padding(top = 8.dp, bottom = 8.dp)
                            .size(64.dp)
                            .clip(CircleShape)
                    )
                    TopMonthRankGraph(foreground = Yellow, points = "69", subtraction = 0)
                }
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_launcher_background),
                        contentDescription = "Profile Second",
                        modifier = Modifier
                            .padding(top = 8.dp, bottom = 8.dp)
                            .size(64.dp)
                            .clip(CircleShape)
                    )
                    TopMonthRankGraph(foreground = LightRed, points = "69", subtraction = 50)
                }
            }
        }
        Spacer(modifier = Modifier.height(70.dp))
    }
}

@Preview
@Composable
fun HomeScreenPreview(){
    HomeScreen()
}