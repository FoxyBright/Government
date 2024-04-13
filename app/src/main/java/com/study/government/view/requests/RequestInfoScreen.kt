package com.study.government.view.requests

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons.Default
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Alignment.Companion.CenterStart
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Alignment.Companion.TopEnd
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.DarkGray
import androidx.compose.ui.graphics.Color.Companion.Gray
import androidx.compose.ui.graphics.Color.Companion.LightGray
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.text.font.FontWeight.Companion.Medium
import androidx.compose.ui.text.font.FontWeight.Companion.Normal
import androidx.compose.ui.text.font.FontWeight.Companion.SemiBold
import androidx.compose.ui.text.input.KeyboardCapitalization.Companion.Sentences
import androidx.compose.ui.text.input.KeyboardType.Companion.Text
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.study.government.GovernmentApp
import com.study.government.R
import com.study.government.data.DataSource
import com.study.government.model.Destination
import com.study.government.model.Destination.ADD_REQUEST
import com.study.government.model.DestinationArg.REQUEST_INFO_ARG
import com.study.government.model.NavArgument
import com.study.government.model.Request
import com.study.government.model.RequestStatus.CANCELED
import com.study.government.model.RequestStatus.CLOSED
import com.study.government.model.RequestStatus.OPENED
import com.study.government.model.UserRole.ADMIN
import com.study.government.model.UserRole.USER
import com.study.government.tools.Navigation.navigateTo
import com.study.government.tools.getViewModel
import com.study.government.ui.theme.EcologyColor
import com.study.government.ui.theme.PrimaryColor
import com.study.government.ui.theme.RedColor
import com.study.government.view.components.DefaultButton
import com.study.government.view.components.ProgressIndicator
import com.study.government.viewmodel.MainViewModel

@Composable
fun RequestInfoScreen(
    navHostController: NavHostController,
    requestId: String,
) {
    val mainVm = getViewModel<MainViewModel>()
    var request by remember(requestId) {
        mutableStateOf<Request?>(null)
    }

    val comment = remember {
        mutableStateOf("")
    }

    LaunchedEffect(Unit) {
        GovernmentApp.curScreen = Destination.REQUEST_INFO
        DataSource.getRequestById(requestId.toLong())
            .onFailure { navHostController.navigateUp() }
            .onSuccess { request = it }
    }

    request?.apply {
        Scaffold(
            topBar = {
                TopBar(id, navHostController)
            }
        ) { paddings ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddings)
                    .verticalScroll(rememberScrollState())
            ) {
                Spacer(Modifier.height(20.dp))

                Label(
                    title = stringResource(R.string.author),
                    label = authorName
                )

                Spacer(Modifier.height(12.dp))

                Label(
                    title = stringResource(R.string.theme),
                    label = stringResource(theme.label)
                )

                Spacer(Modifier.height(12.dp))

                Label(
                    title = stringResource(R.string.status),
                    label = stringResource(
                        when (status) {
                            CANCELED -> R.string.canceled_request
                            OPENED -> R.string.opened_request
                            CLOSED -> R.string.closed_request
                        }
                    )
                ) {
                    Spacer(
                        modifier = Modifier
                            .size(8.dp)
                            .background(
                                color = status.color,
                                shape = CircleShape
                            )
                    )
                }

                Spacer(Modifier.height(30.dp))

                Content(this@apply)

                Spacer(Modifier.height(20.dp))

                when {
                    status != OPENED -> Unit

                    mainVm.user?.role == USER -> {
                        DefaultButton(
                            text = stringResource(R.string.edit_request),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp),
                            textColor = PrimaryColor,
                            color = White
                        ) {
                            navHostController.navigateTo(
                                dest = ADD_REQUEST,
                                arg = NavArgument(
                                    argument = REQUEST_INFO_ARG,
                                    value = id
                                )
                            )
                        }

                        Spacer(Modifier.height(8.dp))

                        DefaultButton(
                            text = stringResource(R.string.delete_request),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp),
                            textColor = White,
                            color = RedColor
                        ) {
                            mainVm.deleteRequest(this@apply)
                            navHostController.navigateUp()
                        }
                    }

                    mainVm.user?.role == ADMIN -> {
                        Comment(comment)

                        Spacer(Modifier.height(10.dp))

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp),
                            verticalAlignment = CenterVertically
                        ) {
                            DefaultButton(
                                text = stringResource(R.string.cancel_request),
                                modifier = Modifier.weight(1f),
                                color = RedColor
                            ) {
                                mainVm.answerRequest(
                                    copy(
                                        answer = comment.value.trim(),
                                        status = CANCELED
                                    )
                                )
                                navHostController.navigateUp()
                            }

                            Spacer(Modifier.width(12.dp))

                            DefaultButton(
                                text = stringResource(R.string.allow_request),
                                modifier = Modifier.weight(1f),
                                color = EcologyColor
                            ) {
                                mainVm.answerRequest(
                                    copy(
                                        answer = comment.value.trim(),
                                        status = CLOSED
                                    )
                                )
                                navHostController.navigateUp()
                            }
                        }
                    }
                }

                Spacer(Modifier.height(20.dp))
            }
        }
    } ?: run {
        ProgressIndicator(PrimaryColor)
    }
}

@Composable
private fun TopBar(
    requestId: Long,
    navHostController: NavHostController
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(PrimaryColor),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            painter = painterResource(R.drawable.ic_back_arrow),
            contentDescription = null,
            tint = White,
            modifier = Modifier
                .align(CenterStart)
                .padding(start = 12.dp)
                .size(24.dp)
                .clickable(
                    interactionSource = remember {
                        MutableInteractionSource()
                    },
                    indication = null
                ) { navHostController.navigateUp() }
        )

        Text(
            modifier = Modifier.padding(vertical = 10.dp),
            text = buildString {
                append(stringResource(R.string.request))
                append(" ")
                append("$requestId")
            },
            fontWeight = Bold,
            fontSize = 22.sp,
            color = White
        )
    }
}

@Composable
private fun Label(
    title: String,
    label: String,
    labelMarker: (@Composable () -> Unit)? = null,
) {
    Row(
        modifier = Modifier.padding(horizontal = 16.dp),
        verticalAlignment = CenterVertically
    ) {
        Text(
            color = PrimaryColor,
            fontWeight = Bold,
            fontSize = 20.sp,
            text = title
        )

        Spacer(Modifier.width(4.dp))

        Text(
            fontWeight = SemiBold,
            color = DarkGray,
            fontSize = 18.sp,
            text = label
        )

        labelMarker?.let {
            Spacer(Modifier.width(4.dp))
            it()
        }
    }
}

@Composable
private fun Content(request: Request) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .border(
                shape = RoundedCornerShape(16.dp),
                color = LightGray,
                width = 2.dp
            )
    ) {
        Column(Modifier.padding(12.dp)) {
            Text(
                modifier = Modifier.align(CenterHorizontally),
                text = stringResource(R.string.problem_description),
                fontWeight = SemiBold,
                color = PrimaryColor,
                fontSize = 18.sp
            )

            Spacer(Modifier.height(10.dp))

            Text(
                text = request.problem,
                fontWeight = Normal,
                color = DarkGray,
                fontSize = 14.sp
            )

            if (request.answer.isNotBlank()) {

                Spacer(Modifier.height(10.dp))

                HorizontalDivider(
                    modifier = Modifier.padding(horizontal = 20.dp),
                    color = LightGray,
                    thickness = 1.dp
                )

                Spacer(Modifier.height(10.dp))

                Text(
                    text = buildAnnotatedString {
                        withStyle(
                            SpanStyle(
                                color = PrimaryColor,
                                fontWeight = Medium
                            )
                        ) { append(stringResource(R.string.request_comment)) }
                        append(" ")
                        append(request.answer)
                    },
                    fontWeight = Normal,
                    color = DarkGray,
                    fontSize = 14.sp
                )
            }

            Spacer(Modifier.height(10.dp))
        }
    }
}

@Composable
private fun Comment(comment: MutableState<String>) {
    var showTextField by remember {
        mutableStateOf(false)
    }

    Crossfade(
        targetState = showTextField,
        label = "Comment animation"
    ) { show ->
        if (show) {
            Box {
                BasicTextField(
                    onValueChange = { comment.value = it },
                    value = comment.value,
                    textStyle = TextStyle(
                        fontWeight = Medium,
                        color = DarkGray,
                        fontSize = 14.sp
                    ),
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
                            text = stringResource(R.string.comment_placeholder),
                            fontSize = 14.sp,
                            color = Gray
                        )
                        Spacer(Modifier.height(2.dp))
                        innerText()
                    }
                }
                Icon(
                    imageVector = Default.Clear,
                    contentDescription = null,
                    modifier = Modifier
                        .align(TopEnd)
                        .padding(end = 26.dp, top = 16.dp)
                        .size(16.dp)
                        .clickable(
                            interactionSource = remember {
                                MutableInteractionSource()
                            },
                            indication = null
                        ) {
                            showTextField = false
                            comment.value = ""
                        },
                    tint = Gray
                )
            }
        } else {
            DefaultButton(
                text = stringResource(R.string.comment),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                textColor = PrimaryColor,
                color = White
            ) { showTextField = true }
        }
    }
}