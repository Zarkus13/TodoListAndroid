package com.cci.todolist.utils

import androidx.room.TypeConverter
import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson
import java.util.*

class DateConverter {
  @TypeConverter
  @FromJson
  fun fromTimestamp(value: Long?): Date? {
    return value?.let { Date(it) }
  }

  @TypeConverter
  @ToJson
  fun dateToTimestamp(date: Date?): Long? {
    return date?.time?.toLong()
  }
}