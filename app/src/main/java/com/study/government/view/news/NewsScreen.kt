package com.study.government.view.news

import android.annotation.SuppressLint
import android.net.Uri
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement.SpaceBetween
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.*
import androidx.compose.material3.CardDefaults.cardColors
import androidx.compose.material3.CardDefaults.cardElevation
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment.Companion.BottomCenter
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterStart
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush.Companion.verticalGradient
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.Transparent
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.layout.ContentScale.Companion.Crop
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight.Companion.Medium
import androidx.compose.ui.text.font.FontWeight.Companion.SemiBold
import androidx.compose.ui.text.style.TextAlign.Companion.End
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.study.government.GovernmentApp
import com.study.government.R
import com.study.government.model.Destination.ADD_NEW
import com.study.government.model.Destination.NEWS
import com.study.government.model.Destination.NEW_INFO
import com.study.government.model.DestinationArg.NEW_INFO_ARG
import com.study.government.model.NavArgument
import com.study.government.model.New
import com.study.government.model.UserRole.ADMIN
import com.study.government.tools.Navigation.navigateTo
import com.study.government.tools.getViewModel
import com.study.government.ui.theme.Background
import com.study.government.ui.theme.PrimaryColor
import com.study.government.view.components.DefaultPullRefreshContainer
import com.study.government.view.components.ProgressIndicator
import com.study.government.viewmodel.MainViewModel
import java.text.SimpleDateFormat
import java.util.Date

@Composable
fun NewsScreen(navHostController: NavHostController) {
    val mainVm = getViewModel<MainViewModel>()
    LaunchedEffect(Unit) { GovernmentApp.curScreen = NEWS }

    Crossfade(
        targetState = mainVm.newsCategory == null,
        label = "News category animation"
    ) { showCategory ->
        Scaffold(
            topBar = {
                mainVm.newsCategory?.apply {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(40.dp)
                            .background(PrimaryColor),
                        contentAlignment = CenterStart
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.ic_categories),
                            contentDescription = null,
                            tint = White,
                            modifier = Modifier
                                .padding(start = 16.dp)
                                .size(32.dp)
                                .clickable(
                                    interactionSource = remember {
                                        MutableInteractionSource()
                                    },
                                    indication = null
                                ) {
                                    mainVm.newsCategory = null
                                    mainVm.news.clear()
                                }
                        )
                        Text(
                            modifier = Modifier.align(Center),
                            text = stringResource(label),
                            fontWeight = Medium,
                            fontSize = 16.sp,
                            color = White
                        )
                    }
                }
            }
        ) { padding ->
            if (showCategory) {
                NewsCategoryScreen(Modifier.padding(padding))
            } else {
                NewsContent(
                    navHostController = navHostController,
                    modifier = Modifier.padding(padding)
                )
            }
        }
    }
}

@Composable
@OptIn(ExperimentalMaterialApi::class)
private fun NewsContent(
    navHostController: NavHostController,
    modifier: Modifier = Modifier,
) {
    val mainVm = getViewModel<MainViewModel>()

    LaunchedEffect(Unit) {
        GovernmentApp.curScreen = NEWS
        if (mainVm.news.isEmpty()) {
            mainVm.uploadNews()
        }
    }

    Crossfade(
        targetState = mainVm.pendingNews
                && mainVm.news.isEmpty(),
        label = "News animation",
        modifier = modifier
    ) { loading ->
        DefaultPullRefreshContainer(
            refreshing = mainVm.refreshNews,
            onRefresh = {
                mainVm.refreshNews = true
                mainVm.uploadNews()
            }
        ) {
            if (loading) {
                ProgressIndicator(
                    color = mainVm.newsCategory?.color
                        ?: PrimaryColor
                )
            } else {
                Scaffold(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Background),
                    floatingActionButton = {
                        if (mainVm.user?.role == ADMIN) {
                            Card(
                                elevation = cardElevation(4.dp),
                                colors = cardColors(White),
                                shape = CircleShape,
                                onClick = {
                                    navHostController.navigateTo(
                                        arg = NavArgument(
                                            argument = NEW_INFO_ARG,
                                            value = "-1"
                                        ),
                                        dest = ADD_NEW
                                    )
                                }
                            ) {
                                Icon(
                                    painter = painterResource(R.drawable.ic_plus),
                                    contentDescription = null,
                                    tint = PrimaryColor,
                                    modifier = Modifier
                                        .padding(16.dp)
                                        .size(20.dp)
                                )
                            }
                        }
                    }
                ) { paddings ->
                    LazyColumn(
                        modifier = Modifier

                            .padding(horizontal = 16.dp)
                            .padding(paddings)
                    ) {
                        item { Spacer(Modifier.height(12.dp)) }
                        items(mainVm.news) { new ->
                            NewItem(new) {
                                navHostController.navigateTo(
                                    arg = NavArgument(NEW_INFO_ARG, new.id),
                                    dest = NEW_INFO
                                )
                            }
                            Spacer(Modifier.height(12.dp))
                        }
                    }
                }
            }
        }
    }
}

@Composable
@SuppressLint("SimpleDateFormat")
private fun NewItem(
    new: New,
    onClick: () -> Unit,
) {
    val newTime by remember(new) {
        val time = SimpleDateFormat("HH:mm")
            .format(Date(new.date))
        mutableStateOf(time)
    }

    Card(
        elevation = cardElevation(2.dp),
        colors = cardColors(White),
        onClick = onClick
    ) {
        Column {
            Box {
                when {
                    new.imagePath.isNotBlank() -> Uri.parse(new.imagePath)
                    new.imageUrl.isNotBlank() -> new.imageUrl
                    else -> null
                }?.let { image ->
                    AsyncImage(
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp),
                        contentScale = Crop,
                        model = image
                    )
                } ?: run {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                            .background(PrimaryColor),
                        contentAlignment = Center
                    ) {
                        Text(
                            text = stringResource(R.string.new_placeholder_image),
                            fontWeight = SemiBold,
                            fontSize = 30.sp,
                            color = White
                        )
                    }
                }
                Box(
                    contentAlignment = BottomCenter,
                    modifier = Modifier
                        .background(
                            brush = verticalGradient(
                                listOf(Transparent, Black.copy(.5f))
                            )
                        )
                        .fillMaxWidth()
                        .align(BottomCenter)
                        .padding(12.dp)
                ) {
                    Text(
                        modifier = Modifier.padding(top = 20.dp),
                        fontWeight = SemiBold,
                        text = new.title,
                        fontSize = 22.sp,
                        textAlign = End,
                        color = White
                    )
                }
            }
            Row(
                horizontalArrangement = SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(6.dp)
            ) {
                Text(
                    color = PrimaryColor,
                    modifier = Modifier,
                    fontSize = 12.sp,
                    text = newTime
                )
                Text(
                    text = stringResource(R.string.read_next),
                    color = PrimaryColor,
                    modifier = Modifier,
                    fontWeight = Medium,
                    fontSize = 14.sp
                )
            }
        }
    }
}