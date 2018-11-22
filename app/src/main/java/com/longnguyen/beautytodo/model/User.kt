package com.longnguyen.beautytodo.model

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.longnguyen.beautytodo.util.Constant
import java.util.*

@Entity(tableName = Constant.USER_TABLE)
data class User(
    @PrimaryKey @ColumnInfo(name = "user_id") val id: String = UUID.randomUUID().toString(),
    @ColumnInfo(name = "user_name") val name: String?,
    @ColumnInfo(name = "password") val password: String?
)