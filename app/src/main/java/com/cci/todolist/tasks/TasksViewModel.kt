package com.cci.todolist.tasks

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import com.cci.todolist.utils.TodoListDatabase
import com.cci.todolist.utils.TodoListMoshi.moshi
import com.cci.todolist.utils.TodoListMoshi.taskAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.net.Inet4Address
import java.net.NetworkInterface
import java.util.*

class TasksViewModel(application: Application): AndroidViewModel(application) {
  val db = Room.databaseBuilder(
    application.applicationContext,
    TodoListDatabase::class.java,
    "todolist-database"
  ).build()

  val tasksDao = db.tasksDao()

  val boredApiService = BoredApi.retrofitService

  val tasks = MutableLiveData<List<Task>>(emptyList())
  val tasksSelectedIds = MutableLiveData<List<Int>>(emptyList())

  init {
    viewModelScope.launch(Dispatchers.IO) {
      val networkInterfaces = NetworkInterface.getNetworkInterfaces()

      val ip =
        networkInterfaces.toList()
          .find { it.displayName == "wlan0"}
          ?.inetAddresses?.toList()
          ?.find { it is Inet4Address }
          ?.hostAddress ?: "127.0.0.1"

      viewModelScope.launch(Dispatchers.Main) {
        Toast.makeText(application, ip, Toast.LENGTH_LONG).show()
      }
    }
  }

  fun updateTasksFromCreator(creatorId: Long) {
    viewModelScope.launch(Dispatchers.IO) {
      val userTasks = tasksDao.getAllFromCreator(creatorId)
      tasks.postValue(userTasks)

      if (!userTasks.isEmpty()) {
        val json = taskAdapter.toJson(userTasks[0])

        viewModelScope.launch(Dispatchers.Main) {
          Toast.makeText(getApplication(), json.toString(), Toast.LENGTH_LONG).show()
        }

        println(json.toString())

        val jsonStr = "{\"id\":26,\"name\":\"Configure two-factor authentication on your accounts\",\"creationDate\":1657118016008,\"creatorId\":5}"

        val t = taskAdapter.fromJson(jsonStr)

        println("Task : " + t.toString())
      }

      if (userTasks.isEmpty()) {
        val newTasks = Array<Int>(10, { it })
          .map {
            val a = boredApiService.getActivity()
            val task = Task(0, a.task, Date(), creatorId)

            tasksDao.insertOne(task)

            task
          }

        tasks.postValue(newTasks)
      }
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