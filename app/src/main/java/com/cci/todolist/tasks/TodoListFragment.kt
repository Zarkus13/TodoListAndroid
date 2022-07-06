package com.cci.todolist.tasks

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.cci.todolist.databinding.FragmentTodoListBinding
import com.cci.todolist.users.UserViewModel

class TodoListFragment : Fragment() {
  private var _binding: FragmentTodoListBinding? = null
  private lateinit var binding: FragmentTodoListBinding

  private val tasksViewModel: TasksViewModel by activityViewModels()
  private val userViewModel: UserViewModel by activityViewModels()

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

    binding.addTaskButton.setOnClickListener {
      tasksViewModel.createTask(
        binding.addTaskInput.text.toString(),
        userViewModel.currentUser.value?.id ?: -1
      )

      binding.addTaskInput.setText("", TextView.BufferType.EDITABLE)
    }
  }

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