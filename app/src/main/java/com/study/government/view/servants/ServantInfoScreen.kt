package com.study.government.view.servants

import android.net.Uri
import androidx.compose.foundation.background
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Alignment.Companion.TopEnd
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.DarkGray
import androidx.compose.ui.graphics.Color.Companion.Gray
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.text.font.FontWeight.Companion.SemiBold
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.study.government.GovernmentApp
import com.study.government.R
import com.study.government.data.DataSource.getServantById
import com.study.government.model.Destination.ADD_SERVANT
import com.study.government.model.Destination.SERVANTS_INFO
import com.study.government.model.DestinationArg.SERVANT_INFO_ARG
import com.study.government.model.NavArgument
import com.study.government.model.Servant
import com.study.government.model.UserRole.ADMIN
import com.study.government.tools.Navigation.navigateTo
import com.study.government.tools.getViewModel
import com.study.government.ui.theme.PrimaryColor
import com.study.government.ui.theme.RedColor
import com.study.government.view.components.ProgressIndicator
import com.study.government.view.requests.TopBar
import com.study.government.viewmodel.MainViewModel

@Composable
fun ServantInfoScreen(
    navHostController: NavHostController,
    servantId: String
) {
    var servant by remember(servantId) {
        mutableStateOf<Servant?>(null)
    }

    LaunchedEffect(Unit) {
        GovernmentApp.curScreen = SERVANTS_INFO
        getServantById(servantId.toLong())
            .onFailure { navHostController.navigateUp() }
            .onSuccess { servant = it }
    }

    servant?.apply {
        Scaffold(
            topBar = { TopBar(navHostController) }
        ) { paddings ->
            Content(
                navHostController = navHostController,
                modifier = Modifier
                    .padding(paddings),
                servant = this
            )
        }
    } ?: run {
        ProgressIndicator(PrimaryColor)
    }
}

@Composable
private fun Content(
    navHostController: NavHostController,
    modifier: Modifier = Modifier,
    servant: Servant,
) {
    val mainVm = getViewModel<MainViewModel>()

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
            .verticalScroll(rememberScrollState()),
    ) {
        Spacer(Modifier.height(30.dp))

        Box(Modifier.fillMaxWidth()) {
            if (mainVm.user?.role == ADMIN) {
                Row(
                    verticalAlignment = CenterVertically,
                    modifier = Modifier.align(TopEnd),
                ) {
                    Icon(
                        imageVector = ImageVector
                            .vectorResource(R.drawable.ic_edit),
                        contentDescription = null,
                        tint = PrimaryColor,
                        modifier = Modifier
                            .padding(end = 12.dp)
                            .size(24.dp)
                            .clickable(
                                interactionSource = remember {
                                    MutableInteractionSource()
                                },
                                indication = null
                            ) {
                                navHostController.navigateTo(
                                    arg = NavArgument(
                                        argument = SERVANT_INFO_ARG,
                                        value = servant.id
                                    ),
                                    dest = ADD_SERVANT
                                )
                            }
                    )

                    Icon(
                        imageVector = ImageVector
                            .vectorResource(R.drawable.ic_delete),
                        contentDescription = null,
                        tint = RedColor,
                        modifier = Modifier
                            .padding(end = 12.dp)
                            .size(24.dp)
                            .clickable(
                                interactionSource = remember {
                                    MutableInteractionSource()
                                },
                                indication = null
                            ) {
                                mainVm.deleteServant(servant)
                                navHostController.navigateUp()
                            }
                    )
                }
            }
            ServantAvatar(
                modifier = Modifier.align(Center),
                servant = servant,
                size = 140.dp
            )
        }

        Spacer(Modifier.height(30.dp))

        Text(
            modifier = Modifier.align(CenterHorizontally),
            textAlign = TextAlign.Center,
            text = servant.name,
            fontWeight = Bold,
            color = DarkGray,
            fontSize = 22.sp
        )

        Spacer(Modifier.height(20.dp))

        Text(
            fontSize = 18.sp,
            color = Gray,
            text = buildAnnotatedString {
                withStyle(
                    SpanStyle(
                        color = PrimaryColor,
                        fontWeight = SemiBold
                    )
                ) { append(stringResource(R.string.departament)) }
                append(" ")
                append(servant.department)
            }
        )

        Spacer(Modifier.height(10.dp))

        Text(
            fontSize = 18.sp,
            color = Gray,
            text = buildAnnotatedString {
                withStyle(
                    SpanStyle(
                        color = PrimaryColor,
                        fontWeight = SemiBold
                    )
                ) { append(stringResource(R.string.post)) }
                append(" ")
                append(servant.post)
            }
        )

        Spacer(Modifier.height(20.dp))

        Text(
            modifier = Modifier.align(CenterHorizontally),
            text = stringResource(R.string.servant_description),
            textAlign = TextAlign.Center,
            fontWeight = SemiBold,
            color = PrimaryColor,
            fontSize = 20.sp
        )

        Spacer(Modifier.height(10.dp))

        Text(
            text = servant.description,
            fontSize = 16.sp,
            color = DarkGray
        )

        Spacer(Modifier.height(30.dp))

        Text(
            modifier = Modifier.align(CenterHorizontally),
            textAlign = TextAlign.Center,
            fontWeight = SemiBold,
            color = PrimaryColor,
            text = stringResource(R.string.merits),
            fontSize = 20.sp
        )

        Spacer(Modifier.height(10.dp))

        Text(
            text = servant.merits,
            fontSize = 16.sp,
            color = DarkGray
        )

        Spacer(Modifier.height(20.dp))
    }
}

@Composable
fun ServantAvatar(
    servant: Servant,
    size: Dp,
    modifier: Modifier = Modifier
) {
    when {
        servant.avatarPath.isNotBlank() -> Uri.parse(servant.avatarPath)
        servant.avatarUrl.isNotBlank() -> servant.avatarUrl
        else -> null
    }?.let { image ->
        AsyncImage(
            contentDescription = null,
            modifier = modifier
                .size(size)
                .clip(CircleShape),
            contentScale = ContentScale.Crop,
            model = image
        )
    } ?: run {
        Box(
            modifier = modifier
                .size(size)
                .clip(CircleShape)
                .background(PrimaryColor),
            contentAlignment = Center
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(
                    R.drawable.profile_placeholder
                ),
                contentDescription = null,
                modifier = Modifier
                    .size(size / 4 * 3)
                    .clip(CircleShape),
                tint = Color.White
            )
        }
    }
}