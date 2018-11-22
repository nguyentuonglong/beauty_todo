package com.longnguyen.beautytodo.model

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.longnguyen.beautytodo.util.Constant
import java.util.*

@Entity(tableName = Constant.TASK_DETAIL_TABLE)
data class TaskDetail(
    @PrimaryKey @ColumnInfo(name = "task_detail_id") val id: String = UUID.randomUUID().toString(),
    @ColumnInfo(name = "task_id") val taskId: String?,
    @ColumnInfo(name = "task_name") val taskName: String?,
    @ColumnInfo(name = "isCompleted") var isCompleted: Boolean = false

)