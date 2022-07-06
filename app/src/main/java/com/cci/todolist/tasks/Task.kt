package com.cci.todolist.tasks

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "tasks")
data class Task(
  @PrimaryKey(autoGenerate = true)
  @ColumnInfo(name = "task_id")
  val id: Int,

  @ColumnInfo
  val name: String,

  @ColumnInfo(name = "creation_date")
  val creationDate: Date,

  @ColumnInfo(name = "creator_id")
  val creatorId: Long
)

