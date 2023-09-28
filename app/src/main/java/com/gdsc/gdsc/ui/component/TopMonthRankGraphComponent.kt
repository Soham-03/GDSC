package com.gdsc.gdsc.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gdsc.gdsc.ui.theme.Yellow
import com.gdsc.gdsc.ui.theme.greyBackgroundTopGraph

@Composable
fun TopMonthRankGraph(foreground: Color, points: String, subtraction: Int) {
    Box(
        modifier = Modifier
            .wrapContentSize()
    )
    {
        Box(
            Modifier
                .padding(start = 8.dp)
                .width(86.dp)
                .height((132 - subtraction).dp)
                .border(2.dp, Color.Black, RoundedCornerShape(20.dp))
                .background(greyBackgroundTopGraph, RoundedCornerShape(20.dp))
        )
        Box(
            Modifier
                .padding(top = 8.dp)
                .width(86.dp)
                .height((132 - subtraction).dp)
                .border(2.dp, Color.Black, RoundedCornerShape(20.dp))
                .background(foreground, RoundedCornerShape(20.dp))
        )
        {

        }
    }
}

@Preview
@Composable
fun TopMonthGraphPreview(){
    TopMonthRankGraph(foreground = Yellow, points = "69" , subtraction = 0)
}