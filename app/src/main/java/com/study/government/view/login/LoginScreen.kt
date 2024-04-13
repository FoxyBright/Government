package com.study.government.view.login

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.*
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement.End
import androidx.compose.foundation.layout.IntrinsicSize.Max
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.material3.ButtonDefaults.buttonColors
import androidx.compose.material3.CardDefaults.cardColors
import androidx.compose.material3.CardDefaults.cardElevation
import androidx.compose.material3.SheetValue.Hidden
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment.Companion.BottomCenter
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.DarkGray
import androidx.compose.ui.graphics.Color.Companion.Gray
import androidx.compose.ui.graphics.Color.Companion.LightGray
import androidx.compose.ui.graphics.Color.Companion.Red
import androidx.compose.ui.graphics.Color.Companion.Transparent
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.graphics.StrokeCap.Companion.Round
import androidx.compose.ui.layout.ContentScale.Companion.FillBounds
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.*
import androidx.compose.ui.text.font.FontWeight.Companion.Medium
import androidx.compose.ui.text.font.FontWeight.Companion.Normal
import androidx.compose.ui.text.font.FontWeight.Companion.SemiBold
import androidx.compose.ui.text.input.KeyboardType.Companion.Text
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation.Companion.None
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.study.government.GovernmentApp
import com.study.government.R
import com.study.government.model.Destination.LOGIN
import com.study.government.model.Destination.NEWS
import com.study.government.tools.Navigation.navigateTo
import com.study.government.tools.WebWorker.openRecoveryPassword
import com.study.government.tools.WebWorker.openRegistration
import com.study.government.tools.getViewModel
import com.study.government.ui.theme.Background
import com.study.government.ui.theme.PrimaryColor
import com.study.government.viewmodel.MainViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun LoginScreen(navHostController: NavHostController) {
    LaunchedEffect(Unit) { GovernmentApp.curScreen = LOGIN }
    
    val scaffoldState = rememberBottomSheetScaffoldState(
        rememberStandardBottomSheetState(
            skipHiddenState = false,
            initialValue = Hidden
        ),
    )
    
    BottomSheetScaffold(
        sheetContent = { LoginBS(navHostController) },
        scaffoldState = scaffoldState,
        sheetContainerColor = White,
        sheetPeekHeight = 0.dp
    ) {
        Image(
            painter = painterResource(R.drawable.login_background),
            modifier = Modifier.fillMaxSize(),
            contentScale = FillBounds,
            contentDescription = null
        )
        
        Column(
            horizontalAlignment = CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            Text(
                text = stringResource(R.string.app_name),
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                fontWeight = SemiBold,
                fontSize = 40.sp,
                color = White
            )
            Spacer(Modifier.height(40.dp))
            LoginCard(scaffoldState)
        }
        
        Box(Modifier.fillMaxSize(), BottomCenter) {
            Text(
                text = stringResource(R.string.sponsors),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 30.dp),
                textAlign = TextAlign.Center,
                fontWeight = Normal,
                fontSize = 12.sp,
                color = White
            )
        }
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun LoginCard(scaffoldState: BottomSheetScaffoldState) {
    val scope = rememberCoroutineScope()
    Card(
        modifier = Modifier.padding(horizontal = 32.dp),
        elevation = cardElevation(4.dp),
        colors = cardColors(White)
    ) {
        Column(
            horizontalAlignment = CenterHorizontally,
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = stringResource(R.string.welcome),
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                color = DarkGray,
                fontSize = 16.sp,
            )
            
            Spacer(Modifier.height(32.dp))
            
            TextButton(
                onClick = {
                    scope.launch {
                        scaffoldState.bottomSheetState.expand()
                    }
                },
                shape = RoundedCornerShape(12.dp),
                colors = buttonColors(PrimaryColor)
            ) {
                Text(
                    text = stringResource(R.string.enter_with_gosuslugi),
                    modifier = Modifier.padding(6.dp),
                    textAlign = TextAlign.Center,
                    fontWeight = Medium,
                    fontSize = 16.sp,
                    color = White
                )
            }
        }
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun LoginBS(navHostController: NavHostController) {
    val mainVm = getViewModel<MainViewModel>()
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    
    val login = remember {
        mutableStateOf("")
    }
    val password = remember {
        mutableStateOf("")
    }
    
    var loginError by remember {
        mutableStateOf(false)
    }
    var passwordError by remember {
        mutableStateOf(false)
    }
    
    var showErrorDialog by remember {
        mutableStateOf(false)
    }
    var errorDialogMessage by remember {
        mutableStateOf("")
    }
    
    Column(
        horizontalAlignment = CenterHorizontally,
        modifier = Modifier
            .background(Background)
            .padding(horizontal = 32.dp)
    ) {
        Image(
            painter = painterResource(R.drawable.gosuslugi_logo),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
        )
        
        Text(
            text = stringResource(R.string.sponsors_description),
            textAlign = TextAlign.Center,
            fontWeight = Normal,
            fontSize = 12.sp,
            color = Gray
        )
        
        Spacer(Modifier.height(40.dp))
        
        Text(
            text = stringResource(R.string.enter),
            fontWeight = Medium,
            fontSize = 28.sp,
            color = DarkGray
        )
        
        Spacer(Modifier.height(2.dp))
        
        Text(
            text = stringResource(R.string.enter_description),
            textAlign = TextAlign.Center,
            fontWeight = Normal,
            fontSize = 14.sp,
            color = DarkGray
        )
        
        Spacer(Modifier.height(40.dp))
        
        LoginTextField(
            label = stringResource(R.string.login),
            isError = loginError,
            value = login
        )
        
        Spacer(Modifier.height(12.dp))
        
        LoginTextField(
            label = stringResource(R.string.password),
            isError = passwordError,
            value = password,
            hideValue = true
        )
        
        Spacer(Modifier.height(22.dp))
        
        Crossfade(
            targetState = mainVm.pendingLogin,
            modifier = Modifier.height(Max),
            label = "Login animation"
        ) { loading ->
            if (loading) {
                Box(Modifier.fillMaxSize(), Center) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(60.dp),
                        color = PrimaryColor,
                        strokeWidth = 4.dp,
                        strokeCap = Round
                    )
                }
            } else {
                TextButton(
                    onClick = {
                        if (login.value.isBlank()) {
                            scope.launch {
                                loginError = true
                                delay(2000L)
                                loginError = false
                            }
                            if (password.value.isNotBlank()) {
                                return@TextButton
                            }
                        }
                        if (password.value.isBlank()) {
                            scope.launch {
                                passwordError = true
                                delay(2000L)
                                passwordError = false
                            }
                            return@TextButton
                        }
                        mainVm.login(
                            password = password.value,
                            login = login.value,
                            onSuccess = {
                                navHostController.navigateTo(
                                    dropScreens = true,
                                    dest = NEWS
                                )
                            },
                            onFailure = { error ->
                                error.localizedMessage?.let {
                                    errorDialogMessage = it
                                }
                                showErrorDialog = true
                            }
                        )
                    },
                    colors = buttonColors(PrimaryColor),
                    shape = RoundedCornerShape(4.dp),
                    modifier = Modifier
                        .fillMaxSize()
                        .height(60.dp)
                ) {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = stringResource(R.string.enter_gosuslugi),
                        textAlign = TextAlign.Center,
                        fontWeight = Medium,
                        fontSize = 18.sp,
                        color = White
                    )
                }
            }
        }
        
        Spacer(Modifier.height(12.dp))
        
        Text(
            text = stringResource(R.string.forgot_password),
            textAlign = TextAlign.Center,
            color = PrimaryColor,
            fontWeight = Medium,
            fontSize = 14.sp,
            modifier = Modifier.clickable(
                interactionSource = remember {
                    MutableInteractionSource()
                },
                indication = null
            ) { openRecoveryPassword(context) }
        )
        
        Spacer(Modifier.height(30.dp))
        
        val registration = stringResource(R.string.registration)
        val registrationText = buildAnnotatedString {
            withStyle(SpanStyle(PrimaryColor)) {
                append(registration)
                append(" ")
            }
            append(stringResource(R.string.registration_description))
            
            val text = remember { toAnnotatedString().text }
            val regIndex = remember { text.indexOf(registration) }
            
            addStringAnnotation(
                end = regIndex + registration.length,
                annotation = registration,
                tag = registration,
                start = regIndex
            )
        }
        
        ClickableText(
            text = registrationText,
            onClick = {
                registrationText
                    .getStringAnnotations(registration, it, it)
                    .firstOrNull()?.let { openRegistration(context) }
            },
            style = TextStyle(
                textAlign = TextAlign.Center,
                fontWeight = Medium,
                fontSize = 16.sp,
                color = Gray
            )
        )
        
        Spacer(Modifier.height(60.dp))
    }
    
    if (showErrorDialog) {
        BasicAlertDialog(
            onDismissRequest = {
                showErrorDialog = false
            }
        ) {
            Column(
                modifier = Modifier
                    .background(White, RoundedCornerShape(16.dp))
                    .padding(16.dp),
                horizontalAlignment = CenterHorizontally
            ) {
                Text(
                    text = stringResource(R.string.error_title),
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    fontWeight = SemiBold,
                    fontSize = 24.sp,
                    color = Black
                )
                Spacer(Modifier.height(10.dp))
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    text = errorDialogMessage,
                    fontWeight = Normal,
                    fontSize = 16.sp,
                    color = DarkGray
                )
                Spacer(Modifier.height(32.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = End
                ) {
                    TextButton(
                        onClick = { showErrorDialog = false },
                        colors = buttonColors(Transparent),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text(
                            text = stringResource(R.string.ok),
                            fontWeight = SemiBold,
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
private fun LoginTextField(
    label: String,
    value: MutableState<String>,
    modifier: Modifier = Modifier,
    hideValue: Boolean = false,
    isError: Boolean = false,
) {
    var focused by remember {
        mutableStateOf(false)
    }
    Box(
        modifier = modifier
            .height(Max)
            .border(
                shape = RoundedCornerShape(4.dp),
                color = when {
                    isError -> Red
                    focused -> Gray
                    else -> LightGray
                },
                width = 2.dp
            )
    ) {
        BasicTextField(
            onValueChange = { value.value = it },
            value = value.value,
            textStyle = TextStyle(
                fontWeight = Medium,
                fontSize = 18.sp,
                color = Black
            ),
            singleLine = true,
            maxLines = 1,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 12.dp, top = 6.dp)
                .padding(horizontal = 12.dp)
                .onFocusChanged {
                    focused = it.isFocused
                },
            keyboardOptions = KeyboardOptions(
                keyboardType = Text
            ),
            visualTransformation = if (hideValue) {
                PasswordVisualTransformation()
            } else {
                None
            }
        ) { innerText ->
            Column {
                Text(
                    color = if (isError) Red else Gray,
                    fontSize = 14.sp,
                    text = label
                )
                Spacer(Modifier.height(2.dp))
                innerText()
            }
        }
    }
}