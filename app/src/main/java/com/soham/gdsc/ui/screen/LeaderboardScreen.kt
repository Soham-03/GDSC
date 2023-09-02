package com.soham.gdsc.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.firebase.auth.FirebaseAuth
import com.soham.gdsc.firebaseDB.FirestoreViewModel
import com.soham.gdsc.ui.component.LeaderboardComponent
import com.soham.gdsc.ui.theme.LightBlue
import com.soham.gdsc.ui.theme.LightRed
import com.soham.gdsc.ui.theme.Yellow
import com.soham.gdsc.ui.theme.greyBackgroundTopGraph
import com.soham.gdsc.ui.theme.textColorGrey

@Composable
fun LeaderBoardScreen(viewModel: FirestoreViewModel){
    LaunchedEffect(true){
        viewModel.getLeaderboardTop10()
    }
    val state by viewModel.state.collectAsState()
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
//                    1 -> {
//                        LightBlue
//                    }
//                    2 -> {
//                        LightRed
//                    }
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
        }
    }

}

@Preview
@Composable
fun LeaderBoardScrnPreview(){
    LeaderBoardScreen(viewModel = viewModel())
}