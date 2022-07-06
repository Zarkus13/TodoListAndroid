package com.cci.todolist.tasks

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import com.cci.todolist.utils.TodoListDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

class TasksViewModel(application: Application): AndroidViewModel(application) {
  val db = Room.databaseBuilder(
    application.applicationContext,
    TodoListDatabase::class.java,
    "todolist-database"
  ).build()

  val tasksDao = db.tasksDao()

  val tasks = MutableLiveData<List<Task>>(emptyList())
  val tasksSelectedIds = MutableLiveData<List<Int>>(emptyList())

  fun updateTasksFromCreator(creatorId: Long) {
    viewModelScope.launch(Dispatchers.IO) {
      tasks.postValue(tasksDao.getAllFromCreator(creatorId))
    }
  }

  fun createTask(name: String, creatorId: Long) {
    viewModelScope.launch(Dispatchers.IO) {
      val t = Task(0, name, Date(), creatorId)

      tasksDao.insertOne(t)

      tasks.postValue(tasks.value?.plus(t))
    }
  }

  fun getTasksSelectedIdsSize(): Int =
    tasksSelectedIds.value?.size ?: 0

  fun addSelectedTaskId(id: Int) {
    tasksSelectedIds.value =
      (tasksSelectedIds.value ?: emptyList()).plus(id)
  }

  fun removeSelectedTaskId(id: Int) {
    tasksSelectedIds.value =
      (tasksSelectedIds.value ?: emptyList()).minus(id)
  }

  fun deleteSelectedTasks() {
    viewModelScope.launch(Dispatchers.IO) {
      tasksDao.deleteAll(
        *((tasksSelectedIds.value ?: emptyList()).map {
          Task(it, "", Date(), 0)
        }.toTypedArray())
      )
    }

    tasks.value =
      (tasks.value ?: emptyList())
        .filterNot { task ->
          (tasksSelectedIds.value ?: emptyList()).contains(task.id)
        }
  }

  fun emptySelectedTasks() {
    tasksSelectedIds.value = emptyList()
  }
}