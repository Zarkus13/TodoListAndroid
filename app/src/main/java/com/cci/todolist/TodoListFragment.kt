package com.cci.todolist

import android.content.Context
import android.os.Bundle
import android.transition.Visibility
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.MutableLiveData
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cci.todolist.databinding.FragmentTodoListBinding
import com.cci.todolist.tasks.Task
import com.cci.todolist.tasks.TaskViewHolder
import com.cci.todolist.tasks.TasksAdapter
import com.cci.todolist.tasks.TasksViewModel
import java.io.BufferedReader
import java.io.FileOutputStream
import java.io.FileReader
import java.util.*

class TodoListFragment : Fragment() {
  private var _binding: FragmentTodoListBinding? = null
  private lateinit var binding: FragmentTodoListBinding

  private val tasksViewModel: TasksViewModel by viewModels()

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

    val adapter = TasksAdapter(emptyList(), selectTask)
    binding.tasksListRecyclerView.adapter = adapter

    binding.deleteButton.setOnClickListener {
      deleteTasks()
    }

    tasksViewModel.tasks.observe(viewLifecycleOwner) { tasks ->
      adapter.updateTasks(tasks)
    }
  }

  //    fun selectTask(checked: Boolean) {
  val selectTask = { checked: Boolean, taskId: Int ->
    if (checked)
      tasksViewModel.addSelectedTaskId(taskId)
    else
      tasksViewModel.removeSelectedTaskId(taskId)

    binding.deleteButton.text =
      "Supprimer " + tasksViewModel.getTasksSelectedIdsSize() + " tâches"

    binding.deleteButton.visibility =
      if (tasksViewModel.getTasksSelectedIdsSize() > 0)
        View.VISIBLE
      else
        View.INVISIBLE
  }

  fun deleteTasks() {
    tasksViewModel.deleteSelectedTasks()

    binding.deleteButton.visibility = View.INVISIBLE

    tasksViewModel.emptySelectedTasks()
  }

  override fun onDestroyView() {
    super.onDestroyView()

    _binding = null
  }
}