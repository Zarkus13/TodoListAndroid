package com.cci.todolist

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.cci.todolist.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private val usernameViewModel: UsernameViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        usernameViewModel.username.observe(this) {
            binding.usernameTextView.text = it
        }

        binding.modifyUsernameButton.setOnClickListener {
            openUsernameDialog()
        }

        val username =
            getPreferences(Context.MODE_PRIVATE)
                .getString("username", null)

        usernameViewModel.username.value = username

        if (username == null) {
            openUsernameDialog()
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