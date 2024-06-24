package com.study.government

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.study.government.navigation.Destination.NEWS
import java.util.Calendar

class GovernmentApp : Application() {

    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    companion object {
        lateinit var instance: Application

        val curTime get() = Calendar.getInstance().time.time
        var curScreen by mutableStateOf(NEWS)
    }
}