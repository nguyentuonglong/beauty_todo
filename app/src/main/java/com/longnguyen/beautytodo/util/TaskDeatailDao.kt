package com.longnguyen.beautytodo.util

import android.arch.persistence.room.*
import com.longnguyen.beautytodo.model.TaskDetail
import io.reactivex.Single

@Dao
interface TaskDeatailDao {

    @Query(Constant.QUERY_TASK_DETAIL_BY_TASK_ID)
    fun getTaskDetailByTaskId(taskId: String): Single<List<TaskDetail>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTaskDetail(taskDetail: TaskDetail)

    @Delete
    fun delete(taskDetail: TaskDetail)

    @Update
    fun update(taskDetail: TaskDetail)

}