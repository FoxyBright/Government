package com.study.government.view.appeals

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults.cardColors
import androidx.compose.material3.CardDefaults.cardElevation
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme.shapes
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.DarkGray
import androidx.compose.ui.graphics.Color.Companion.LightGray
import androidx.compose.ui.graphics.Color.Companion.Transparent
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight.Companion.Medium
import androidx.compose.ui.text.font.FontWeight.Companion.SemiBold
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration.Companion.Underline
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.study.government.GovernmentApp.Companion.curScreen
import com.study.government.R
import com.study.government.model.appeals.AppealsQuestion
import com.study.government.model.appeals.AppealsTheme
import com.study.government.navigation.Destination.APPEALS
import com.study.government.tools.PrimaryColor
import com.study.government.tools.UriWorker.openMail
import com.study.government.tools.UriWorker.openMap
import com.study.government.tools.UriWorker.openPhone

@Composable
fun AppealsScreen() {
    LaunchedEffect(Unit) { curScreen = APPEALS }

    var selectedTheme by remember {
        mutableStateOf<AppealsTheme?>(null)
    }

    var selectedQuestion by remember {
        mutableStateOf<AppealsQuestion?>(null)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Spacer(Modifier.height(12.dp))

        Text(
            text = stringResource(R.string.appeals),
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            color = PrimaryColor,
            fontWeight = SemiBold,
            fontSize = 24.sp
        )

        Spacer(Modifier.height(12.dp))

        Text(
            text = stringResource(R.string.appeals_description),
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            fontWeight = Medium,
            color = DarkGray,
            fontSize = 16.sp
        )

        Spacer(Modifier.height(20.dp))

        Themes(selectedTheme) {
            selectedQuestion = null
            selectedTheme = it
        }

        selectedTheme?.let {
            Spacer(Modifier.height(20.dp))
            Questions(selectedQuestion) {
                selectedQuestion = it
            }
        }

        Spacer(Modifier.height(40.dp))

        selectedQuestion?.run { Contacts() }
    }
}

@Composable
private fun AppealsQuestion.Contacts() {
    Text(
        text = buildString {
            append(stringResource(R.string.question_address))
            append(organization)
            append(" ")
            append(stringResource(R.string.on_address))
            append("\n")
            append(address)
        },
        fontWeight = Medium,
        color = DarkGray,
        fontSize = 18.sp
    )

    Spacer(Modifier.height(12.dp))

    Card(
        onClick = { openMap(address) },
        modifier = Modifier.fillMaxWidth(),
        elevation = cardElevation(2.dp),
        colors = cardColors(White)
    ) {
        Text(
            text = stringResource(R.string.show_on_map),
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(6.dp, 10.dp),
            fontWeight = Medium,
            color = PrimaryColor,
            fontSize = 16.sp
        )
    }

    if (phones.isNotEmpty()) {
        Spacer(Modifier.height(20.dp))

        Text(
            text = stringResource(R.string.or_by_phone),
            fontWeight = Medium,
            color = DarkGray,
            fontSize = 18.sp
        )

        Spacer(Modifier.height(12.dp))

        phones.forEach { phone ->
            Text(
                textDecoration = Underline,
                color = PrimaryColor,
                fontWeight = Medium,
                fontSize = 18.sp,
                text = phone,
                modifier = Modifier.clickable(
                    interactionSource = remember {
                        MutableInteractionSource()
                    },
                    indication = null
                ) { openPhone(phone) }
            )

            Spacer(Modifier.height(6.dp))
        }
    }

    if (email.isNotBlank()) {
        Spacer(Modifier.height(20.dp))

        Text(
            text = stringResource(R.string.or_by_email),
            fontWeight = Medium,
            color = DarkGray,
            fontSize = 18.sp
        )

        Spacer(Modifier.height(12.dp))

        Text(
            modifier = Modifier.clickable(
                interactionSource = remember {
                    MutableInteractionSource()
                },
                indication = null
            ) { openMail(email) },
            textDecoration = Underline,
            color = PrimaryColor,
            fontWeight = Medium,
            fontSize = 18.sp,
            text = email
        )
    }

    Spacer(Modifier.height(24.dp))
}

@Composable
private fun Themes(
    selectedTheme: AppealsTheme?,
    modifier: Modifier = Modifier,
    onSelect: (AppealsTheme?) -> Unit = {}
) {
    var showThemes by remember {
        mutableStateOf(false)
    }

    Card(
        border = BorderStroke(1.dp, PrimaryColor),
        modifier = modifier.fillMaxWidth(),
        colors = cardColors(Transparent),
        elevation = cardElevation(0.dp),
        onClick = { showThemes = true },
        shape = shapes.large,
    ) {
        Text(
            text = selectedTheme?.label
                ?.let { stringResource(it) }
                ?: stringResource(R.string.non_select),
            modifier = Modifier.padding(12.dp),
            fontWeight = SemiBold,
            color = selectedTheme
                ?.let { PrimaryColor }
                ?: LightGray,
            fontSize = 16.sp
        )
        DropdownMenu(
            onDismissRequest = { showThemes = false },
            expanded = showThemes,
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .background(White)
        ) {
            AppealsTheme.entries.forEach { theme ->
                DropdownMenuItem(
                    text = {
                        Text(
                            text = stringResource(theme.label),
                            color = if (selectedTheme == theme) {
                                PrimaryColor
                            } else {
                                DarkGray
                            },
                            fontWeight = Medium,
                            fontSize = 16.sp
                        )
                    },
                    onClick = {
                        showThemes = false
                        onSelect(theme)
                    }
                )
            }
        }
    }
}

@Composable
private fun Questions(
    selectedQuestion: AppealsQuestion?,
    modifier: Modifier = Modifier,
    onSelect: (AppealsQuestion?) -> Unit = {}
) {
    var showQuestions by remember {
        mutableStateOf(false)
    }

    Card(
        border = BorderStroke(1.dp, PrimaryColor),
        onClick = { showQuestions = true },
        modifier = modifier.fillMaxWidth(),
        colors = cardColors(Transparent),
        elevation = cardElevation(0.dp),
        shape = shapes.large
    ) {
        Text(
            modifier = Modifier.padding(12.dp),
            text = selectedQuestion?.question
                ?: stringResource(R.string.non_select),
            fontWeight = SemiBold,
            color = selectedQuestion
                ?.let { PrimaryColor }
                ?: LightGray,
            fontSize = 16.sp
        )
        DropdownMenu(
            onDismissRequest = { showQuestions = false },
            expanded = showQuestions,
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .background(White)
        ) {
            AppealsQuestion.entries.forEach { question ->
                DropdownMenuItem(
                    text = {
                        Text(
                            color = if (selectedQuestion == question) {
                                PrimaryColor
                            } else {
                                DarkGray
                            },
                            text = question.question,
                            fontWeight = Medium,
                            fontSize = 16.sp
                        )
                    },
                    onClick = {
                        showQuestions = false
                        onSelect(question)
                    }
                )
            }
        }
    }
}