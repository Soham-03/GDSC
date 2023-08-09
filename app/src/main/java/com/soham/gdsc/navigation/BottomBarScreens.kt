package com.soham.gdsc.navigation

import com.soham.gdsc.R

sealed class BottomBarScreen(
    val route: String,
    val title: String,
    val icon: Int
){
    object Home: BottomBarScreen(
        route = "home",
        title = "home",
        icon = R.drawable.ic_home
    )

    object Events: BottomBarScreen(
        route = "events",
        title = "events",
        icon = R.drawable.ic_events
    )

    object AddPost: BottomBarScreen(
        route = "add post",
        title = "add post",
        icon = R.drawable.ic_home
    )

    object Profile: BottomBarScreen(
        route = "profile",
        title = "profile",
        icon = R.drawable.ic_home
    )
}