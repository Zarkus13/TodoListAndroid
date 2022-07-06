package com.cci.todolist.users

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import com.cci.todolist.utils.TodoListDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UserViewModel(application: Application): AndroidViewModel(application) {
  val db = Room.databaseBuilder(
    application.applicationContext,
    TodoListDatabase::class.java,
    "todolist-database"
  ).build()

  val usersDao = db.usersDao()

  val currentUser = MutableLiveData<User>(null)

  fun changeUsername(username: String) {
    viewModelScope.launch(Dispatchers.IO) {
      val user = usersDao.getByUsername(username)

      if (user == null) {
        val id = usersDao.insertOne(User(0, username))

        currentUser.postValue(
          User(id, username)
        )
      }
      else {
        currentUser.postValue(user!!)
      }
    }
  }

}