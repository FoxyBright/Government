package com.study.government.view.servants

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize.Max
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults.cardColors
import androidx.compose.material3.CardDefaults.cardElevation
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.DarkGray
import androidx.compose.ui.graphics.Color.Companion.Gray
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight.Companion.Medium
import androidx.compose.ui.text.font.FontWeight.Companion.Normal
import androidx.compose.ui.text.font.FontWeight.Companion.SemiBold
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.study.government.GovernmentApp
import com.study.government.R
import com.study.government.model.Destination.ADD_SERVANT
import com.study.government.model.Destination.SERVANTS
import com.study.government.model.Destination.SERVANTS_INFO
import com.study.government.model.DestinationArg.SERVANT_INFO_ARG
import com.study.government.model.NavArgument
import com.study.government.model.Servant
import com.study.government.model.UserRole.ADMIN
import com.study.government.tools.Navigation.navigateTo
import com.study.government.tools.getViewModel
import com.study.government.ui.theme.Background
import com.study.government.ui.theme.PrimaryColor
import com.study.government.view.components.DefaultPullRefreshContainer
import com.study.government.viewmodel.MainViewModel

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ServantsScreen(navHostController: NavHostController) {

    val mainVm = getViewModel<MainViewModel>()

    LaunchedEffect(Unit) {
        GovernmentApp.curScreen = SERVANTS
        if (mainVm.servants.isEmpty()) {
            mainVm.uploadServants()
        }
    }

    Crossfade(
        targetState = mainVm.pendingServants,
        label = "Requests animation"
    ) { loading ->
        Scaffold(
            floatingActionButton = {
                if (mainVm.user?.role == ADMIN) {
                    Card(
                        elevation = cardElevation(4.dp),
                        colors = cardColors(White),
                        shape = CircleShape,
                        onClick = {
                            navHostController.navigateTo(
                                arg = NavArgument(
                                    argument = SERVANT_INFO_ARG,
                                    value = "-1"
                                ),
                                dest = ADD_SERVANT
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
        ) { padding ->
            DefaultPullRefreshContainer(
                refreshing = mainVm.refreshServants,
                modifier = Modifier
                    .fillMaxSize()
                    .background(Background)
                    .padding(padding),
                onRefresh = {
                    mainVm.refreshServants = true
                    mainVm.uploadServants()
                }
            ) {
                if (loading) {
                    Box(contentAlignment = Center) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(40.dp),
                            color = PrimaryColor,
                            strokeWidth = 3.dp
                        )
                    }
                } else {
                    ServantsContent(
                        navHostController = navHostController,
                        servants = mainVm.servants
                    )
                }
            }
        }
    }
}

@Composable
private fun ServantsContent(
    servants: List<Servant>,
    navHostController: NavHostController
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Background)
            .padding(horizontal = 16.dp)
    ) {
        item { Spacer(Modifier.height(12.dp)) }

        item {
            Text(
                text = stringResource(R.string.servants_list),
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                color = PrimaryColor,
                fontWeight = SemiBold,
                fontSize = 24.sp
            )
        }

        item { Spacer(Modifier.height(20.dp)) }

        items(servants) { servant ->
            ServantItem(servant) {
                navHostController.navigateTo(
                    arg = NavArgument(
                        value = servant.id.toString(),
                        argument = SERVANT_INFO_ARG
                    ),
                    dest = SERVANTS_INFO
                )
            }

            Spacer(Modifier.height(12.dp))
        }
    }
}

@Composable
private fun ServantItem(
    servant: Servant,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier.height(Max),
        elevation = cardElevation(2.dp),
        colors = cardColors(White),
        onClick = onClick
    ) {
        Row(
            verticalAlignment = CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
        ) {
            ServantAvatar(servant, 80.dp)

            Spacer(Modifier.width(8.dp))

            Column {
                Text(
                    text = servant.name,
                    fontWeight = Medium,
                    fontSize = 18.sp,
                    color = DarkGray
                )

                Spacer(Modifier.height(4.dp))

                Text(
                    text = servant.post,
                    fontWeight = Normal,
                    fontSize = 14.sp,
                    color = Gray
                )
            }
        }
    }
}