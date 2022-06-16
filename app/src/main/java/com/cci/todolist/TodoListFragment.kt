package com.cci.todolist

import android.os.Bundle
import android.transition.Visibility
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cci.todolist.databinding.FragmentTodoListBinding
import com.cci.todolist.tasks.Task
import com.cci.todolist.tasks.TaskViewHolder
import com.cci.todolist.tasks.TasksAdapter
import java.util.*

class TodoListFragment : Fragment() {
    private var _binding: FragmentTodoListBinding? = null
    private lateinit var binding: FragmentTodoListBinding

    private var tasksCheckedNb = 0
    private var tasksSelectedIds: List<Int> = emptyList()

    private var tasks = listOf<Task>(
        Task(0, "Manger", Date()),
        Task(1, "Boire", Date()),
        Task(2, "Dormir", Date()),
        Task(3, "Manger", Date()),
        Task(4, "Se brosser les dents", Date()),
        Task(5, "Regarder Netflix", Date())
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTodoListBinding.inflate(inflater, container, false)
        binding = _binding!!

        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.tasksListRecyclerView.layoutManager =
            LinearLayoutManager(context)

        val adapter = TasksAdapter(tasks, selectTask)
        binding.tasksListRecyclerView.adapter = adapter

        binding.deleteButton.setOnClickListener {
            deleteTasks(adapter)
        }
    }

//    fun selectTask(checked: Boolean) {
    val selectTask = { checked: Boolean, taskId: Int ->
        if (checked)
            this.tasksSelectedIds = this.tasksSelectedIds.plus(taskId)
        else
            this.tasksSelectedIds = this.tasksSelectedIds.minus(taskId)

        binding.deleteButton.text =
            "Supprimer " + this.tasksSelectedIds.size + " tâches"

        binding.deleteButton.visibility =
            if (this.tasksSelectedIds.size > 0)
                View.VISIBLE
            else
                View.INVISIBLE
    }

    fun deleteTasks(tasksAdapter: TasksAdapter) {
        this.tasks = this.tasks.filterNot { task ->
            this.tasksSelectedIds.contains(task.id)
        }

        tasksAdapter.updateTasks(this.tasks)

        binding.deleteButton.visibility = View.INVISIBLE

        this.tasksSelectedIds = emptyList()
    }

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }
}