package com.ergegananputra.aplikasikpu.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.ergegananputra.aplikasikpu.domain.dao.DataPesertaDao
import com.ergegananputra.aplikasikpu.domain.entities.room.DataPeserta

@Database(
    entities = [
        DataPeserta::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun formDao(): DataPesertaDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatbase(context: Context) : AppDatabase {
            return INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "kpu_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { INSTANCE = it }
            }
        }
    }

}