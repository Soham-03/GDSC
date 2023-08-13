package com.soham.gdsc.ui.component

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController
import com.soham.gdsc.ui.theme.ProductSans
import com.soham.gdsc.R
import com.soham.gdsc.navigation.BottomBarScreen
import com.soham.gdsc.ui.theme.cardBackgroundGreen
import java.net.URL

@Composable
fun EventSingleRow(
    backgroundColor: Color,
    eventImage: String,
    eventName: String,
    eventDate: String,
    eventTime: String,
    onEventClick:() ->Unit
)
{
    var height by remember{ mutableStateOf(0.dp) }
    val localDensity = LocalDensity.current
    Box(
        modifier = Modifier
            .clickable {
                onEventClick.invoke()
            }
    )
    {
        Column(
            modifier = Modifier
                .padding(top = 14.dp)
                .border(
                    width = 2.dp,
                    color = Color(0xFF000000),
                    shape = RoundedCornerShape(size = 30.dp)
                )
                .fillMaxWidth()
                .height(height = height)
                .background(color = backgroundColor, shape = RoundedCornerShape(size = 30.dp))
        )
        {
        }
        Column(
            modifier = Modifier
                .border(
                    width = 2.dp,
                    color = Color(0xFF000000),
                    shape = RoundedCornerShape(size = 30.dp)
                )
                .fillMaxWidth()
                .wrapContentSize()
                .background(color = Color.White, shape = RoundedCornerShape(size = 30.dp))
                .onGloballyPositioned { coordinates ->
                    height = with(localDensity) { coordinates.size.height.toDp() }}
        )
        {
            Image(
                painter = painterResource(id = R.drawable.ic_launcher_background),
                contentDescription = "Event Image Poster",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .padding(top = 12.dp, start = 12.dp, end = 12.dp)
                    .fillMaxWidth()
                    .aspectRatio(1f)
                    .clip(RoundedCornerShape(30.dp))
            )
            Text(
                text = eventName,
                color = Color.Black,
                fontSize = 20.sp,
                fontFamily = ProductSans,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            )
            Row(
                modifier = Modifier
                    .padding(bottom = 26.dp)
            )
            {
                Card(
                    shape = RoundedCornerShape(10.dp),
                    backgroundColor = Color.White,
                    border = BorderStroke(1.dp, Color.Black),
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .wrapContentSize()
                )
                {
                    Row(){
                        Image(
                            painter = painterResource(id = R.drawable.ic_calendar),
                            contentDescription = "Date Image",
                            modifier = Modifier
                                .padding(6.dp)
                                .size(20.dp)
                        )
                        Text(
                            text = eventDate,
                            style = TextStyle(
                                fontSize = 12.sp,
                                fontFamily = ProductSans,
                                fontWeight = FontWeight(700),
                                color = Color(0xFF000000),
                            ),
                            modifier = Modifier
                                .padding(end = 6.dp)
                                .align(CenterVertically)
                        )
                    }
                }
                Card(
                    shape = RoundedCornerShape(10.dp),
                    backgroundColor = Color.White,
                    border = BorderStroke(1.dp, Color.Black),
                    modifier = Modifier
                        .padding(end = 16.dp)
                        .wrapContentSize()
                )
                {
                    Row(){
                        Image(
                            painter = painterResource(id = R.drawable.ic_clock),
                            contentDescription = "Time Image",
                            modifier = Modifier
                                .padding(6.dp)
                                .size(20.dp)
                        )
                        Text(
                            text = eventTime,
                            style = TextStyle(
                                fontSize = 12.sp,
                                fontFamily = ProductSans,
                                fontWeight = FontWeight(700),
                                color = Color(0xFF000000),
                            ),
                            modifier = Modifier
                                .padding(end = 6.dp)
                                .align(CenterVertically)
                        )
                    }
                }
            }
            
        }

    }
}

@Preview
@Composable
fun EventSingleRowPreview(){
//    EventSingleRow(cardBackgroundGreen, "", "Compose Camp", "28-30 Nov", "10 am - 12-pm")
}