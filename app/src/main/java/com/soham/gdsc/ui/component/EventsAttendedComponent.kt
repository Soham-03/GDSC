package com.soham.gdsc.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.soham.gdsc.ui.theme.LightBlue
import com.soham.gdsc.ui.theme.LightRed
import com.soham.gdsc.ui.theme.Yellow
import com.soham.gdsc.ui.theme.textColorGrey

@Composable
fun EventsAttendedSingleRow(){
    Box(
        modifier = Modifier
            .fillMaxWidth()
    )
    {
        Column(
            modifier = Modifier
                .padding(top = 10.dp)
                .border(
                    width = 2.dp,
                    color = Color(0xFF000000),
                    shape = RoundedCornerShape(size = 30.dp)
                )
                .fillMaxWidth()
                .height(150.dp)
                .background(color = LightBlue, shape = RoundedCornerShape(size = 30.dp))
        ){

        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .border(
                    width = 2.dp,
                    color = Color(0xFF000000),
                    shape = RoundedCornerShape(size = 30.dp)
                )
                .fillMaxWidth()
                .height(150.dp)
                .background(color = Color.White, shape = RoundedCornerShape(size = 30.dp))
        ) {
            Image(
                painter = painterResource(id = com.soham.gdsc.R.drawable.ic_launcher_background),
                contentDescription = "Event Image",
                modifier = Modifier
                    .padding(8.dp)
                    .aspectRatio(1f)
                    .clip(RoundedCornerShape(20.dp))
            )
            Column(
                verticalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .padding(vertical = 20.dp)
                    .height(150.dp)
            )
            {
                Text(
                    text = "Event Name",
                    color = textColorGrey,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    text = "100 Points",
                    color = LightRed,
                    fontSize = 24.sp
                )
            }
        }
    }
}

@Preview
@Composable
fun EventsAttendedPreview(){
    EventsAttendedSingleRow()
}