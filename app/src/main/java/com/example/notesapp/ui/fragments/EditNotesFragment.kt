package com.example.notesapp.ui.fragments

import android.os.Bundle
import android.text.format.DateFormat
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.navigateUp
import com.example.notesapp.R
import com.example.notesapp.databinding.FragmentEditNotesBinding
import com.example.notesapp.model.Notes
import com.example.notesapp.ui.fragments.EditNotesFragmentArgs
import com.example.notesapp.viewmodel.NotesViewModel
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.snackbar.Snackbar
import java.util.Date

class EditNotesFragment : Fragment() {
    val notes by navArgs<EditNotesFragmentArgs>()
    private lateinit var binding: FragmentEditNotesBinding
    private val viewModel by activityViewModels<NotesViewModel> ()
    private var priority = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEditNotesBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        toolbarSetup()
        setHasOptionsMenu(true)
        setUpDataOnView()
        prioritySet()


        (activity as AppCompatActivity).setSupportActionBar(binding.toolbar)

        binding.btnDoneNotes.setOnClickListener {
            createNotes()
        }
    }

    private fun toolbarSetup() {
        binding.toolbar.title = resources.getString(R.string.edit_notes)
        binding.toolbar.setNavigationIcon(R.drawable.ic_back_arrow)
        binding.toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun createNotes() {
        val title = binding.etTitle.text.toString()
        val subtitle = binding.etSubtitle.text.toString()
        val notesText = binding.etNotes.text.toString()
        val notesDate = DateFormat.format("MMMM d, yyyy", Date()).toString()
        Log.d("currentDate", notesDate)

        viewModel.updateNotes(
            Notes(notes.data.id, title = title, subtitle = subtitle,
            notes = notesText, date = notesDate, priority = priority)
        )


        Toast.makeText(requireContext(), "Notes Updated Successfully", Toast.LENGTH_LONG).show()
        findNavController().navigateUp()
    }

    private fun setUpDataOnView() {
        binding.etTitle.setText(notes.data.title)
        binding.etSubtitle.setText(notes.data.subtitle)
        binding.etNotes.setText(notes.data.notes)


        when (notes.data.priority) {
            "1" -> {
                priority = "1"
                binding.imgGreenDot.setImageResource(R.drawable.ic_done)
                binding.imgRedDot.setImageResource(0)
                binding.imgYellowDot.setImageResource(0)
            }

            "2" -> {
                priority = "2"
                binding.imgYellowDot.setImageResource(R.drawable.ic_done)
                binding.imgGreenDot.setImageResource(0)
                binding.imgRedDot.setImageResource(0)
            }

            "3" -> {
                priority = "3"
                binding.imgRedDot.setImageResource(R.drawable.ic_done)
                binding.imgGreenDot.setImageResource(0)
                binding.imgYellowDot.setImageResource(0)
            }
        }
    }

    private fun prioritySet() {
        binding.apply{
            imgGreenDot.setOnClickListener {
                priority = "1"
                imgGreenDot.setImageResource(R.drawable.ic_done)
                imgRedDot.setImageResource(0)
                imgYellowDot.setImageResource(0)
            }

            imgYellowDot.setOnClickListener {
                priority = "2"
                imgYellowDot.setImageResource(R.drawable.ic_done)
                imgGreenDot.setImageResource(0)
                imgRedDot.setImageResource(0)
            }

            imgRedDot.setOnClickListener {
                priority = "3"
                imgRedDot.setImageResource(R.drawable.ic_done)
                imgGreenDot.setImageResource(0)
                imgYellowDot.setImageResource(0)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.edit_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.delete) {
            val bottomSheetDialog = BottomSheetDialog(requireContext(), R.style.BottomSheetDialog)
            bottomSheetDialog.setContentView(R.layout.dialog_delete)
            val btnYes = bottomSheetDialog.findViewById<Button>(R.id.btnDialogYes)
            val btnNo = bottomSheetDialog.findViewById<Button>(R.id.btnDialogNo)
            btnYes?.setOnClickListener {
                viewModel.deleteNotes(notes.data.id!!)
                bottomSheetDialog.dismiss()
                findNavController().navigateUp()
                Snackbar.make(requireContext(), binding.root, "Note Deleted Successfully", Snackbar.LENGTH_LONG).show()
            }
            btnNo?.setOnClickListener {
                bottomSheetDialog.dismiss()
            }
            bottomSheetDialog.show()
        }
        return super.onOptionsItemSelected(item)
    }

}