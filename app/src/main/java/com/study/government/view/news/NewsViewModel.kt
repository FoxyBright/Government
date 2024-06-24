package com.study.government.view.news

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import com.study.government.model.news.NewsCategory

class NewsViewModel(
    application: Application
) : AndroidViewModel(application) {
    var newsCategory by mutableStateOf<NewsCategory?>(null)
}