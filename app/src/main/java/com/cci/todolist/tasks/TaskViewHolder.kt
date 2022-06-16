package com.cci.todolist.tasks

import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.cci.todolist.TodoListFragmentDirections
import com.cci.todolist.databinding.TaskListBinding

class TaskViewHolder(
    binding: TaskListBinding,
    onTaskSelected: (Boolean, Int) -> Unit
): RecyclerView.ViewHolder(binding.root) {
    val taskCheckBox = binding.taskCheckBox
    val taskName = binding.taskName

    var taskId: Int? = null

    init {
        taskCheckBox.setOnCheckedChangeListener { _, checked ->
            taskId?.let {
                onTaskSelected(checked, it)
            }
        }

        taskName.setOnClickListener {
            val action =
                TodoListFragmentDirections
                    .actionTodoListFragmentToTodoItemFragment(
                        taskName.text.toString()
                    )

            Navigation.findNavController(binding.root)
                .navigate(action)
        }
    }
}