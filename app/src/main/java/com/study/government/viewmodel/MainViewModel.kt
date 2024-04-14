package com.study.government.viewmodel

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.study.government.data.DataSource
import com.study.government.data.DataSource.clearDb
import com.study.government.data.DataSource.editRequest
import com.study.government.data.DataSource.getAllRequests
import com.study.government.data.DataSource.getAllServants
import com.study.government.data.DataSource.getNews
import com.study.government.data.DataSource.getUser
import com.study.government.data.DataSource.getUserRequests
import com.study.government.data.DataSource.loginInGosuslugi
import com.study.government.data.DataSource.setDbPresets
import com.study.government.model.New
import com.study.government.model.NewsCategory
import com.study.government.model.Request
import com.study.government.model.RequestStatus
import com.study.government.model.RequestTheme
import com.study.government.model.Servant
import com.study.government.model.User
import com.study.government.model.UserRole.ADMIN
import com.study.government.tools.SharedPrefs
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MainViewModel(
    application: Application,
) : AndroidViewModel(application) {

    private val prefs = SharedPrefs(application)

    var newsCategory by mutableStateOf<NewsCategory?>(null)
    val news = mutableStateListOf<New>()
    var user by mutableStateOf<User?>(null)

    var firstUploadRequests by mutableStateOf(true)
    var pendingRequests by mutableStateOf(false)
    var pendingLogin by mutableStateOf(false)
    var pendingNews by mutableStateOf(false)
    var pendingUser by mutableStateOf(false)

    var refreshRequests by mutableStateOf(false)
    var refreshNews by mutableStateOf(false)

    val servants = mutableStateListOf<Servant>()
    var pendingServants by mutableStateOf(false)
    var refreshServants by mutableStateOf(false)

    private val _requestStatusFilters =
        MutableStateFlow<List<RequestStatus>>(emptyList())
    val requestStatusFilters =
        _requestStatusFilters.asStateFlow()

    private val _requestThemeFilters =
        MutableStateFlow<List<RequestTheme>>(emptyList())
    val requestThemeFilters =
        _requestThemeFilters.asStateFlow()

    fun changeThemeFilter(theme: RequestTheme) {
        val list = _requestThemeFilters.value
        if (list.contains(theme)) {
            _requestThemeFilters.value = list - theme
        } else {
            _requestThemeFilters.value = list + theme
        }
    }

    private val _requests = MutableStateFlow<List<Request>>(emptyList())
    val requests = _requests.asStateFlow()

    fun changeStatusFilter(theme: RequestStatus) {
        val list = _requestStatusFilters.value
        if (list.contains(theme)) {
            _requestStatusFilters.value = list - theme
        } else {
            _requestStatusFilters.value = list + theme
        }
    }

    fun clearFilters() {
        _requestThemeFilters.value = emptyList()
        _requestStatusFilters.value = emptyList()
    }

    fun login(
        login: String,
        password: String,
        onSuccess: () -> Unit = {},
        onFailure: (Throwable) -> Unit = {},
    ) {
        pendingLogin = true
        viewModelScope.launch {
            loginInGosuslugi(login, password)
                .onFailure { onFailure(it) }
                .onSuccess {
                    prefs.saveToken(it.id)
                    user = it
                    onSuccess()
                }
            pendingLogin = false
        }
    }

    fun uploadNews() {
        pendingNews = true
        viewModelScope.launch {
            getNews().onSuccess {
                news.clear()
                news.addAll(it)
            }
            pendingNews = false
            refreshNews = false
        }
    }

    fun updateUser() {
        pendingUser = true
        viewModelScope.launch {
            getUser(prefs.authToken)
                .onSuccess { user = it }
            pendingUser = false
        }
    }

    fun uploadRequests() {
        pendingRequests = true
        firstUploadRequests = false
        viewModelScope.launch {
            val list = mutableStateListOf<Request>()
            when (user?.role) {
                ADMIN -> getAllRequests()
                    .onSuccess { list.addAll(it) }

                else -> getUserRequests(user?.id)
                    .onSuccess { list.addAll(it) }
            }
            _requests.value = list
            pendingRequests = false
            refreshRequests = false
        }
    }

    fun logout() {
        news.clear()
        user = null
        newsCategory = null
        pendingLogin = false
        pendingNews = false
        pendingUser = false
        prefs.clearToken()
    }

    fun setDatabasePresets() {
        viewModelScope.launch {
            setDbPresets()
            uploadNews()
            uploadRequests()
            uploadServants()
        }
    }

    fun clearDatabase() {
        viewModelScope.launch {
            clearDb()
            uploadNews()
            uploadRequests()
            uploadServants()
        }
    }

    fun answerRequest(request: Request) {
        viewModelScope.launch {
            editRequest(request)
            uploadRequests()
        }
    }

    fun addRequest(request: Request) {
        viewModelScope.launch {
            DataSource.addRequest(request)
            uploadRequests()
        }
    }

    fun deleteRequest(request: Request) {
        viewModelScope.launch {
            DataSource.deleteRequest(request)
            uploadRequests()
        }
    }

    fun addNew(new: New) {
        viewModelScope.launch {
            DataSource.addNew(new)
            uploadNews()
        }
    }

    fun deleteNew(new: New) {
        viewModelScope.launch {
            DataSource.deleteNew(new)
            uploadNews()
        }
    }

    fun deleteServant(servant: Servant) {
        viewModelScope.launch {
            DataSource.deleteServant(servant)
            uploadServants()
        }
    }

    fun uploadServants() {
        pendingServants = true
        viewModelScope.launch {
            getAllServants().onSuccess {
                servants.clear()
                servants.addAll(it)
            }
            pendingServants = false
            refreshServants = false
        }
    }
}