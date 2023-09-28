package com.gdsc.gdsc.ui.component

import android.content.Intent
import android.net.Uri
import androidx.browser.customtabs.CustomTabsIntent
import androidx.compose.foundation.BorderStroke
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
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gdsc.gdsc.R
import com.gdsc.gdsc.model.Problem
import com.gdsc.gdsc.ui.theme.LightBlue
import com.gdsc.gdsc.ui.theme.LightRed
import com.gdsc.gdsc.ui.theme.Yellow
import com.gdsc.gdsc.ui.theme.cardBackgroundGreen

@Composable
fun ProblemStatement(problem: Problem?){
    Box(
        modifier = Modifier
            .padding(horizontal = 16.dp)
    ) {
        var height by remember{ mutableStateOf(0.dp) }
        val localDensity = LocalDensity.current
        Column(
            modifier = Modifier
                .padding(top = 10.dp)
                .border(
                    width = 2.dp,
                    color = Color.Black,
                    shape = RoundedCornerShape(size = 30.dp)
                )
                .fillMaxWidth()
                .height(height = height)
                .background(color = LightRed, shape = RoundedCornerShape(size = 30.dp))
        )
        {
        }
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .border(
                    width = 2.dp,
                    color = Color(0xFF000000),
                    shape = RoundedCornerShape(size = 30.dp)
                )
                .wrapContentSize()
                .background(color = Color.White, shape = RoundedCornerShape(size = 30.dp))
                .fillMaxWidth()
                .onGloballyPositioned { coordinates ->
                    height = with(localDensity) { coordinates.size.height.toDp() }
                }
        )
        {
            Text(
                text = "For the Week: ${problem!!.problemStartAndEnd}",
                fontSize = 16.sp,
                color = cardBackgroundGreen,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(horizontal = 12.dp, vertical = 10.dp)
            )
            Text(
                text = "Problem Statement",
                fontSize = 20.sp,
                color = LightRed,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(start = 8.dp, end = 8.dp, bottom = 8.dp)
            )
            Text(
                text = problem?.problemTitle ?: "Title",
                fontSize = 20.sp,
                color = Color.Black,
                modifier = Modifier
                    .padding(horizontal = 8.dp)
            )
            Text(
                text = "We know you're smart but, read this to know your problem more :)",
                fontSize = 18.sp,
                color = Yellow,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(horizontal = 16.dp)
            )
            Text(
                text = problem?.problemDesp ?: "Title",
                fontSize = 16.sp,
                color = Color.Black,
                modifier = Modifier
                    .padding(horizontal = 16.dp)
            )
            val context = LocalContext.current
            val uri = Uri.parse(problem.submissionLink)
            val intent = Intent(Intent.ACTION_VIEW, uri)
            val uriHandler = LocalUriHandler.current
            Card (
                backgroundColor = Color.White,
                shape = RoundedCornerShape(20.dp),
                border = BorderStroke(2.dp, LightBlue),
                elevation = 10.dp,
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .clickable {
                        CustomTabsIntent.Builder().build().launchUrl(context, Uri.parse(problem.submissionLink))
                    }
            )
            {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.CenterHorizontally)
                )
                {
                    Text(
                        text = "Submit Solution",
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .padding(10.dp)
                    )
                    Image(
                        painter = painterResource(id = R.drawable.gdsc_logo),
                        contentDescription = "gdsc logo",
                        modifier = Modifier
                            .size(60.dp)
                    )
                    
                }
            }
        }
    }
}

@Preview
@Composable
fun ProblemStatementPreview(){
//    ProblemStatement(problem = Problem(" Contributing to the Open Source","","", "", ))
}
