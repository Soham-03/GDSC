package com.soham.gdsc.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.soham.gdsc.ui.theme.textColorGrey

@Composable
fun BlogsScreen(){
    Column() {
        Text(
            text = "The GDSC Blogs",
            color = textColorGrey,
            fontSize = 24.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(top = 12.dp)
                .fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(100.dp))
        Text(
            text = "Coming Soon...",
            color = textColorGrey,
            fontSize = 32.sp,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
        )
    }

}

@Preview
@Composable
fun BlogsScreenPreview(){

}