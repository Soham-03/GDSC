package com.gdsc.gdsc.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.gdsc.gdsc.ui.theme.ProductSans
import com.gdsc.gdsc.ui.theme.Yellow

@Composable
fun FlagshipEventSingleRow(eventName: String, eventImage: String){
    Box() {
        var height by remember{ mutableStateOf(0.dp) }
        var width by remember{ mutableStateOf(0.dp) }
        val localDensity = LocalDensity.current
        Column(
            modifier = Modifier
                .padding(top = 10.dp, start = 10.dp)
                .border(
                    width = 2.dp,
                    color = Color(0xFF000000),
                    shape = RoundedCornerShape(size = 30.dp)
                )
                .width(width = width)
                .height(height = height)
                .background(color = Yellow, shape = RoundedCornerShape(size = 30.dp))
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
                .wrapContentSize()
                .background(color = Color.White, shape = RoundedCornerShape(size = 30.dp))
                .onGloballyPositioned { coordinates ->
                    height = with(localDensity) { coordinates.size.height.toDp() }
                    width = with(localDensity) { coordinates.size.width.toDp() }
                }
        )
        {
            AsyncImage(
                model = eventImage,
                contentDescription = "Event Image Poster",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .padding(top = 12.dp, start = 12.dp, end = 12.dp)
                    .width(240.dp)
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
        }
    }
}

@Preview
@Composable
fun FlagshipEventSingleRowPreview(){
    FlagshipEventSingleRow(eventName = "Bit N Build", eventImage = "")
}