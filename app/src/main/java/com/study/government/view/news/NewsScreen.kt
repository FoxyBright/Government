package com.study.government.view.news

import android.annotation.SuppressLint
import androidx.activity.ComponentActivity
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement.SpaceBetween
import androidx.compose.foundation.layout.Arrangement.spacedBy
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults.cardColors
import androidx.compose.material3.CardDefaults.cardElevation
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment.Companion.BottomCenter
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterStart
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush.Companion.verticalGradient
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.Transparent
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.layout.ContentScale.Companion.Crop
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight.Companion.Medium
import androidx.compose.ui.text.font.FontWeight.Companion.SemiBold
import androidx.compose.ui.text.style.TextAlign.Companion.End
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.study.government.GovernmentApp.Companion.curScreen
import com.study.government.R
import com.study.government.model.news.New
import com.study.government.model.news.newsPresets
import com.study.government.navigation.Destination.NEWS
import com.study.government.navigation.Destination.NEW_INFO
import com.study.government.navigation.DestinationArg.NEW_INFO_ARG
import com.study.government.navigation.NavArgument
import com.study.government.navigation.Navigation.navigateTo
import com.study.government.tools.Background
import com.study.government.tools.PrimaryColor
import java.text.SimpleDateFormat
import java.util.Date

@Composable
fun NewsScreen(navHostController: NavHostController) {
    val newsVm = viewModel<NewsViewModel>(
        LocalContext.current as ComponentActivity
    )

    LaunchedEffect(Unit) { curScreen = NEWS }

    Crossfade(
        targetState = newsVm.newsCategory == null,
        label = "News category animation"
    ) { showCategory ->
        Scaffold(
            topBar = {
                newsVm.newsCategory?.apply {
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
                                ) { newsVm.newsCategory = null }
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
                NewsCategoryScreen(
                    modifier = Modifier.padding(padding)
                ) { newsVm.newsCategory = it }
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
private fun NewsContent(
    navHostController: NavHostController,
    modifier: Modifier = Modifier
) {
    LaunchedEffect(Unit) { curScreen = NEWS }

    Scaffold(
        modifier = modifier
            .fillMaxSize()
            .background(Background),
    ) { paddings ->
        LazyColumn(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .padding(paddings),
            verticalArrangement = spacedBy(12.dp)
        ) {
            item { Spacer(Modifier) }

            items(newsPresets) { new ->
                NewItem(new) {
                    navHostController.navigateTo(
                        arg = NavArgument(NEW_INFO_ARG, new.id),
                        dest = NEW_INFO
                    )
                }
            }

            item { Spacer(Modifier) }
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
                new.imageUrl?.ifBlank { null }?.let { image ->
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