package com.longnguyen.beautytodo.model

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.longnguyen.beautytodo.util.Constant
import java.util.*

@Entity(tableName = Constant.TASK_TABLE)
data class Task(
    @PrimaryKey @ColumnInfo(name = "task_id") val id: String = UUID.randomUUID().toString(),
    @ColumnInfo(name = "user_name") val userName: String?,
    @ColumnInfo(name = "task_name") val taskName: String?
)