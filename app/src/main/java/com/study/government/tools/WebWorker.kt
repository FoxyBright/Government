package com.study.government.tools

import android.content.Context
import android.content.Intent.FLAG_ACTIVITY_NO_HISTORY
import android.net.Uri
import androidx.browser.customtabs.CustomTabColorSchemeParams
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.content.ContextCompat.startActivity

object WebWorker {

    private const val RECOVERY_PASSWORD_URL = "https://esia.gosuslugi.ru/login/recovery"
    private const val REGISTRATION_URL = "https://esia.gosuslugi.ru/login/registration"
    private const val GOSUSLUGI_URL = "https://gosuslugi.ru"

    fun openRecoveryPassword(context: Context) {
        try {
            openInWeb(context, RECOVERY_PASSWORD_URL)
        } catch (e: Exception) {
            e.message.logE("error ope in web")
        }
    }

    fun openRegistration(context: Context) {
        try {
            openInWeb(context, REGISTRATION_URL)
        } catch (e: Exception) {
            e.message.logE("error ope in web")
        }
    }

    fun openGosuslugi(context: Context) {
        try {
            openInWeb(context, GOSUSLUGI_URL)
        } catch (e: Exception) {
            e.message.logE("error ope in web")
        }
    }

    fun openInWeb(context: Context, uri: String) {
        try {
            openInWeb(context, Uri.parse(uri))
        } catch (e: Exception) {
            e.message.logE("error ope in web")
        }
    }

    private fun openInWeb(context: Context, uri: Uri) {
        try {
            CustomTabsIntent.Builder().apply {
                setDefaultColorSchemeParams(tabColorScheme)
                setUrlBarHidingEnabled(true)
            }.build().run {
                intent.addFlags(FLAG_ACTIVITY_NO_HISTORY)
                intent.data = uri
                startActivity(context, intent, startAnimationBundle)
            }
        } catch (e: Exception) {
            e.message.logE("error ope in web")
        }
    }

    private val tabColorScheme
        get() = CustomTabColorSchemeParams.Builder().build()
}