package com.cci.todolist.tasks

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.util.*

class TasksViewModel: ViewModel() {
  val tasks = MutableLiveData<List<Task>>(emptyList())
  val tasksSelectedIds = MutableLiveData<List<Int>>(emptyList())

  init {
    tasks.value = listOf<Task>(
      Task(0, "Manger", Date()),
      Task(1, "Boire", Date()),
      Task(2, "Dormir", Date()),
      Task(3, "Manger", Date()),
      Task(4, "Se brosser les dents", Date()),
      Task(5, "Regarder Netflix", Date())
    )
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