package com.soham.gdsc.ui.screen

import android.content.Intent
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.soham.gdsc.MainActivity
import com.soham.gdsc.R
import com.soham.gdsc.ui.theme.textColorGrey

@Composable
fun ProfileScreen(uid: String){
    val context = LocalContext.current
    Column() {
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
                        val intent = Intent(context,MainActivity::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
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
    }
}

@Preview
@Composable
fun ProfileScreenPreview(){
    ProfileScreen(uid = "")
}