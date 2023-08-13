package com.soham.gdsc.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.soham.gdsc.ui.screen.*

@Composable
fun BottomNavigationGraph(
    navController: NavHostController
){
    NavHost(navController = navController, startDestination = BottomBarScreen.Home.route){
        composable(route = BottomBarScreen.Home.route){
            HomeScreen()
        }
        composable(route = BottomBarScreen.Events.route){
            EventsScreen(navController)
        }
        composable(route = BottomBarScreen.Leaderboard.route){
            LeaderBoardScreen()
        }
        composable(route = BottomBarScreen.Blogs.route){
            BlogsScreen()
        }
        composable(route = "EventInfo"){
            EventInfoScreen(eventName = "", eventDate = "", eventTime = "")
        }
    }
}
