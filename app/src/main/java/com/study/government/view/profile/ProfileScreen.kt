package com.study.government.view.profile

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.ButtonDefaults.buttonColors
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults.cardColors
import androidx.compose.material3.CardDefaults.cardElevation
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.DarkGray
import androidx.compose.ui.graphics.Color.Companion.Gray
import androidx.compose.ui.graphics.Color.Companion.LightGray
import androidx.compose.ui.graphics.Color.Companion.Transparent
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale.Companion.Crop
import androidx.compose.ui.layout.ContentScale.Companion.FillHeight
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight.Companion.Medium
import androidx.compose.ui.text.font.FontWeight.Companion.Normal
import androidx.compose.ui.text.font.FontWeight.Companion.SemiBold
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.study.government.BuildConfig.VERSION_NAME
import com.study.government.GovernmentApp
import com.study.government.R
import com.study.government.data.Presets.additionalAppPresets
import com.study.government.model.Destination.LOGIN
import com.study.government.model.Destination.PROFILE
import com.study.government.tools.Navigation.navigateTo
import com.study.government.tools.WebWorker.openGosuslugi
import com.study.government.tools.WebWorker.openInWeb
import com.study.government.tools.getViewModel
import com.study.government.ui.theme.Background
import com.study.government.ui.theme.PrimaryColor
import com.study.government.ui.theme.RedColor
import com.study.government.view.components.DefaultButton
import com.study.government.viewmodel.MainViewModel

@Composable
fun ProfileScreen(navHostController: NavHostController) {
    val mainVm = getViewModel<MainViewModel>()

    var showDbSettings by remember {
        mutableStateOf(false)
    }

    LaunchedEffect(Unit) { GovernmentApp.curScreen = PROFILE }

    val showExitDialog = remember {
        mutableStateOf(false)
    }

    Crossfade(
        targetState = mainVm.pendingUser
                || mainVm.user == null,
        label = "Profile animation"
    ) { loading ->
        if (loading) {
            Box(
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
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Background)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = CenterHorizontally
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp, 20.dp),
                    verticalAlignment = CenterVertically
                ) {
                    AsyncImage(
                        modifier = Modifier
                            .size(100.dp)
                            .clip(CircleShape),
                        model = mainVm.user?.avatar,
                        contentDescription = null,
                        contentScale = Crop
                    )

                    Spacer(Modifier.width(16.dp))

                    Column(Modifier.weight(1f)) {
                        Text(
                            text = mainVm.user?.name ?: "",
                            fontWeight = Medium,
                            color = DarkGray,
                            fontSize = 20.sp
                        )

                        Spacer(Modifier.height(4.dp))

                        Text(
                            text = mainVm.user?.email ?: "",
                            fontWeight = Normal,
                            fontSize = 16.sp,
                            color = Gray
                        )
                    }
                }

                Spacer(Modifier.height(20.dp))

                mainVm.user?.phone?.let { phone ->
                    DataCard(stringResource(R.string.number), phone)
                }

                Spacer(Modifier.height(12.dp))

                mainVm.user?.snils?.let { snils ->
                    DataCard(stringResource(R.string.snils), snils)
                }

                Spacer(Modifier.height(22.dp))

                GosButton()

                Spacer(Modifier.height(8.dp))

                TryAlso()

                Spacer(Modifier.height(8.dp))

                Crossfade(
                    targetState = showDbSettings,
                    label = "Database control buttons animation"
                ) { showButtons ->
                    Column(
                        verticalArrangement = Arrangement.Bottom,
                        modifier = Modifier.weight(1f)
                    ) {
                        Spacer(Modifier.height(10.dp))
                        Spacer(Modifier.weight(1f))

                        if (showButtons) {
                            DefaultButton(
                                text = stringResource(R.string.set_database_presets),
                                modifier = Modifier.padding(horizontal = 16.dp),
                                textColor = PrimaryColor,
                                color = White
                            ) {
                                mainVm.setDatabasePresets()
                                showDbSettings = false
                            }

                            Spacer(Modifier.height(10.dp))

                            DefaultButton(
                                text = stringResource(R.string.clear_database),
                                modifier = Modifier.padding(horizontal = 16.dp),
                                textColor = RedColor,
                                color = White
                            ) {
                                mainVm.clearDatabase()
                                showDbSettings = false
                            }

                            Spacer(Modifier.height(10.dp))

                            DefaultButton(
                                text = stringResource(R.string.cancel),
                                modifier = Modifier.padding(horizontal = 16.dp),
                                color = RedColor
                            ) { showDbSettings = false }

                            Spacer(Modifier.height(10.dp))
                        } else {
                            DefaultButton(
                                text = stringResource(R.string.exit),
                                modifier = Modifier.padding(horizontal = 16.dp),
                                color = RedColor
                            ) { showExitDialog.value = true }
                        }

                        Spacer(Modifier.height(20.dp))

                        Text(
                            text = stringResource(R.string.version, VERSION_NAME),
                            modifier = Modifier
                                .fillMaxWidth()
                                .pointerInput(Unit) {
                                    detectTapGestures(onLongPress = {
                                        showDbSettings = !showDbSettings
                                    })
                                },
                            textAlign = TextAlign.Center,
                            fontWeight = Medium,
                            color = LightGray,
                            fontSize = 14.sp
                        )

                        Spacer(Modifier.height(16.dp))
                    }
                }
            }
        }
    }

    ExitDialog(navHostController, showExitDialog)
}

@Composable
private fun GosButton() {
    val context = LocalContext.current
    Card(
        modifier = Modifier.padding(horizontal = 16.dp),
        onClick = { openGosuslugi(context) },
        elevation = cardElevation(2.dp),
        colors = cardColors(White)
    ) {
        Row(
            verticalAlignment = CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Image(
                painter = painterResource(R.drawable.gos_logo),
                modifier = Modifier.size(40.dp),
                contentDescription = null,
            )
            Box(Modifier.weight(1f), Center) {
                Text(
                    text = stringResource(R.string.open_gosuslugi),
                    color = PrimaryColor,
                    fontWeight = Medium,
                    fontSize = 16.sp
                )
            }

        }
    }
}


@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun ExitDialog(
    navHostController: NavHostController,
    showErrorDialog: MutableState<Boolean>,
) {
    val mainVm = getViewModel<MainViewModel>()
    if (showErrorDialog.value) {
        BasicAlertDialog(
            onDismissRequest = {
                showErrorDialog.value = false
            }
        ) {
            Column(
                modifier = Modifier
                    .background(White, RoundedCornerShape(16.dp))
                    .padding(16.dp),
                horizontalAlignment = CenterHorizontally
            ) {
                Text(
                    text = stringResource(R.string.exit_dialog),
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    fontWeight = SemiBold,
                    fontSize = 24.sp,
                    color = Color.Black
                )

                Spacer(Modifier.height(30.dp))

                Text(
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    text = stringResource(R.string.exit_agree),
                    fontWeight = Normal,
                    fontSize = 16.sp,
                    color = DarkGray
                )

                Spacer(Modifier.height(50.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    TextButton(
                        onClick = { showErrorDialog.value = false },
                        colors = buttonColors(Transparent),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text(
                            text = stringResource(R.string.cancel),
                            fontWeight = SemiBold,
                            color = PrimaryColor,
                            fontSize = 18.sp
                        )
                    }

                    Spacer(Modifier.width(16.dp))

                    TextButton(
                        onClick = {
                            showErrorDialog.value = false
                            mainVm.logout()
                            navHostController.navigateTo(
                                dropScreens = true,
                                dest = LOGIN
                            )
                        },
                        colors = buttonColors(Transparent),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text(
                            fontWeight = SemiBold,
                            text = stringResource(R.string.ok),
                            color = PrimaryColor,
                            fontSize = 18.sp
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun DataCard(
    label: String,
    text: String,
) {
    Box(
        Modifier
            .padding(horizontal = 16.dp)
            .border(1.dp, LightGray, RoundedCornerShape(16.dp))
            .padding(12.dp)
    ) {
        Row(
            horizontalArrangement = SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = label,
                color = DarkGray,
                fontSize = 14.sp,
                fontWeight = Medium
            )

            Text(
                text = text,
                color = DarkGray,
                fontSize = 14.sp,
                fontWeight = Normal
            )
        }
    }
}

@Composable
private fun TryAlso() {
    val context = LocalContext.current
    Column {
        Spacer(Modifier.height(12.dp))

        Text(
            modifier = Modifier.padding(start = 18.dp),
            text = stringResource(R.string.try_also),
            fontWeight = SemiBold,
            color = PrimaryColor,
            fontSize = 16.sp
        )

        Spacer(Modifier.height(8.dp))

        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = spacedBy(8.dp)
        ) {
            item { Spacer(Modifier.width(8.dp)) }

            items(additionalAppPresets) { addApp ->
                Card(
                    shape = RoundedCornerShape(12.dp),
                    elevation = cardElevation(2.dp),
                    colors = cardColors(White),
                    modifier = Modifier,
                    onClick = {
                        openInWeb(context, addApp.link)
                    }
                ) {
                    Column(
                        horizontalAlignment = CenterHorizontally,
                        modifier = Modifier.padding(10.dp)
                    ) {
                        Image(
                            painter = painterResource(addApp.avatar),
                            contentDescription = null,
                            modifier = Modifier.size(50.dp),
                            contentScale = FillHeight
                        )

                        Spacer(Modifier.height(4.dp))

                        Text(
                            fontWeight = Medium,
                            text = addApp.name,
                            color = DarkGray,
                            fontSize = 16.sp
                        )
                    }
                }
            }

            item { Spacer(Modifier.width(8.dp)) }
        }
    }
}