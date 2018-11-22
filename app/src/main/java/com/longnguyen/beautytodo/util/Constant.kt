package com.longnguyen.beautytodo.util

object Constant {
    const val USER_TABLE = "user"
    const val TASK_TABLE = "task"
    const val TASK_DETAIL_TABLE = "task_detail"
    const val USER_NAME = "user_name"
    const val PASSWORD = "password"
    const val TASK_ID = "task_id"
    const val DB_VERSION = 1
    const val DB_NAME = "todo_db"
    const val QUERY_USER_BY_USER_NAME = "SELECT * FROM $USER_TABLE WHERE $USER_NAME = :name AND $PASSWORD = :password"
    const val QUERY_TASK_BY_USER_NAME = "SELECT * FROM $TASK_TABLE WHERE $USER_NAME = :name"
    const val QUERY_TASK_DETAIL_BY_TASK_ID = "SELECT * FROM $TASK_DETAIL_TABLE WHERE $TASK_ID = :taskId"

}