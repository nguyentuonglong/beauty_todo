package com.longnguyen.beautytodo.util

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.longnguyen.beautytodo.model.User
import io.reactivex.Single

@Dao
interface UserDao {

    @Query(Constant.QUERY_USER_BY_USER_NAME)
    fun getUser(name: String, password: String): Single<User?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUser(user: User)

}