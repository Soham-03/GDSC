package com.soham.gdsc.ui.screen

import android.content.Intent
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.soham.gdsc.MainActivity
import com.soham.gdsc.R
import com.soham.gdsc.ui.theme.LightBlue
import com.soham.gdsc.ui.theme.LightRed
import com.soham.gdsc.ui.theme.Yellow
import com.soham.gdsc.ui.theme.textColorGrey

@Composable
fun ProfileScreen(uid: String){
    val context = LocalContext.current
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    )
    {
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
            Text(
                text = "Your Profile",
                color = textColorGrey,
                fontSize = 24.sp,
                textAlign = TextAlign.Center
            )
            Image(
                painter = painterResource(id = R.drawable.ic_question),
                contentDescription = "question ic",
                modifier = Modifier
                    .size(30.dp)
            )
        }
        Image(
            painter = painterResource(id = R.drawable.ic_launcher_background),
            contentDescription = "profile image",
            modifier = Modifier
                .size(220.dp)
                .clip(CircleShape)
                .align(Alignment.CenterHorizontally)
        )
        Text(
            text = "User's Name",
            color = textColorGrey,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(8.dp)
        )
        Text(
            text = "Fr. Conceicao Rodrigues College of Engineering",
            color = LightRed,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = "abc@xyz.com",
            color = LightBlue,
            fontSize = 14.sp
        )

        //points card
        Box(
            modifier = Modifier
                .padding(16.dp)
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
                    .background(color = Yellow, shape = RoundedCornerShape(size = 30.dp))
            )
            {
            }
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .border(
                        width = 2.dp,
                        color = Color(0xFF000000),
                        shape = RoundedCornerShape(size = 30.dp)
                    )
                    .fillMaxWidth()
                    .height(150.dp)
                    .background(color = Color.White, shape = RoundedCornerShape(size = 30.dp))
            )
            {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                )
                {
                    Column(
                        verticalArrangement = Arrangement.SpaceBetween,
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .padding(start = 12.dp)
                            .wrapContentHeight()
                    )
                    {
                        Text(
                            text = "Your Points",
                            color = Yellow,
                            fontSize = 32.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "696",
                            color = textColorGrey,
                            fontSize = 32.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    Image(
                        painter = painterResource(id = R.drawable.ic_star),
                        contentDescription = "star image",
                        alpha = 0.8f,
                        modifier = Modifier
                            .size(120.dp)
                            .rotate(-45f)
                    )
                }
            }
        }

    }
}

@Preview
@Composable
fun ProfileScreenPreview(){
    ProfileScreen(uid = "")
}