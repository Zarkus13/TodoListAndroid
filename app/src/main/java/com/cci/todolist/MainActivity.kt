package com.cci.todolist

import android.content.Context
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.cci.todolist.databinding.ActivityMainBinding
import com.cci.todolist.tasks.TasksViewModel
import com.cci.todolist.users.UsernameDialogFragment
import com.cci.todolist.users.UserViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private val userViewModel: UserViewModel by viewModels()
    private val tasksViewModel: TasksViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        userViewModel.currentUser.observe(this) { user ->
            if (user != null) {
                binding.usernameTextView.text = user.username
                tasksViewModel.updateTasksFromCreator(user.id)
            }
        }

        binding.modifyUsernameButton.setOnClickListener {
            openUsernameDialog()
        }

        val username =
            getPreferences(Context.MODE_PRIVATE)
                .getString("username", null)

        if (username == null) {
            openUsernameDialog()
        } else {
            userViewModel.changeUsername(username!!)
        }
    }

    fun openUsernameDialog() {
        UsernameDialogFragment()
            .show(
                supportFragmentManager,
                "username-dialog"
            )
    }
}