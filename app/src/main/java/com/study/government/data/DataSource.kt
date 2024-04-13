package com.study.government.data

import com.study.government.data.AppDatabase.Companion.dbDao
import com.study.government.data.Presets.newsPresets
import com.study.government.data.Presets.requestsPresets
import com.study.government.data.Presets.servantsPresets
import com.study.government.data.Presets.usersPresets
import com.study.government.model.New
import com.study.government.model.Request
import com.study.government.tools.logE
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import kotlin.Result.Companion.failure
import kotlin.Result.Companion.success

object DataSource {

    private const val LOG_TAG = "Data Source"

    suspend fun setDbPresets() = withContext(IO) {
        clearDb()
        requestsPresets.forEach { dbDao.addRequest(it) }
        servantsPresets.forEach { dbDao.addServant(it) }
        newsPresets.forEach { dbDao.addNew(it) }
    }

    suspend fun clearDb() = withContext(IO) {
        dbDao.clearAllRequests()
        dbDao.clearAllServants()
        dbDao.clearAllNews()
    }

    suspend fun getNews() = withContext(IO) {
        success(dbDao.getNews())
    }

    suspend fun loginInGosuslugi(
        login: String,
        password: String,
    ) = withContext(IO) {
        delay(1000L)
        usersPresets.find {
            val snilsEquals = it.snils == login
            val phoneEquals = it.phone == login
            val emailEquals = it.email == login
            (snilsEquals || phoneEquals || emailEquals)
                    && it.password == password
        }?.let { success(it) } ?: run {
            failure(Throwable("Неверный логин или пароль".logE(LOG_TAG)))
        }
    }

    suspend fun getUser(token: String) = withContext(IO) {
        usersPresets.find { it.id == token }
            ?.let { success(it) }
            ?: run {
                failure(Throwable("Токен устарел, перепройдите авторизацию".logE(LOG_TAG)))
            }
    }

    suspend fun getNewById(id: Long) = withContext(IO) {
        dbDao.getNewById(id).firstOrNull()?.let { success(it) } ?: run {
            failure(Throwable("Не удалось найти новость по данному id".logE(LOG_TAG)))
        }
    }

    suspend fun getAllRequests() = withContext(IO) {
        success(dbDao.getRequests())
    }

    suspend fun getRequestById(requestId: Long) = withContext(IO) {
        dbDao.getRequestById(requestId).firstOrNull()?.let { success(it) } ?: run {
            failure(Throwable("Не удалось найти запрос по данному id".logE(LOG_TAG)))
        }
    }

    suspend fun getUserRequests(userId: String?) = withContext(IO) {
        userId?.let { id ->
            success(dbDao.getUserRequests(id).sortedBy { it.date })
        } ?: run {
            failure(Throwable("id Пользователя указан как null".logE(LOG_TAG)))
        }
    }

    suspend fun editRequest(request: Request) = withContext(IO) {
        dbDao.editRequest(request)
    }

    suspend fun addRequest(request: Request) = withContext(IO) {
        dbDao.addRequest(request)
    }

    suspend fun addNew(new: New) = withContext(IO) {
        dbDao.addNew(new)
    }

    suspend fun deleteRequest(request: Request) = withContext(IO) {
        dbDao.deleteRequest(request)
    }

    suspend fun getAllServants() = withContext(IO) {
        success(dbDao.getServants())
    }

    suspend fun getServantById(servantId: Long) = withContext(IO) {
        dbDao.getServantById(servantId).firstOrNull()?.let { success(it) } ?: run {
            failure(Throwable("Не удалось найти служащего по данному id".logE(LOG_TAG)))
        }
    }
}