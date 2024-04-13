package com.study.government.view.main

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.material3.CardDefaults.cardColors
import androidx.compose.material3.CardDefaults.cardElevation
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.LightGray
import androidx.compose.ui.graphics.Color.Companion.Transparent
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight.Companion.Medium
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.study.government.GovernmentApp.Companion.curScreen
import com.study.government.GovernmentApp.Companion.startDest
import com.study.government.R
import com.study.government.model.Destination
import com.study.government.model.Destination.*
import com.study.government.tools.Navigation.navigateTo
import com.study.government.tools.getViewModel
import com.study.government.ui.theme.Background
import com.study.government.ui.theme.PrimaryColor
import com.study.government.view.Navigation
import com.study.government.viewmodel.MainViewModel

@Composable
fun MainScreen(navHostController: NavHostController) {
    val mainVm = getViewModel<MainViewModel>()

    LaunchedEffect(Unit) {
        mainVm.user ?: mainVm.updateUser()
    }

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
        Navigation(
            navHostController = navHostController,
            startDestination = startDest,
            modifier = Modifier
                .fillMaxSize()
                .padding(paddings)
        )
    }
}

@Suppress("unused")
private enum class BottomButtons(
    @DrawableRes val icon: Int = 0,
    @StringRes val label: Int = 0,
    val dest: Destination,
) {

    NEWS_BT(
        icon = R.drawable.ic_news,
        label = R.string.news,
        dest = NEWS
    ),
    REQUESTS_BT(
        icon = R.drawable.ic_file,
        label = R.string.requests,
        dest = REQUESTS
    ),
    SERVANTS_BT(
        icon = R.drawable.ic_people,
        label = R.string.servants,
        dest = SERVANTS
    ),
    PROFILE_BT(
        icon = R.drawable.ic_profile,
        label = R.string.profile,
        dest = PROFILE
    )
}

@Composable
private fun MainBottomBar(navHostController: NavHostController) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(PrimaryColor),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        BottomButtons.entries.forEach { button ->
            val isSelected by remember(curScreen, button) {
                mutableStateOf(curScreen == button.dest)
            }
            Card(
                onClick = { navHostController.navigateTo(button.dest) },
                colors = cardColors(Transparent),
                elevation = cardElevation(0.dp),
                shape = RectangleShape,
                modifier = Modifier
                    .height(56.dp)
                    .weight(1f)
            ) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    MainBottomBarItem(
                        isSelected = isSelected,
                        text = button.label,
                        icon = button.icon,
                    )
                }
            }
        }
    }
}

@Composable
private fun MainBottomBarItem(
    @DrawableRes icon: Int,
    @StringRes text: Int,
    isSelected: Boolean,
    modifier: Modifier = Modifier,
) {
    val color by animateColorAsState(
        targetValue = if (isSelected) White else LightGray,
        animationSpec = tween(200), label = ""
    )
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            painter = painterResource(icon),
            contentDescription = null,
            modifier = Modifier
                .padding(horizontal = 8.dp)
                .padding(top = 5.dp)
                .size(26.dp)
                .offset {
                    IntOffset(0, if (isSelected) -2 else 0)
                },
            tint = color
        )
        Text(
            fontSize = if (isSelected) 12.sp else 10.sp,
            text = stringResource(text),
            fontWeight = Medium,
            color = color
        )
    }
}
