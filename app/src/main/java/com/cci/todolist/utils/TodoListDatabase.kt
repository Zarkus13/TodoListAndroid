package com.cci.todolist.utils

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.cci.todolist.tasks.Task
import com.cci.todolist.tasks.TasksDao
import com.cci.todolist.users.User
import com.cci.todolist.users.UsersDao

@Database(
  entities = [User::class, Task::class],
  version = 1
)
@TypeConverters(RoomDateConverter::class)
abstract class TodoListDatabase: RoomDatabase() {
  abstract fun usersDao(): UsersDao

  abstract fun tasksDao(): TasksDao
}