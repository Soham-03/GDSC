package com.gdsc.gdsc.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.gdsc.gdsc.ui.theme.textColorGrey

@Composable
fun LeaderboardComponent(
    userName: String,
    userImage: String,
    tags: String,
    userClass: String,
    backgroundColor: Color,
    rank: String
){
    Box(
        modifier = Modifier
            .padding(vertical = 10.dp)
    )
    {
        Box(
            modifier = Modifier
                .padding(top = 8.dp)
                .fillMaxWidth()
                .height(72.dp)
                .border(2.dp, Color.Black, RoundedCornerShape(30.dp))
                .background(backgroundColor, RoundedCornerShape(30.dp))
        )
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxWidth()
                .height(72.dp)
                .background(Color.White, RoundedCornerShape(30.dp))
                .border(2.dp, Color.Black, RoundedCornerShape(30.dp))
        )
        {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .padding(12.dp)
                    .fillMaxWidth()
            )
            {
                AsyncImage(
                    model = userImage,
                    contentDescription = "profile image",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .padding(end = 8.dp)
                        .size(50.dp)
                        .clip(CircleShape)
                        .weight(1f)
                )
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                    .padding(start = 8.dp, end = 8.dp)
                    .weight(2f)
                ){
                    Text(
                        text = userName,
                        fontSize = 20.sp,
                        color = Color.Black,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )
                    Text(
                        text = userClass,
                        fontSize = 14.sp,
                        color = Color.Black,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )
                }
                
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .weight(1f)
                )
                {
                    Text(
                        text = rank,
                        fontSize = 20.sp,
                        color = Color.Black
                    )
                    Text(
                        text = "Tags: $tags",
                        fontSize = 16.sp,
                        color = textColorGrey
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun LeaderboardCompPreview(){
    LeaderboardComponent(userName = "adadasdasdasdasdasdasdazdasdasdawdadawdasd", userImage = "", tags = "169", "", textColorGrey,"1")
}