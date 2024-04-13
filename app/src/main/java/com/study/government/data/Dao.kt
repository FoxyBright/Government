package com.study.government.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.REPLACE
import androidx.room.Query
import androidx.room.Update
import com.study.government.model.New
import com.study.government.model.Request
import com.study.government.model.Servant

@Dao
interface Dao {

    /// Новости ///////////////////////////////////////////////

    @Query("SELECT * FROM news")
    suspend fun getNews(): List<New>

    @Update
    suspend fun editNew(value: New)

    @Delete
    suspend fun deleteNew(value: New)

    @Insert(onConflict = REPLACE)
    suspend fun addNew(value: New)

    @Query("DELETE FROM news")
    suspend fun clearAllNews()

    @Query("SELECT * FROM news WHERE id = :newId")
    suspend fun getNewById(newId: Long): List<New>

    /// Запросы ///////////////////////////////////////////////

    @Query("SELECT * FROM requests")
    suspend fun getRequests(): List<Request>

    @Query("SELECT * FROM requests WHERE authorId = :userId")
    fun getUserRequests(userId: String): List<Request>

    @Query("SELECT * FROM requests WHERE id = :requestId")
    suspend fun getRequestById(requestId: Long): List<Request>

    @Query("DELETE FROM requests")
    suspend fun clearAllRequests()

    @Insert(onConflict = REPLACE)
    suspend fun addRequest(value: Request)

    @Update
    suspend fun editRequest(value: Request)

    @Delete
    suspend fun deleteRequest(value: Request)

    /// Служащие ///////////////////////////////////////////////

    @Query("SELECT * FROM servants")
    suspend fun getServants(): List<Servant>

    @Query("SELECT * FROM servants WHERE id = :servantId")
    suspend fun getServantById(servantId: Long): List<Servant>

    @Query("DELETE FROM servants")
    suspend fun clearAllServants()

    @Insert(onConflict = REPLACE)
    suspend fun addServant(value: Servant)

    @Update
    suspend fun editServant(value: Servant)

    @Delete
    suspend fun deleteServant(value: Servant)
}