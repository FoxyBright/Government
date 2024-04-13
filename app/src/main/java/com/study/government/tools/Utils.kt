package com.study.government.tools

import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.Manifest.permission.READ_MEDIA_IMAGES
import android.Manifest.permission.WRITE_EXTERNAL_STORAGE
import android.net.Uri
import android.os.Build.VERSION.SDK_INT
import android.os.Build.VERSION_CODES.TIRAMISU
import androidx.activity.ComponentActivity
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel

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