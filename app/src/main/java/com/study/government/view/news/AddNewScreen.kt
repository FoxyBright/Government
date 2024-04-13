package com.study.government.view.news

import android.content.Context
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.net.Uri
import android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
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
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color.Companion.DarkGray
import androidx.compose.ui.graphics.Color.Companion.Gray
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale.Companion.Crop
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.FontWeight.Companion.Medium
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.MultiplePermissionsState
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.study.government.GovernmentApp
import com.study.government.R
import com.study.government.data.DataSource.getNewById
import com.study.government.model.Destination.ADD_NEW
import com.study.government.model.New
import com.study.government.tools.getSettingsUri
import com.study.government.tools.getViewModel
import com.study.government.tools.neededStoragePermissions
import com.study.government.ui.theme.PrimaryColor
import com.study.government.view.components.DefaultButton
import com.study.government.view.requests.TopBar
import com.study.government.viewmodel.MainViewModel
import java.io.File

@Composable
@OptIn(ExperimentalPermissionsApi::class)
fun AddNewScreen(
    navHostController: NavHostController,
    newId: String
) {
    val mainVm = getViewModel<MainViewModel>()
    val context = LocalContext.current

    var new by remember {
        mutableStateOf(New())
    }

    var imagePath by remember(new) {
        mutableStateOf(new.imagePath)
    }

    var title by remember(new) {
        mutableStateOf(new.title)
    }

    var link by remember(new) {
        mutableStateOf(new.link)
    }

    var text by remember(new) {
        mutableStateOf(new.text)
    }

    val imagePicker = rememberLauncherForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri -> uri?.let { imagePath = it.toString() } }

    val permissionStorageState = rememberMultiplePermissionsState(
        permissions = neededStoragePermissions
    ) { results -> if (results.all { it.value }) imagePicker.launch("image/*") }

    LaunchedEffect(Unit) {
        GovernmentApp.curScreen = ADD_NEW
        getNewById(newId.toLong())
            .onSuccess { new = it }
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
                    .align(Alignment.CenterHorizontally),
                text = stringResource(
                    if (newId != "-1") {
                        R.string.edit_new
                    } else {
                        R.string.add_new
                    }
                ),
                color = PrimaryColor,
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp
            )

            Spacer(Modifier.height(40.dp))

            when {
                imagePath.isNotBlank() -> Uri.parse(imagePath)
                new.imageUrl.isNotBlank() -> new.imageUrl
                else -> null
            }?.let { image ->
                AsyncImage(
                    contentDescription = null,
                    contentScale = Crop,
                    model = image,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(180.dp)
                        .padding(horizontal = 16.dp)
                        .border(1.dp, Gray, RoundedCornerShape(16.dp))
                        .clip(RoundedCornerShape(16.dp))
                        .clickable {
                            permissionStorageState.openGallery(
                                imagePicker = imagePicker,
                                context = context
                            )
                        }
                )
            } ?: run {
                Box(
                    contentAlignment = Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(180.dp)
                        .padding(horizontal = 16.dp)
                        .border(1.dp, Gray, RoundedCornerShape(16.dp))
                        .clip(RoundedCornerShape(16.dp))
                        .clickable {
                            permissionStorageState.openGallery(
                                imagePicker = imagePicker,
                                context = context
                            )
                        }
                ) {
                    Icon(
                        imageVector = ImageVector.vectorResource(
                            R.drawable.ic_image_placeholder
                        ),
                        contentDescription = null,
                        modifier = Modifier.size(52.dp),
                        tint = PrimaryColor
                    )
                }
            }

            Spacer(Modifier.height(10.dp))

            BasicTextField(
                onValueChange = { title = it },
                textStyle = TextStyle(
                    fontWeight = Medium,
                    color = DarkGray,
                    fontSize = 14.sp
                ),
                value = title,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 12.dp, top = 6.dp)
                    .padding(horizontal = 16.dp)
                    .border(1.dp, Gray, RoundedCornerShape(16.dp))
                    .padding(10.dp, 6.dp),
                keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.Sentences,
                    keyboardType = KeyboardType.Text
                )
            ) { innerText ->
                Column {
                    Text(
                        text = stringResource(R.string.title),
                        fontSize = 14.sp,
                        color = Gray
                    )
                    Spacer(Modifier.height(2.dp))
                    innerText()
                }
            }

            Spacer(Modifier.height(8.dp))

            BasicTextField(
                onValueChange = { text = it },
                textStyle = TextStyle(
                    fontWeight = Medium,
                    color = DarkGray,
                    fontSize = 14.sp
                ),
                value = text,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 12.dp, top = 6.dp)
                    .padding(horizontal = 16.dp)
                    .border(1.dp, Gray, RoundedCornerShape(16.dp))
                    .padding(10.dp, 6.dp),
                keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.Sentences,
                    keyboardType = KeyboardType.Text
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
                onValueChange = { link = it },
                textStyle = TextStyle(
                    fontWeight = Medium,
                    color = PrimaryColor,
                    fontSize = 14.sp
                ),
                value = link,
                maxLines = 1,
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 12.dp, top = 6.dp)
                    .padding(horizontal = 16.dp)
                    .border(1.dp, Gray, RoundedCornerShape(16.dp))
                    .padding(10.dp, 6.dp),
                keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.Sentences,
                    keyboardType = KeyboardType.Text
                )
            ) { innerText ->
                Column {
                    Text(
                        text = stringResource(R.string.add_link),
                        fontSize = 14.sp,
                        color = Gray
                    )
                    Spacer(Modifier.height(2.dp))
                    innerText()
                }
            }

            Spacer(Modifier.height(8.dp))

            DefaultButton(
                modifier = Modifier.padding(horizontal = 16.dp),
                text = stringResource(R.string.create_new),
                textColor = PrimaryColor,
                color = White
            ) {
                mainVm.addNew(
                    new.copy(
                        title = title,
                        text = text,
                        imagePath = imagePath,
                        link = link
                    )
                )
                navHostController.navigateUp()
            }

            Spacer(Modifier.height(20.dp))
        }
    }
}

@OptIn(ExperimentalPermissionsApi::class)
fun MultiplePermissionsState.openGallery(
    imagePicker: ManagedActivityResultLauncher<String, Uri?>,
    context: Context
) {
    when {
        allPermissionsGranted -> imagePicker.launch("image/*")

        shouldShowRationale -> context.startActivity(
            Intent(
                ACTION_APPLICATION_DETAILS_SETTINGS
            ).apply {
                addFlags(FLAG_ACTIVITY_NEW_TASK)
                data = getSettingsUri(context.packageName)
            }
        )

        else -> launchMultiplePermissionRequest()
    }
}