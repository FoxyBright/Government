package com.study.government.tools

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.Intent.ACTION_VIEW
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.content.Intent.FLAG_ACTIVITY_NO_HISTORY
import android.net.Uri
import android.net.Uri.fromParts
import android.net.Uri.parse
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import androidx.browser.customtabs.CustomTabColorSchemeParams
import androidx.browser.customtabs.CustomTabsIntent
import com.study.government.GovernmentApp.Companion.instance
import com.study.government.R

object UriWorker {

    fun openMail(
        email: String = "",
        text: String = "",
        subject: String = instance.getString(R.string.appeal)
    ) {
        instance.openApp(
            uri = parse("mailto:?subject=$subject&to=$email&body=$text"),
            applicationName = instance.getString(R.string.mail)
        )
    }

    fun openPhone(phone: String) {
        val intent = Intent(Intent.ACTION_DIAL)
        intent.flags = FLAG_ACTIVITY_NEW_TASK
        intent.data = fromParts("tel", phone, null)
        instance.startActivity(intent)
    }

    fun openMap(address: String) {
        instance.openApp(
            uri = parse("geo:0,0?q=$address"),
            applicationName = instance.getString(R.string.maps)
        )
    }

    private fun Context.openApp(uri: Uri, applicationName: String) {
        val intent = Intent(ACTION_VIEW)
        intent.addFlags(FLAG_ACTIVITY_NEW_TASK)
        intent.setData(uri)

        try {
            startActivity(intent)
        } catch (e: ActivityNotFoundException) {
            makeToast(getString(R.string.missing_application, applicationName))
        }
    }

    fun openLink(uri: String) {
        try {
            CustomTabsIntent.Builder().apply {
                setDefaultColorSchemeParams(
                    CustomTabColorSchemeParams
                        .Builder()
                        .build()
                )
                setUrlBarHidingEnabled(true)
            }.build().run {
                intent.addFlags(FLAG_ACTIVITY_NO_HISTORY)
                intent.addFlags(FLAG_ACTIVITY_NEW_TASK)
                intent.data = parse(uri)
                instance.startActivity(intent, startAnimationBundle)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun Context.makeToast(text: String, duration: Int = LENGTH_SHORT) {
        Toast.makeText(this, text, duration).show()
    }
}
