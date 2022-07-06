package com.cci.todolist.tasks

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.cci.todolist.databinding.FragmentTodoItemBinding

class TodoItemFragment : Fragment() {
    private var _binding: FragmentTodoItemBinding? = null
    private lateinit var binding: FragmentTodoItemBinding

    private val args: TodoItemFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTodoItemBinding.inflate(inflater, container, false)
        binding = _binding!!

        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.todoDetailTitle.text = args.itemName

        binding.goBackButton.setOnClickListener {
            val action = TodoItemFragmentDirections.actionTodoItemFragmentToTodoListFragment()

            findNavController().navigate(action)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }
}