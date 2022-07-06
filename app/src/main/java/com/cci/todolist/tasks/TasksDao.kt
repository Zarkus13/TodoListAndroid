package com.cci.todolist.tasks

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface TasksDao {
  @Insert
  fun insertOne(task: Task)

  @Delete
  fun deleteAll(vararg tasks: Task)

  @Query(
    """
    SELECT *
    FROM 
        tasks t
    WHERE
        t.creator_id = :creatorId
  """)
  fun getAllFromCreator(creatorId: Long): List<Task>
}