package com.study.government.view.requests

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement.spacedBy
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight.Companion.Medium
import androidx.compose.ui.text.font.FontWeight.Companion.SemiBold
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.study.government.GovernmentApp
import com.study.government.R
import com.study.government.model.Destination.ADD_REQUEST
import com.study.government.model.Destination.REQUESTS
import com.study.government.model.Destination.REQUEST_INFO
import com.study.government.model.DestinationArg.REQUEST_INFO_ARG
import com.study.government.model.NavArgument
import com.study.government.model.Request
import com.study.government.model.RequestStatus
import com.study.government.model.RequestStatus.CANCELED
import com.study.government.model.RequestStatus.CLOSED
import com.study.government.model.RequestStatus.OPENED
import com.study.government.model.RequestTheme
import com.study.government.model.UserRole.USER
import com.study.government.tools.Navigation.navigateTo
import com.study.government.tools.getViewModel
import com.study.government.ui.theme.Background
import com.study.government.ui.theme.PrimaryColor
import com.study.government.view.components.DefaultPullRefreshContainer
import com.study.government.viewmodel.MainViewModel

@Composable
@OptIn(ExperimentalMaterialApi::class)
fun RequestsScreen(navHostController: NavHostController) {
    val mainVm = getViewModel<MainViewModel>()

    val themeFilters = mainVm
        .requestThemeFilters.collectAsState()

    val statusFilters = mainVm
        .requestStatusFilters.collectAsState()

    val requestList = mainVm.requests.collectAsState()

    val requests = remember(
        statusFilters.value, themeFilters.value, requestList.value
    ) {
        requestList.value.filter {
            val inStatus = statusFilters.value.run {
                if (isEmpty()) true else contains(it.status)
            }
            val inTheme = themeFilters.value.run {
                if (isEmpty()) true else contains(it.theme)
            }
            inStatus && inTheme
        }.toMutableStateList()
    }

    LaunchedEffect(Unit) {
        GovernmentApp.curScreen = REQUESTS
        if (mainVm.firstUploadRequests) {
            mainVm.uploadRequests()
        }
    }

    Crossfade(
        targetState = mainVm.pendingRequests,
        label = "Requests animation"
    ) { loading ->
        Scaffold(
            topBar = {
                Filters(
                    themeFilters = themeFilters.value,
                    statusFilters = statusFilters.value
                )
            },
            floatingActionButton = {
                if (mainVm.user?.role == USER) {
                    Card(
                        elevation = cardElevation(4.dp),
                        colors = cardColors(White),
                        shape = CircleShape,
                        onClick = {
                            navHostController.navigateTo(
                                arg = NavArgument(
                                    argument = REQUEST_INFO_ARG,
                                    value = "-1"
                                ),
                                dest = ADD_REQUEST
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
                refreshing = mainVm.refreshRequests,
                modifier = Modifier
                    .fillMaxSize()
                    .background(Background)
                    .padding(padding),
                onRefresh = {
                    mainVm.refreshRequests = true
                    mainVm.uploadRequests()
                }
            ) {
                when {
                    loading -> Box(contentAlignment = Center) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(40.dp),
                            color = PrimaryColor,
                            strokeWidth = 3.dp
                        )
                    }

                    requests.isEmpty() -> RequestsPlaceholder()

                    else -> RequestsContent(
                        navHostController = navHostController,
                        requests = requests
                    )
                }
            }
        }
    }
}

@Composable
private fun Filters(
    themeFilters: List<RequestTheme>,
    statusFilters: List<RequestStatus>,
    modifier: Modifier = Modifier
) {
    val mainVm = getViewModel<MainViewModel>()

    Column(Modifier.background(White)) {
        Text(
            modifier = Modifier.padding(top = 6.dp, start = 6.dp),
            text = stringResource(R.string.filters),
            fontWeight = SemiBold,
            color = PrimaryColor,
            fontSize = 20.sp
        )

        LazyRow(
            modifier = modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            horizontalArrangement = spacedBy(8.dp)
        ) {
            item { Spacer(Modifier.width(8.dp)) }

            item {
                FilterItem(
                    isSelected = statusFilters.isEmpty()
                            && themeFilters.isEmpty(),
                    text = stringResource(R.string.all)
                ) { mainVm.clearFilters() }
            }

            items(RequestStatus.entries) { status ->
                val isSelected by remember(statusFilters) {
                    mutableStateOf(statusFilters.contains(status))
                }
                FilterItem(
                    isSelected = isSelected,
                    text = stringResource(
                        when (status) {
                            OPENED -> R.string.opened
                            CANCELED -> R.string.canceled
                            CLOSED -> R.string.closed
                        }
                    )
                ) { mainVm.changeStatusFilter(status) }
            }

            items(RequestTheme.entries) { theme ->
                val isSelected by remember(themeFilters) {
                    mutableStateOf(themeFilters.contains(theme))
                }
                FilterItem(
                    text = stringResource(theme.label),
                    isSelected = isSelected
                ) { mainVm.changeThemeFilter(theme) }
            }

            item { Spacer(Modifier.width(8.dp)) }
        }
    }
}

@Composable
private fun FilterItem(
    isSelected: Boolean,
    text: String,
    onClick: () -> Unit
) {
    Card(
        elevation = cardElevation(2.dp),
        shape = CircleShape,
        onClick = onClick,
        colors = cardColors(
            containerColor = if (isSelected) {
                PrimaryColor
            } else {
                White
            }
        )
    ) {
        Text(
            color = if (isSelected) {
                White
            } else {
                PrimaryColor
            },
            modifier = Modifier.padding(6.dp, 4.dp),
            fontWeight = Medium,
            fontSize = 14.sp,
            text = text
        )
    }
}

@Composable
private fun RequestsContent(
    requests: List<Request>,
    navHostController: NavHostController
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Background)
            .padding(horizontal = 16.dp)
    ) {
        item { Spacer(Modifier.height(12.dp)) }

        items(requests) { request ->
            RequestItem(request) {
                navHostController.navigateTo(
                    arg = NavArgument(
                        value = request.id.toString(),
                        argument = REQUEST_INFO_ARG
                    ),
                    dest = REQUEST_INFO
                )
            }

            Spacer(Modifier.height(12.dp))
        }
    }
}