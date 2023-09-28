package com.gdsc.gdsc.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun NavGraphBuilder.EventsNavGraph(
    navHostController: NavHostController
){
    NavHost(navController = navHostController, startDestination = "Events"){
        composable("Events"){
//            EventInfoScreen(eventName = "GGWp", eventDate = "GGS", eventTime = "GGs")
        }
    }
}