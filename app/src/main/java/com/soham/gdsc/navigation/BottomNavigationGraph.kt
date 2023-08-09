package com.soham.gdsc.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.soham.gdsc.ui.screen.EventsScreen
import com.soham.gdsc.ui.screen.HomeScreen

@Composable
fun BottomNavigationGraph(
    navController: NavHostController
){
    NavHost(navController = navController, startDestination = BottomBarScreen.Home.route){
        composable(route = BottomBarScreen.Home.route){
            HomeScreen()
        }
        composable(route = BottomBarScreen.Events.route){
            EventsScreen()
        }
        composable(route = BottomBarScreen.AddPost.route){
            HomeScreen()
        }
        composable(route = BottomBarScreen.Profile.route){
            EventsScreen()
        }
    }
}
