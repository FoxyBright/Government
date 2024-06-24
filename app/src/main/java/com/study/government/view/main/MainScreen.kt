package com.study.government.view.main

import android.app.Activity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.study.government.GovernmentApp.Companion.curScreen
import com.study.government.navigation.Destination.NEWS
import com.study.government.navigation.Destination.NEW_INFO
import com.study.government.navigation.Destination.APPEALS
import com.study.government.navigation.Destination.SERVANTS
import com.study.government.navigation.Destination.SERVANTS_INFO
import com.study.government.navigation.DestinationArg.NEW_INFO_ARG
import com.study.government.navigation.DestinationArg.SERVANT_INFO_ARG
import com.study.government.navigation.Navigation.argScreen
import com.study.government.navigation.Navigation.screen
import com.study.government.tools.Background
import com.study.government.tools.PrimaryColor
import com.study.government.view.news.NewInfoScreen
import com.study.government.view.news.NewsScreen
import com.study.government.view.appeals.AppealsScreen
import com.study.government.view.servants.ServantInfoScreen
import com.study.government.view.servants.ServantsScreen

@Composable
fun MainScreen() {
    val navHostController = rememberNavController()
    val view = LocalView.current

    if (!view.isInEditMode) SideEffect {
        val window = (view.context as Activity).window
        window.statusBarColor = PrimaryColor.toArgb()
        WindowCompat.getInsetsController(window, view)
            .isAppearanceLightStatusBars = false
    }

    MaterialTheme(
        colorScheme = lightColorScheme(
            background = Background,
            primary = PrimaryColor
        )
    ) {
        Scaffold(
            modifier = Modifier
                .fillMaxSize()
                .background(Background),
            bottomBar = {
                if (curScreen.showBottomBar) {
                    MainBottomBar(navHostController)
                }
            }
        ) { paddings ->
            NavHost(
                navController = navHostController,
                startDestination = NEWS.route,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddings)
            ) {
                argScreen(SERVANTS_INFO, SERVANT_INFO_ARG) {
                    ServantInfoScreen(navHostController, it)
                }

                argScreen(NEW_INFO, NEW_INFO_ARG) {
                    NewInfoScreen(navHostController, it)
                }

                screen(SERVANTS) {
                    ServantsScreen(navHostController)
                }

                screen(NEWS) {
                    NewsScreen(navHostController)
                }

                screen(APPEALS) {
                    AppealsScreen()
                }
            }
        }
    }
}