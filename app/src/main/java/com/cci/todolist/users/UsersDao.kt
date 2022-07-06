package com.cci.todolist.users

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction

@Dao
interface UsersDao {
  @Insert
  fun insertOne(user: User): Long

  @Query("SELECT COUNT(*) FROM users WHERE username = :username")
  fun doesUserExist(username: String): Int

  @Query("""
    SELECT * 
    FROM 
        users u
    WHERE
        u.username = :username
    LIMIT 1
  """)
  fun getByUsername(username: String): User?
}