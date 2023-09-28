package com.gdsc.gdsc.ui.screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.gdsc.gdsc.firebaseDB.FirestoreViewModel
import com.gdsc.gdsc.ui.component.LeaderboardComponent
import com.gdsc.gdsc.ui.theme.LightBlue
import com.gdsc.gdsc.ui.theme.LightRed
import com.gdsc.gdsc.ui.theme.Yellow
import com.gdsc.gdsc.ui.theme.greyBackgroundTopGraph
import com.gdsc.gdsc.ui.theme.textColorGrey
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun LeaderBoardScreen(viewModel: FirestoreViewModel){
    LaunchedEffect(true){
        viewModel.getLeaderboardTop10()
    }
    val state by viewModel.state.collectAsState()
    val coroutineScope = rememberCoroutineScope()
    val pullRefreshState = rememberPullRefreshState(refreshing = state.isLeaderBoardRefreshing, onRefresh = {
        coroutineScope.launch{
            viewModel.getLeaderboardTop10Refresh()
        }
    })
    Box(
        modifier = Modifier
            .pullRefresh(pullRefreshState)
    ){
        Column() {
            val list = state.isLeaderBoardDataSuccess
            Text(
                text = "Leaderboard",
                color = textColorGrey,
                fontSize = 24.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(top = 12.dp)
                    .fillMaxWidth()
            )
            AnimatedVisibility(visible = list.isEmpty()) {
                Text(
                    text = "Can't see anyone here :(",
                    fontSize = 24.sp,
                    color = textColorGrey,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                )
            }
            LazyColumn(
                modifier = Modifier
                    .padding(top = 16.dp, start = 16.dp, end = 16.dp)
            )
            {
                for (i in 0..list.lastIndex){
                    val backgroundColor = when (i) {
                        0 -> {
                            Yellow
                        }
                        1 -> {
                            LightBlue
                        }
                        2 -> {
                            LightRed
                        }
                        else -> {
                            greyBackgroundTopGraph
                        }
                    }
                    item {
                        LeaderboardComponent(
                            userName = list[i].userName,
                            userImage = list[i].userImage,
                            tags = list[i].tags,
                            backgroundColor = backgroundColor,
                            userClass = list[i].userClass,
                            rank = (i+1).toString()
                        )
                    }
                }
                item {
                    Spacer(modifier = Modifier.height(120.dp))
                }
            }
        }
        PullRefreshIndicator(
            refreshing = state.isLeaderBoardRefreshing,
            state = pullRefreshState,
            modifier = Modifier.align(Alignment.TopCenter),
            backgroundColor = Color.White,
            contentColor = Color.Black
        )
    }

}

@Preview
@Composable
fun LeaderBoardScrnPreview(){
    LeaderBoardScreen(viewModel = viewModel())
}