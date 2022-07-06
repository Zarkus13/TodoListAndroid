package com.cci.todolist.tasks

import com.squareup.moshi.Json

data class BoredActivity(
  @Json(name = "activity")
  val task: String
)
