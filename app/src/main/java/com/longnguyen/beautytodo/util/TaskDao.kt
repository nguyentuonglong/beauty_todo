package com.longnguyen.beautytodo.util

import android.arch.persistence.room.*
import com.longnguyen.beautytodo.model.Task
import io.reactivex.Single

@Dao
interface TaskDao {

    @Query(Constant.QUERY_TASK_BY_USER_NAME)
    fun getTaskByUserName(name: String): Single<List<Task>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTask(task: Task)

    @Delete
    fun delete(task: Task)

}