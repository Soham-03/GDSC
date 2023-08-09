package com.soham.gdsc.ui.component

import android.graphics.drawable.Icon
import androidx.compose.animation.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.soham.gdsc.R
import com.soham.gdsc.navigation.BottomBarScreen
import com.soham.gdsc.navigation.BottomNavigationGraph
import com.soham.gdsc.ui.theme.LightBlue
import com.soham.gdsc.ui.theme.Yellow

@Composable
fun BottomNavigation(){
    val navController = rememberNavController()
    Scaffold(
        bottomBar = { BottomBar(navController = navController) }
    ) {it.calculateBottomPadding()
        BottomNavigationGraph(navController = navController)
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun BottomBar(navController:NavController){
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    var selectedHome by remember { mutableStateOf(true) }
    var selectedEvents by remember { mutableStateOf(false) }
    var selectedLeaderBoard by remember { mutableStateOf(false) }
    var selectedBlogs by remember { mutableStateOf(false) }

    Box(){
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
                .height(60.dp)
                .clip(RoundedCornerShape(100.dp))
                .border(2.dp, Color.Black, RoundedCornerShape(100.dp))
                .background(Yellow),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ){

        }
    }
    Row(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
            .height(54.dp)
            .clip(RoundedCornerShape(100.dp))
            .border(2.dp, Color.Black, RoundedCornerShape(100.dp))
            .background(LightBlue),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ){
        //1
        Box(
            modifier = Modifier
                .background(LightBlue)
        ){
            androidx.compose.animation.AnimatedVisibility(
                visible = selectedHome,
                enter = scaleIn(),
                exit = ExitTransition.None,
            ) {
                Card(
                    shape = RoundedCornerShape(100.dp),
                    border = BorderStroke(2.dp,Color.Black),
                    backgroundColor = Color.White,
                    modifier = Modifier
                        .width(72.dp)
                        .height(37.dp),
                    elevation = 0.dp
                ){}
            }
            Card(
                modifier = Modifier
                    .width(
                        if (!selectedHome) {
                            32.dp
                        } else {
                            72.dp
                        }
                    )
                    .height(32.dp)
                    .animateContentSize(),
                shape = RoundedCornerShape(100.dp),
                border = if(selectedHome){BorderStroke(2.dp,Color.Black)} else {
                    null
                },
                backgroundColor = if(selectedHome){ Yellow } else{LightBlue},
                elevation = 0.dp
            ){
                Icon(painter = painterResource(id = R.drawable.ic_home),
                    contentDescription = "icon",
                    tint = Color.Black,
                    modifier = Modifier
                        .size(32.dp, 32.dp)
                        .clickable {
                            if (!selectedHome) {
                                selectedHome = true
                                selectedEvents = false
                                selectedBlogs = false
                                selectedLeaderBoard = false
                            }
                            navController.navigate(BottomBarScreen.Home.route) {
                                popUpTo(navController.graph.findStartDestination().id)
                                launchSingleTop = true
                            }
                        }
                        .padding(4.dp)
                )
            }
        }
        //2
        Box(
            modifier = Modifier
                .background(LightBlue)
        ){
            androidx.compose.animation.AnimatedVisibility(
                visible = selectedEvents,
                enter = scaleIn(),
                exit = ExitTransition.None,
            ) {
                Card(
                    shape = RoundedCornerShape(100.dp),
                    border = BorderStroke(2.dp,Color.Black),
                    backgroundColor = Color.White,
                    modifier = Modifier
                        .width(72.dp)
                        .height(37.dp),
                    elevation = 0.dp
                ){}
            }
            Card(
                shape = RoundedCornerShape(100.dp),
                border = if(selectedEvents){BorderStroke(2.dp,Color.Black)} else {
                    null
                },
                backgroundColor = if(selectedEvents){ Yellow } else{LightBlue},
                modifier = Modifier
                    .width(
                        if (!selectedEvents) {
                            32.dp
                        } else {
                            72.dp
                        }
                    )
                    .height(32.dp)
                    .animateContentSize(),
                elevation = 0.dp
            ){
                Icon(painter = painterResource(id = R.drawable.ic_events),
                    contentDescription = "icon",
                    tint = Color.Black,
                    modifier = Modifier
                        .size(32.dp, 32.dp)
                        .clickable {
                            if (!selectedEvents) {
                                selectedEvents = true
                                selectedHome = false
                                selectedBlogs = false
                                selectedLeaderBoard = false
                            }
                            navController.navigate(BottomBarScreen.Events.route) {
                                popUpTo(navController.graph.findStartDestination().id)
                                launchSingleTop = true
                            }
                        }
                        .padding(4.dp)
                )
            }
        }
        //3
        Box(
            modifier = Modifier
                .background(LightBlue)
        ){
            androidx.compose.animation.AnimatedVisibility(
                visible = selectedLeaderBoard,
                enter = scaleIn(),
                exit = ExitTransition.None,
            ) {
                Card(
                    shape = RoundedCornerShape(100.dp),
                    border = BorderStroke(2.dp,Color.Black),
                    backgroundColor = Color.White,
                    modifier = Modifier
                        .width(72.dp)
                        .height(37.dp),
                    elevation = 0.dp
                ){}
            }
            Card(
                shape = RoundedCornerShape(100.dp),
                border = if(selectedLeaderBoard){BorderStroke(2.dp,Color.Black)} else {
                    null
                },
                backgroundColor = if(selectedLeaderBoard){ Yellow } else{LightBlue},
                modifier = Modifier
                    .width(
                        if (!selectedLeaderBoard) {
                            32.dp
                        } else {
                            72.dp
                        }
                    )
                    .height(32.dp)
                    .animateContentSize(),
                elevation = 0.dp

            ){
                Icon(painter = painterResource(id = R.drawable.ic_leaderboard),
                    contentDescription = "icon",
                    tint = Color.Black,
                    modifier = Modifier
                        .size(32.dp, 32.dp)
                        .clickable {
                            if (!selectedLeaderBoard) {
                                selectedLeaderBoard = true
                                selectedEvents = false
                                selectedBlogs = false
                                selectedHome = false
                            }
                            navController.navigate(BottomBarScreen.Home.route) {
                                popUpTo(navController.graph.findStartDestination().id)
                                launchSingleTop = true
                            }
                        }
                        .padding(4.dp)
                )
            }
        }
        //4
        Box(
            modifier = Modifier
                .background(LightBlue)
        ){
            androidx.compose.animation.AnimatedVisibility(
                visible = selectedBlogs,
                enter = scaleIn(),
                exit = ExitTransition.None,
            ) {
                Card(
                    shape = RoundedCornerShape(100.dp),
                    border = BorderStroke(2.dp,Color.Black),
                    backgroundColor = Color.White,
                    modifier = Modifier
                        .width(72.dp)
                        .height(37.dp),
                    elevation = 0.dp
                ){}
            }
            Card(
                shape = RoundedCornerShape(100.dp),
                border = if(selectedBlogs){BorderStroke(2.dp,Color.Black)} else {
                    null
                },
                backgroundColor = if(selectedBlogs){ Yellow } else{LightBlue},
                modifier = Modifier
                    .width(
                        if (!selectedBlogs) {
                            32.dp
                        } else {
                            72.dp
                        }
                    )
                    .height(32.dp)
                    .animateContentSize(),
                elevation = 0.dp
            ){
                Icon(painter = painterResource(id = R.drawable.ic_blogs),
                    contentDescription = "icon",
                    tint = Color.Black,
                    modifier = Modifier
                        .size(32.dp, 32.dp)
                        .clickable {
                            if (!selectedBlogs) {
                                selectedBlogs = true
                                selectedEvents = false
                                selectedHome = false
                                selectedLeaderBoard = false
                            }
                            navController.navigate(BottomBarScreen.Home.route) {
                                popUpTo(navController.graph.findStartDestination().id)
                                launchSingleTop = true
                            }
                        }
                        .padding(4.dp)
                )
            }
        }

    }
}


@Preview
@Composable
fun BottomNavPreview(){
    BottomNavigation()
}