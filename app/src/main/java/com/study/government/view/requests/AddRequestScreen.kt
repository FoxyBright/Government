package com.study.government.view.requests

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement.Absolute.spacedBy
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardDefaults.cardColors
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Alignment.Companion.CenterStart
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.DarkGray
import androidx.compose.ui.graphics.Color.Companion.Gray
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.text.font.FontWeight.Companion.Medium
import androidx.compose.ui.text.font.FontWeight.Companion.SemiBold
import androidx.compose.ui.text.input.KeyboardCapitalization.Companion.Sentences
import androidx.compose.ui.text.input.KeyboardType.Companion.Text
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.study.government.GovernmentApp
import com.study.government.R
import com.study.government.data.DataSource.getRequestById
import com.study.government.model.Destination.ADD_REQUEST
import com.study.government.model.Request
import com.study.government.model.RequestTheme
import com.study.government.tools.getViewModel
import com.study.government.ui.theme.PrimaryColor
import com.study.government.view.components.DefaultButton
import com.study.government.viewmodel.MainViewModel

@Composable
fun AddRequestScreen(
    navHostController: NavHostController,
    requestId: String
) {
    val mainVm = getViewModel<MainViewModel>()

    var request by remember {
        mutableStateOf(
            Request(
                authorName = mainVm.user?.name ?: "",
                authorId = mainVm.user?.id ?: ""
            )
        )
    }

    var problem by remember(request) {
        mutableStateOf(request.problem)
    }

    var theme by remember(request) {
        mutableStateOf(request.theme)
    }

    LaunchedEffect(Unit) {
        GovernmentApp.curScreen = ADD_REQUEST
        getRequestById(requestId.toLong())
            .onSuccess { request = it }
    }

    Scaffold(
        topBar = { TopBar(navHostController) },
        modifier = Modifier.fillMaxSize()
    ) { paddings ->
        Column(
            modifier = Modifier
                .padding(paddings)
                .verticalScroll(rememberScrollState())
        ) {
            Spacer(Modifier.height(20.dp))

            Text(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .align(CenterHorizontally),
                text = stringResource(
                    if (requestId != "-1") {
                        R.string.edit_request
                    } else {
                        R.string.add_request
                    }
                ),
                color = PrimaryColor,
                fontWeight = Bold,
                fontSize = 24.sp
            )

            Spacer(Modifier.height(40.dp))

            Text(
                modifier = Modifier.padding(start = 20.dp),
                text = stringResource(R.string.description),
                fontWeight = SemiBold,
                color = PrimaryColor,
                fontSize = 18.sp
            )

            Spacer(Modifier.height(4.dp))

            BasicTextField(
                onValueChange = { problem = it },
                textStyle = TextStyle(
                    fontWeight = Medium,
                    color = DarkGray,
                    fontSize = 14.sp
                ),
                value = problem,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 12.dp, top = 6.dp)
                    .padding(horizontal = 16.dp)
                    .border(1.dp, Gray, RoundedCornerShape(16.dp))
                    .padding(10.dp, 6.dp),
                keyboardOptions = KeyboardOptions(
                    capitalization = Sentences,
                    keyboardType = Text
                )
            ) { innerText ->
                Column {
                    Text(
                        text = stringResource(R.string.problem_description),
                        fontSize = 14.sp,
                        color = Gray
                    )
                    Spacer(Modifier.height(2.dp))
                    innerText()
                }
            }

            Text(
                text = stringResource(R.string.select_theme),
                modifier = Modifier.padding(start = 20.dp),
                fontWeight = SemiBold,
                color = PrimaryColor,
                fontSize = 18.sp
            )

            Spacer(Modifier.height(4.dp))

            LazyRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = spacedBy(8.dp)
            ) {
                item { Spacer(Modifier.width(8.dp)) }

                items(RequestTheme.entries) {
                    Card(
                        colors = cardColors(if (theme == it) PrimaryColor else White),
                        elevation = CardDefaults.cardElevation(2.dp),
                        onClick = { theme = it },
                        shape = CircleShape
                    ) {
                        Text(
                            color = if (theme == it) White else PrimaryColor,
                            modifier = Modifier.padding(6.dp, 4.dp),
                            text = stringResource(it.label),
                            fontWeight = Medium,
                            fontSize = 14.sp
                        )
                    }
                }

                item { Spacer(Modifier.width(8.dp)) }
            }

            Spacer(Modifier.height(10.dp))

            DefaultButton(
                modifier = Modifier.padding(horizontal = 16.dp),
                text = stringResource(R.string.send_request),
                textColor = PrimaryColor,
                color = White
            ) {
                mainVm.addRequest(request.copy(problem = problem, theme = theme))
                navHostController.navigateUp()
            }
        }
    }
}

@Composable
fun TopBar(navHostController: NavHostController) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(PrimaryColor),
        contentAlignment = CenterStart
    ) {
        Icon(
            painter = painterResource(R.drawable.ic_back_arrow),
            contentDescription = null,
            tint = White,
            modifier = Modifier
                .padding(vertical = 6.dp)
                .padding(start = 12.dp)
                .size(24.dp)
                .clickable(
                    interactionSource = remember {
                        MutableInteractionSource()
                    },
                    indication = null
                ) { navHostController.navigateUp() }
        )
    }
}