package com.soham.gdsc.ui.screen

import android.content.ClipData.Item
import android.content.Intent
import android.icu.text.CaseMap.Title
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.google.firebase.auth.FirebaseAuth
import com.soham.gdsc.Greeting
import com.soham.gdsc.ProfileActivity
import com.soham.gdsc.R
import com.soham.gdsc.firebaseDB.FirestoreRepo
import com.soham.gdsc.firebaseDB.FirestoreViewModel
import com.soham.gdsc.ui.component.FlagshipEventSingleRow
import com.soham.gdsc.ui.component.ProblemStatement
import com.soham.gdsc.ui.component.TopMonthRankGraph
import com.soham.gdsc.ui.theme.LightBlue
import com.soham.gdsc.ui.theme.LightRed
import com.soham.gdsc.ui.theme.Yellow
import com.soham.gdsc.ui.theme.textColorGrey
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomeScreen(viewModel: FirestoreViewModel){
    LaunchedEffect(Unit){
        viewModel.getBestOfMonth()
    }
    val coroutineScope = rememberCoroutineScope()
    val state by viewModel.state.collectAsState()
    val pullRefreshState = rememberPullRefreshState(refreshing = state.isProblemStatementRefreshing, onRefresh = {
        coroutineScope.launch{
            viewModel.getProblemStatementRefresh()
        }
    })
    Box(
        modifier = Modifier.pullRefresh(pullRefreshState)
    ){
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .verticalScroll(rememberScrollState())
        )
        {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
            )
            {
                Column()
                {
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier
                            .fillMaxWidth()
                    )
                    {
                        val context = LocalContext.current
                        Text(
                            text = "Welcome\nBack",
                            fontWeight = FontWeight.Bold,
                            color = Color.Black,
                            fontSize = 36.sp,
                            modifier = Modifier
                                .padding(top = 30.dp, start = 20.dp, end = 20.dp)
                        )
                        Image(
                            painter = painterResource(id = R.drawable.ic_user),
                            contentDescription = "profile icon",
                            modifier = Modifier
                                .padding(top = 30.dp, start = 20.dp, end = 20.dp)
                                .size(64.dp)
                                .clickable {
                                    val intent = Intent(context, ProfileActivity::class.java)
                                    context.startActivity(intent)
                                }
                        )
                    }
                    Text(
                        text = FirebaseAuth.getInstance().currentUser!!.displayName!!,
                        fontWeight = FontWeight.Bold,
                        color = textColorGrey,
                        fontSize = 20.sp,
                        modifier = Modifier
                            .padding(start = 20.dp, end = 20.dp)
                    )
                }
            }

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .padding(top = 20.dp, start = 16.dp, end = 16.dp, bottom = 14.dp)
                    .fillMaxWidth()
            ) {
                Text(
                    text = "Flagship Events",
                    fontSize = 24.sp,
                )
            }
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier
                    .padding(start = 16.dp, bottom = 16.dp)
            )
            {
                val flagshipList = state.isFlagShipEventSuccess
                for(flagshipEvent in flagshipList){
                    item {
                        FlagshipEventSingleRow(flagshipEvent.eventName, flagshipEvent.eventImage)
                    }
                }
                item {
                    Spacer(modifier = Modifier.width(10.dp))
                }
            }
            // Best of month
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .padding(top = 20.dp, start = 16.dp, end = 16.dp, bottom = 14.dp)
                    .fillMaxWidth()
            ) {
                Text(
                    text = "Best of Month",
                    fontSize = 24.sp,
                )
            }
            Card(
                shape = RoundedCornerShape(30.dp),
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth()
                    .height(240.dp)
            )
            {
                Image(
                    painter = painterResource(id = R.drawable.grid_bg),
                    contentDescription = "Grid",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxSize()
                )
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Bottom,
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxSize()
                )
                {
                    Column(horizontalAlignment = Alignment.CenterHorizontally)
                    {
                        AsyncImage(
                            model = if(state.isBestOfMonthSuccess.isEmpty()){""}else{state.isBestOfMonthSuccess.get(1)},
                            contentDescription = "Profile Second",
                            contentScale = ContentScale.Crop,
                            error = painterResource(id = R.drawable.ic_launcher_foreground),
                            modifier = Modifier
                                .padding(top = 8.dp, bottom = 8.dp)
                                .size(64.dp)
                                .clip(CircleShape)
                        )
                        TopMonthRankGraph(foreground = LightBlue, points = "69", subtraction = 32)
                    }
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        AsyncImage(
                            model = if(state.isBestOfMonthSuccess.isEmpty()){""}else{state.isBestOfMonthSuccess.get(0)},
                            contentDescription = "Profile Second",
                            contentScale = ContentScale.Crop,
                            error = painterResource(id = R.drawable.ic_launcher_foreground),
                            modifier = Modifier
                                .padding(top = 8.dp, bottom = 8.dp)
                                .size(64.dp)
                                .clip(CircleShape)
                        )
                        TopMonthRankGraph(foreground = Yellow, points = "69", subtraction = 0)
                    }
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        AsyncImage(
                            model = if(state.isBestOfMonthSuccess.isEmpty()){""}else{state.isBestOfMonthSuccess.get(2)},
                            contentDescription = "Profile Second",
                            contentScale = ContentScale.Crop,
                            error = painterResource(id = R.drawable.ic_launcher_foreground),
                            modifier = Modifier
                                .padding(top = 8.dp, bottom = 8.dp)
                                .size(64.dp)
                                .clip(CircleShape)
                        )
                        TopMonthRankGraph(foreground = LightRed, points = "69", subtraction = 50)
                    }
                }

            }
            Text(
                text = "Problem of the week",
                fontSize = 24.sp,
                color = textColorGrey,
                modifier = Modifier
                    .padding(top = 20.dp, start = 16.dp, end = 16.dp, bottom = 14.dp)
            )
//        Card(
//            backgroundColor = Color.White,
//            border = BorderStroke(2.dp, LightRed),
//            modifier = Modifier
//                .padding(horizontal = 16.dp, vertical = 8.dp)
//                .fillMaxWidth()
//        )
//        {
//
//        }
            ProblemStatement(problem = state.isProblemStatementSuccess)
            Spacer(modifier = Modifier.height(100.dp))
        }
        PullRefreshIndicator(
            refreshing = state.isProblemStatementRefreshing,
            state = pullRefreshState,
            modifier = Modifier.align(Alignment.TopCenter),
            backgroundColor = Color.White,
            contentColor = Color.Black
        )
    }

}

@Preview
@Composable
fun HomeScreenPreview(){
    HomeScreen(FirestoreViewModel(FirestoreRepo(LocalContext.current)))
}