package com.study.government.view.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.study.government.data.AppDatabase.Companion.initDatabase
import com.study.government.tools.getViewModel
import com.study.government.ui.theme.GovernmentTheme
import com.study.government.viewmodel.MainViewModel


class MainActivity : ComponentActivity() {

    private lateinit var navHostController: NavHostController
    private lateinit var mainVm: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initDatabase()
        mainVm = getViewModel()
        setContent {
            GovernmentTheme {
                navHostController = rememberNavController()
                MainScreen(navHostController)
            }
        }
    }
}