package com.study.government.tools

import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.Manifest.permission.READ_MEDIA_IMAGES
import android.content.Context
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.net.Uri
import android.os.Build.VERSION.SDK_INT
import android.os.Build.VERSION_CODES.TIRAMISU
import android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS
import androidx.activity.ComponentActivity
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.MultiplePermissionsState

@Composable
inline fun <reified T: AndroidViewModel> getViewModel() =
    viewModel<T>(LocalContext.current as ComponentActivity)

inline fun <reified T: AndroidViewModel> ViewModelStoreOwner.getViewModel() =
    ViewModelProvider(this)[T::class.java]

val neededStoragePermissions
    get() = ArrayList<String>().apply {
        if (SDK_INT >= TIRAMISU) {
            add(READ_MEDIA_IMAGES)
        } else {
            add(READ_EXTERNAL_STORAGE)
        }
    }.toList()

fun getSettingsUri(packageName: String): Uri =
    Uri.fromParts("package", packageName, null)

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