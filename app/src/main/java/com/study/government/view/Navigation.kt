package com.study.government.view

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.study.government.model.Destination
import com.study.government.model.Destination.ADD_NEW
import com.study.government.model.Destination.ADD_REQUEST
import com.study.government.model.Destination.LOGIN
import com.study.government.model.Destination.NEWS
import com.study.government.model.Destination.NEW_INFO
import com.study.government.model.Destination.PROFILE
import com.study.government.model.Destination.REQUESTS
import com.study.government.model.Destination.REQUEST_INFO
import com.study.government.model.Destination.SERVANTS
import com.study.government.model.DestinationArg.NEW_INFO_ARG
import com.study.government.model.DestinationArg.REQUEST_INFO_ARG
import com.study.government.tools.Navigation.argScreen
import com.study.government.tools.Navigation.screen
import com.study.government.view.login.LoginScreen
import com.study.government.view.news.AddNewScreen
import com.study.government.view.news.NewInfoScreen
import com.study.government.view.news.NewsScreen
import com.study.government.view.profile.ProfileScreen
import com.study.government.view.requests.AddRequestScreen
import com.study.government.view.requests.RequestInfoScreen
import com.study.government.view.requests.RequestsScreen
import com.study.government.view.servants.ServantsScreen

@Composable
fun Navigation(
    navHostController: NavHostController,
    startDestination: Destination,
    modifier: Modifier = Modifier,
) {
    NavHost(
        startDestination = startDestination.route,
        navController = navHostController,
        modifier = modifier.fillMaxSize()
    ) {
        screen(LOGIN) { LoginScreen(navHostController) }
        screen(NEWS) { NewsScreen(navHostController) }
        argScreen(NEW_INFO, NEW_INFO_ARG) { NewInfoScreen(navHostController, it) }
        argScreen(REQUEST_INFO, REQUEST_INFO_ARG) { RequestInfoScreen(navHostController, it) }
        screen(REQUESTS) { RequestsScreen(navHostController) }
        screen(PROFILE) { ProfileScreen(navHostController) }
        screen(SERVANTS) { ServantsScreen(navHostController) }
        argScreen(ADD_REQUEST, REQUEST_INFO_ARG) { AddRequestScreen(navHostController, it) }
        argScreen(ADD_NEW, NEW_INFO_ARG) { AddNewScreen(navHostController, it) }
    }
}