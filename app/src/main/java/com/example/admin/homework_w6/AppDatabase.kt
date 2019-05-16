package com.example.admin.homework_w6

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import com.example.admin.homework_w6.DAO.TaskDAO
import com.example.admin.homework_w6.DAO.UserDAO
import com.example.admin.homework_w6.Entity.Task
import com.example.admin.homework_w6.Entity.User

@Database(entities = arrayOf(Task::class, User::class), version = 1)
    abstract class AppDatabase : RoomDatabase() {
        abstract fun taskDao() : TaskDAO
        abstract fun userDao() : UserDAO
        companion object {
            @Volatile
            private var instance: AppDatabase? = null
            private val LOCK = Any()

            // Singleton pattern
            operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
                instance ?: buildDatabase(context).also { instance = it }
            }

            private fun buildDatabase(context: Context) = Room.databaseBuilder(
                    context,
                    AppDatabase::class.java, DATABASE_NAME
            ).allowMainThreadQueries()
                    .build()
        }
    }