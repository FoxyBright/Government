package com.study.government

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.study.government.model.Destination
import com.study.government.model.Destination.LOGIN
import com.study.government.model.Destination.NEWS
import com.study.government.tools.SharedPrefs
import java.util.Calendar

class GovernmentApp: Application() {
    
    override fun onCreate() {
        super.onCreate()
        prefs = SharedPrefs(applicationContext)
        instance = this
        startDest = if (prefs.hasToken) NEWS else LOGIN
        curScreen = startDest
    }
    
    companion object {
        
        lateinit var startDest: Destination
        lateinit var instance: Application
        lateinit var prefs: SharedPrefs
        
        val curTime get() = Calendar.getInstance().time.time
        var curScreen by mutableStateOf(LOGIN)
    }
}