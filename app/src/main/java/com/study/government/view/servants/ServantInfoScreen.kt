package com.study.government.view.servants

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.ui.Alignment.Companion.CenterStart
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.DarkGray
import androidx.compose.ui.graphics.Color.Companion.Gray
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.ContentScale.Companion.Crop
import androidx.compose.ui.res.painterResource
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
import com.study.government.GovernmentApp.Companion.curScreen
import com.study.government.R
import com.study.government.model.Servant
import com.study.government.model.servantsPresets
import com.study.government.navigation.Destination.SERVANTS_INFO
import com.study.government.tools.Background
import com.study.government.tools.PrimaryColor

@Composable
fun ServantInfoScreen(
    navHostController: NavHostController,
    servantId: String
) {
    var servant by remember(servantId) {
        mutableStateOf<Servant?>(null)
    }

    LaunchedEffect(Unit) {
        curScreen = SERVANTS_INFO

        servantsPresets
            .find { it.id == servantId.toLongOrNull() }
            ?.let { servant = it }
            ?: navHostController.navigateUp()
    }

    servant?.apply {
        Scaffold(
            topBar = { TopBar(navHostController) }
        ) { paddings ->
            Content(
                modifier = Modifier
                    .padding(paddings),
                servant = this
            )
        }
    } ?: Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Background),
        contentAlignment = Center
    ) {
        CircularProgressIndicator(
            modifier = Modifier.size(40.dp),
            color = PrimaryColor,
            strokeWidth = 3.dp
        )
    }
}

@Composable
private fun Content(
    modifier: Modifier = Modifier,
    servant: Servant,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
            .verticalScroll(rememberScrollState()),
    ) {
        Spacer(Modifier.height(30.dp))

        Box(Modifier.fillMaxWidth()) {
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
private fun TopBar(navHostController: NavHostController) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Background),
        contentAlignment = Center
    ) {
        Icon(
            painter = painterResource(R.drawable.ic_back_arrow),
            contentDescription = null,
            tint = PrimaryColor,
            modifier = Modifier
                .align(CenterStart)
                .padding(12.dp)
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

@Composable
fun ServantAvatar(
    servant: Servant,
    size: Dp,
    modifier: Modifier = Modifier
) {
    servant.avatarUrl.ifBlank { null }?.let { image ->
        AsyncImage(
            contentDescription = null,
            modifier = modifier
                .size(size)
                .clip(CircleShape),
            contentScale = Crop,
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