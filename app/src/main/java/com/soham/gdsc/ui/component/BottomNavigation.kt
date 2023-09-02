package com.soham.gdsc.ui.component

import androidx.compose.animation.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.soham.gdsc.firebaseDB.FirestoreViewModel
import com.soham.gdsc.navigation.BottomBarScreen
import com.soham.gdsc.navigation.BottomNavigationGraph
import com.soham.gdsc.ui.theme.LightBlue
import com.soham.gdsc.ui.theme.Yellow
import kotlinx.coroutines.delay

@Composable
fun BottomNavigation(viewModel: FirestoreViewModel){
    val navController = rememberNavController()
    val state by viewModel.state.collectAsState()
    var show by remember {
        mutableStateOf(true)
    }
    LaunchedEffect(Unit){
        delay(3000)
        if(!state.isFlagShipEventLoading){
            show = false
        }
    }
    Scaffold(
        bottomBar = {
            AnimatedVisibility(visible = !show) {
                BottomBar(navController = navController)
            }

        }
    ) {it.calculateBottomPadding()
        BottomNavigationGraph(navController = navController, viewModel)
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun BottomBar(navController:NavHostController){
    val screens = listOf(
        BottomBarScreen.Home,
        BottomBarScreen.Events,
        BottomBarScreen.Leaderboard,
        BottomBarScreen.Blogs
    )
    val navStackBackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navStackBackEntry?.destination

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
        screens.forEach { screen->
            if(currentDestination!=null){
                AddItem(screen = screen, currentDestination =currentDestination , navController = navController)
            }
        }
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun RowScope.AddItem(
    screen: BottomBarScreen,
    currentDestination:NavDestination,
    navController: NavHostController
){
    val selected = currentDestination.hierarchy.any{ it.route == screen.route }

    Box(
        modifier = Modifier
            .background(LightBlue)
    ){
        androidx.compose.animation.AnimatedVisibility(
            visible = selected,
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
                    if (!selected) {
                        32.dp
                    } else {
                        72.dp
                    }
                )
                .height(32.dp)
                .animateContentSize(),
            shape = RoundedCornerShape(100.dp),
            border = if(selected){BorderStroke(2.dp,Color.Black)} else {
                null
            },
            backgroundColor = if(selected){ Yellow } else{LightBlue},
            elevation = 0.dp
        ){
            Icon(painter = painterResource(id = screen.icon),
                contentDescription = "icon",
                tint = Color.Black,
                modifier = Modifier
                    .size(32.dp, 32.dp)
                    .clickable {
                        navController.navigate(screen.route) {
                            popUpTo(navController.graph.findStartDestination().id)
                            launchSingleTop = true
                        }
                    }
                    .padding(4.dp)
            )
        }
    }

}


@Preview
@Composable
fun BottomNavPreview(){
//    BottomNavigation()
}