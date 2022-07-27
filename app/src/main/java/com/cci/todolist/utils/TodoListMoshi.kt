package com.cci.todolist.utils

import com.cci.todolist.tasks.Task
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

object TodoListMoshi {
  val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .add(DateConverter())
    .build()

  val taskAdapter = moshi.adapter(Task::class.java)
}