package com.study.government.data

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.study.government.GovernmentApp
import com.study.government.model.New
import com.study.government.model.Request
import com.study.government.model.Servant

@Database(
    entities = [
        New::class,
        Request::class,
        Servant::class],
    version = 6
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun getDao(): Dao

    companion object {
        lateinit var dbDao: Dao

        fun initDatabase() {
            dbDao = Room.databaseBuilder(
                context = GovernmentApp.instance,
                klass = AppDatabase::class.java,
                name = "database"
            ).fallbackToDestructiveMigration()
                .build()
                .getDao()
        }
    }
}