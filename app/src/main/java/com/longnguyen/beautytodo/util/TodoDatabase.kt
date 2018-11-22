package com.longnguyen.beautytodo.util

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import com.longnguyen.beautytodo.app.App
import com.longnguyen.beautytodo.model.Task
import com.longnguyen.beautytodo.model.TaskDetail
import com.longnguyen.beautytodo.model.User

/**
 * The Room Database that contains the Wallet table.
 */
@Database(entities = [(User::class), (Task::class), (TaskDetail::class)], version = Constant.DB_VERSION)
abstract class TodoDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao
    abstract fun taskDao(): TaskDao
    abstract fun taskDetail(): TaskDeatailDao


    companion object {

        private var INSTANCE: TodoDatabase? = null

        private val lock = Any()

        fun getInstance(): TodoDatabase {
            synchronized(lock) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                        App.context.applicationContext,
                        TodoDatabase::class.java, Constant.DB_NAME
                    ).allowMainThreadQueries().build()
                }
                return INSTANCE!!
            }
        }
    }

}