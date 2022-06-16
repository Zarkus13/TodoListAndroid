package com.cci.todolist.tasks

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cci.todolist.databinding.TaskListBinding

class TasksAdapter(
    var tasks: List<Task>,
    val onTaskSelected: (Boolean, Int) -> Unit
): RecyclerView.Adapter<TaskViewHolder>() {

    fun updateTasks(newTasks: List<Task>) {
        this.tasks = newTasks
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TaskViewHolder =
        TaskViewHolder(
            TaskListBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            onTaskSelected
        )

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val task = tasks[position]

        holder.taskName.text = task.name
        holder.taskCheckBox.isChecked = false
        holder.taskId = task.id
    }

    override fun getItemCount(): Int = tasks.size
}