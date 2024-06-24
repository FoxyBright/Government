package com.study.government.view.news

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
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.BottomCenter
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush.Companion.verticalGradient
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.DarkGray
import androidx.compose.ui.graphics.Color.Companion.Transparent
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.layout.ContentScale.Companion.Crop
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight.Companion.SemiBold
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextAlign.Companion.End
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.study.government.GovernmentApp.Companion.curScreen
import com.study.government.R
import com.study.government.model.news.New
import com.study.government.model.news.newsPresets
import com.study.government.navigation.Destination.NEW_INFO
import com.study.government.tools.Background
import com.study.government.tools.PrimaryColor
import com.study.government.tools.UriWorker.openLink

@Composable
fun NewInfoScreen(
    navHostController: NavHostController,
    newId: String,
) {
    var new by remember(newId) { mutableStateOf<New?>(null) }

    LaunchedEffect(Unit) {
        curScreen = NEW_INFO
        newsPresets
            .find { it.id == newId.toLongOrNull() }
            ?.let { new = it }
            ?: navHostController.navigateUp()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Background)
            .verticalScroll(rememberScrollState())
    ) {
        new?.apply {
            Box {
                imageUrl?.ifBlank { null }?.let { image ->
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
                        fontSize = 22.sp,
                        textAlign = End,
                        color = White,
                        text = title
                    )
                }
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(40.dp)
                        .background(
                            brush = verticalGradient(
                                listOf(Black.copy(.5f), Transparent)
                            )
                        ),
                    contentAlignment = Alignment.CenterStart
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_back_arrow),
                        contentDescription = null,
                        tint = White,
                        modifier = Modifier
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

            Spacer(Modifier.height(20.dp))

            Text(
                modifier = Modifier.padding(
                    horizontal = 20.dp
                ),
                fontWeight = SemiBold,
                color = DarkGray,
                fontSize = 16.sp,
                text = text
            )

            Spacer(Modifier.height(20.dp))

            val riaNews = stringResource(R.string.ria_news)
            val sourceText = buildAnnotatedString {
                append(stringResource(R.string.source))

                withStyle(SpanStyle(PrimaryColor)) {
                    append(" $riaNews")
                }

                val text = remember { toAnnotatedString().text }
                val linkIndex = remember { text.indexOf(riaNews) }

                addStringAnnotation(
                    end = linkIndex + riaNews.length,
                    annotation = riaNews,
                    start = linkIndex,
                    tag = riaNews
                )
            }

            if (link.isNotBlank()) {
                ClickableText(
                    modifier = Modifier.fillMaxWidth(),
                    style = TextStyle(
                        textAlign = TextAlign.Center,
                        fontWeight = SemiBold,
                        color = DarkGray,
                        fontSize = 16.sp
                    ),
                    text = sourceText,
                    onClick = {
                        sourceText
                            .getStringAnnotations(riaNews, it, it)
                            .firstOrNull()?.let { openLink(link) }
                    }
                )
            }

            Spacer(Modifier.height(40.dp))
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
}