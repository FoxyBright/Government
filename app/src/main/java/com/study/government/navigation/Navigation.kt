package com.study.government.navigation

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.runtime.Composable
import androidx.navigation.*
import androidx.navigation.NavType.Companion.StringType
import androidx.navigation.compose.composable
import com.study.government.GovernmentApp

object Navigation {
    fun NavGraphBuilder.screen(
        dest: Destination,
        content: @Composable AnimatedContentScope.(NavBackStackEntry) -> Unit,
    ) = composable(route = dest.route, content = content)
    
    fun NavController.navigateTo(
        dest: Destination,
        dropScreens: Boolean = false,
        arg: NavArgument? = null,
    ) {
        if (currentDestination?.route == dest.route) return
        GovernmentApp.curScreen = dest
        navigate(arg?.setInPath(dest.route) ?: dest.route) {
            if (dropScreens) popUpTo(graph.id)
        }
    }
    
    fun NavGraphBuilder.argScreen(
        dest: Destination,
        arg: DestinationArg,
        content: @Composable (String) -> Unit,
    ) = composable(
        arguments = listOf(navArgument(arg.arg) { type = StringType }),
        route = dest.route
    ) { stack ->
        stack.arguments?.getString(arg.arg)?.let { content(it) }
    }
}