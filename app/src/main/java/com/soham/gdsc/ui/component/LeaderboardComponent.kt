package com.soham.gdsc.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.soham.gdsc.R
import com.soham.gdsc.ui.theme.Yellow
import com.soham.gdsc.ui.theme.textColorGrey
import kotlinx.coroutines.launch

@Composable
fun LeaderboardComponent(
    userName: String,
    userImage: String,
    tags: String,
    backgroundColor: Color,
    rank: String
){
    Box()
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
                        .padding(end = 12.dp)
                        .size(50.dp)
                        .clip(CircleShape)
                )
                Text(
                    text = userName,
                    fontSize = 20.sp,
                    color = Color.Black,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier
                        .padding(start = 10.dp, end = 10.dp)
                        .widthIn(max = 160.dp)
                )
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .padding(start = 12.dp)
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
    LeaderboardComponent(userName = "adadasdasdasdasdasdasdazdasdasdawdadawdasd", userImage = "", tags = "69", textColorGrey,"1")
}