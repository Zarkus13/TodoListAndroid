package com.cci.todolist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.cci.todolist.databinding.FragmentTodoListBinding

class TodoListFragment : Fragment() {
    private var _binding: FragmentTodoListBinding? = null
    private lateinit var binding: FragmentTodoListBinding

    private var tasksCheckedNb = 0

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

        binding.todoItemCheck1.setOnCheckedChangeListener { _, checked ->
            selectTask(checked)
        }

        binding.todoItemCheck2.setOnCheckedChangeListener { _, checked ->
            selectTask(checked)
        }

        binding.todoItem1.setOnClickListener {
            val action = TodoListFragmentDirections
                .actionTodoListFragmentToTodoItemFragment(
                    binding.todoItem1.text.toString()
                )

            findNavController().navigate(action)
        }
    }

    fun selectTask(checked: Boolean) {
        if (checked)
            this.tasksCheckedNb++
        else
            this.tasksCheckedNb--

        binding.deleteButton.text = "Supprimer " + this.tasksCheckedNb + " tâches"

        binding.deleteButton.visibility =
            if (this.tasksCheckedNb > 0)
                View.VISIBLE
            else
                View.INVISIBLE
    }

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }
}