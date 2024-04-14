package com.study.government.view.servants

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color.Companion.DarkGray
import androidx.compose.ui.graphics.Color.Companion.Gray
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.FontWeight.Companion.Medium
import androidx.compose.ui.text.input.KeyboardCapitalization.Companion.Sentences
import androidx.compose.ui.text.input.KeyboardType.Companion.Text
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.study.government.GovernmentApp
import com.study.government.R
import com.study.government.data.DataSource
import com.study.government.model.Destination.ADD_SERVANT
import com.study.government.model.Servant
import com.study.government.tools.getViewModel
import com.study.government.tools.neededStoragePermissions
import com.study.government.tools.openGallery
import com.study.government.ui.theme.PrimaryColor
import com.study.government.view.components.DefaultButton
import com.study.government.view.requests.TopBar
import com.study.government.viewmodel.MainViewModel

@Composable
@OptIn(ExperimentalPermissionsApi::class)
fun AddServantScreen(
    navHostController: NavHostController,
    servantId: String
) {
    val mainVm = getViewModel<MainViewModel>()

    var servant by remember {
        mutableStateOf(Servant())
    }

    var name by remember(servant) {
        mutableStateOf(servant.name)
    }

    var post by remember(servant) {
        mutableStateOf(servant.post)
    }

    var department by remember(servant) {
        mutableStateOf(servant.department)
    }

    var description by remember(servant) {
        mutableStateOf(servant.description)
    }

    var avatarPath by remember(servant) {
        mutableStateOf(servant.avatarPath)
    }

    var merits by remember(servant) {
        mutableStateOf(servant.merits)
    }

    val imagePicker = rememberLauncherForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri -> uri?.let { avatarPath = it.toString() } }

    val permissionStorageState = rememberMultiplePermissionsState(
        permissions = neededStoragePermissions
    ) { results -> if (results.all { it.value }) imagePicker.launch("image/*") }

    val context = LocalContext.current

    LaunchedEffect(Unit) {
        GovernmentApp.curScreen = ADD_SERVANT
        DataSource.getServantById(servantId.toLong())
            .onSuccess { servant = it }
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
                    if (servantId != "-1") {
                        R.string.edit_servant
                    } else {
                        R.string.add_servant
                    }
                ),
                color = PrimaryColor,
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp
            )

            Spacer(Modifier.height(40.dp))

            when {
                avatarPath.isNotBlank() -> Uri.parse(avatarPath)
                servant.avatarUrl.isNotBlank() -> servant.avatarUrl
                else -> null
            }?.let { image ->
                AsyncImage(
                    contentDescription = null,
                    modifier = Modifier
                        .size(140.dp)
                        .clip(CircleShape)
                        .align(CenterHorizontally)
                        .clickable {
                            permissionStorageState.openGallery(
                                imagePicker = imagePicker,
                                context = context
                            )
                        },
                    contentScale = ContentScale.Crop,
                    model = image
                )
            } ?: run {
                Box(
                    modifier = Modifier
                        .size(140.dp)
                        .background(PrimaryColor, CircleShape)
                        .clip(CircleShape)
                        .align(CenterHorizontally)
                        .clickable {
                            permissionStorageState.openGallery(
                                imagePicker = imagePicker,
                                context = context
                            )
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = ImageVector.vectorResource(
                            R.drawable.profile_placeholder
                        ),
                        contentDescription = null,
                        modifier = Modifier
                            .size(140.dp / 4 * 3)
                            .clip(CircleShape),
                        tint = White
                    )
                }
            }

            Spacer(Modifier.height(10.dp))

            BasicTextField(
                onValueChange = { name = it },
                textStyle = TextStyle(
                    fontWeight = Medium,
                    color = DarkGray,
                    fontSize = 14.sp
                ),
                value = name,
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
                        text = stringResource(R.string.name),
                        fontSize = 14.sp,
                        color = Gray
                    )
                    Spacer(Modifier.height(2.dp))
                    innerText()
                }
            }

            Spacer(Modifier.height(8.dp))

            BasicTextField(
                onValueChange = { department = it },
                textStyle = TextStyle(
                    fontWeight = Medium,
                    color = DarkGray,
                    fontSize = 14.sp
                ),
                value = department,
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
                        text = stringResource(R.string.departament),
                        fontSize = 14.sp,
                        color = Gray
                    )
                    Spacer(Modifier.height(2.dp))
                    innerText()
                }
            }

            Spacer(Modifier.height(8.dp))

            BasicTextField(
                onValueChange = { post = it },
                textStyle = TextStyle(
                    fontWeight = Medium,
                    color = DarkGray,
                    fontSize = 14.sp
                ),
                value = post,
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
                        text = stringResource(R.string.post),
                        fontSize = 14.sp,
                        color = Gray
                    )
                    Spacer(Modifier.height(2.dp))
                    innerText()
                }
            }

            Spacer(Modifier.height(8.dp))

            BasicTextField(
                onValueChange = { description = it },
                textStyle = TextStyle(
                    fontWeight = Medium,
                    color = DarkGray,
                    fontSize = 14.sp
                ),
                value = description,
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
                        text = stringResource(R.string.description),
                        fontSize = 14.sp,
                        color = Gray
                    )
                    Spacer(Modifier.height(2.dp))
                    innerText()
                }
            }

            Spacer(Modifier.height(8.dp))

            BasicTextField(
                onValueChange = { merits = it },
                textStyle = TextStyle(
                    fontWeight = Medium,
                    color = DarkGray,
                    fontSize = 14.sp
                ),
                value = merits,
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
                        text = stringResource(R.string.servant_merits),
                        fontSize = 14.sp,
                        color = Gray
                    )
                    Spacer(Modifier.height(2.dp))
                    innerText()
                }
            }

            Spacer(Modifier.height(12.dp))

            DefaultButton(
                modifier = Modifier.padding(horizontal = 16.dp),
                text = stringResource(R.string.save_servant),
                textColor = PrimaryColor,
                color = White
            ) {
                mainVm.addServant(
                    servant.copy(
                        avatarPath = avatarPath,
                        name = name,
                        post = post,
                        department = department,
                        description = description,
                        merits = merits
                    )
                )
                navHostController.navigateUp()
            }

            Spacer(Modifier.height(20.dp))
        }
    }
}