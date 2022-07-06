package com.cci.todolist.users

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.cci.todolist.databinding.UsernameDialogBinding

class UsernameDialogFragment: DialogFragment() {
  private var _binding: UsernameDialogBinding? = null
  private lateinit var binding: UsernameDialogBinding

  private val userViewModel: UserViewModel by activityViewModels()

  override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
    _binding = UsernameDialogBinding.inflate(
      layoutInflater,
      null,
      false
    )
    binding = _binding!!

    val builder = AlertDialog.Builder(requireActivity())

    val dialog =
      builder
        .setMessage("Enter your username :")
        .setView(binding.root)
        .setPositiveButton("Save") { dialog, id ->
          val prefs = requireActivity().getPreferences(Context.MODE_PRIVATE)

          prefs.edit()
            .putString("username", binding.usernameInput.text.toString())
            .apply()

          userViewModel.changeUsername(
            binding.usernameInput.text.toString()
          )
        }
        .create()

    return dialog
  }
}