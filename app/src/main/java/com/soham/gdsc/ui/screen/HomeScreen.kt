package com.soham.gdsc.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.soham.gdsc.Greeting
import com.soham.gdsc.R
import com.soham.gdsc.ui.theme.textColorGrey

@Composable
fun HomeScreen(){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    )
    {
        Image(painter = painterResource(id = R.drawable.ic_gdsc_logo_transparent), contentDescription = "GDSC logo home page")
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
}

@Preview
@Composable
fun HomeScreenPreview(){
    HomeScreen()
}