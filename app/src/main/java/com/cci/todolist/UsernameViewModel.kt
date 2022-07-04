package com.cci.todolist

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class UsernameViewModel: ViewModel() {
  val username = MutableLiveData<String>(null)


}