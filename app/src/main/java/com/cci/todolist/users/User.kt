package com.cci.todolist.users

import androidx.room.*
import com.cci.todolist.tasks.Task

@Entity(tableName = "users")
data class User(
  @PrimaryKey(autoGenerate = true)
  @ColumnInfo(name = "user_id")
  val id: Long,

  @ColumnInfo
  val username: String
)

data class UserTasks(
  @Embedded
  val user: User,

  @Relation(
    parentColumn = "user_id",
    entityColumn = "creator_id"
  )
  val tasks: List<Task>
)