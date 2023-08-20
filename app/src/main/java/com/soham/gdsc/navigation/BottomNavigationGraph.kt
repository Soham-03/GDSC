package com.soham.gdsc.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.google.firebase.auth.FirebaseAuth
import com.soham.gdsc.firebaseDB.FirestoreViewModel
import com.soham.gdsc.ui.screen.*

@Composable
fun BottomNavigationGraph(
    navController: NavHostController,
    viewModel: FirestoreViewModel
){
    LaunchedEffect(Unit){
        viewModel.getAllEvents()
        viewModel.getBestOfMonth()
//        viewModel.getLeaderboardTop10()
        viewModel.getFlagshipEvents()
    }
    NavHost(navController = navController, startDestination = BottomBarScreen.Home.route){
        composable(route = BottomBarScreen.Home.route){
            HomeScreen(viewModel = viewModel)
        }
        composable(route = BottomBarScreen.Events.route){
            EventsScreen(viewModel)
        }
        composable(route = BottomBarScreen.Leaderboard.route){
            LeaderBoardScreen(viewModel = viewModel)
        }
        composable(route = BottomBarScreen.Blogs.route){
            BlogsScreen()
        }
//        composable(route = "EventInfo"){
//            EventInfoScreen(eventName = "", eventDate = "", eventTime = "","","",)
//        }
    }
}
