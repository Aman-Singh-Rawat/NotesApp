package com.example.notesapp.ui.fragments

import android.os.Build
import android.os.Bundle
import android.text.format.DateFormat
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.notesapp.R
import com.example.notesapp.databinding.FragmentCreateNotesBinding
import com.example.notesapp.model.Notes
import com.example.notesapp.viewmodel.NotesViewModel
import java.util.Date

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
class CreateNotesFragment : Fragment() {
    private lateinit var binding: FragmentCreateNotesBinding
    private var priority: String = "1"
    private val viewModel: NotesViewModel by activityViewModels<NotesViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCreateNotesBinding.inflate(layoutInflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        toolbarSetup()

        val applyColor = ContextCompat.getColor(requireContext(), R.color.white)
        binding.btnDoneNotes.setColorFilter(applyColor)

        prioritySet()

        binding.btnDoneNotes.setOnClickListener {
            createNotes()
        }
    }


    private fun toolbarSetup() {
        binding.toolbar.title = resources.getString(R.string.create_fragment)
        binding.toolbar.setNavigationIcon(R.drawable.ic_back_arrow)
        binding.toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }
    }
    private fun prioritySet() {
        binding.apply{
            imgGreenDot.setImageResource(R.drawable.ic_done)
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

    private fun createNotes() {
        val title = binding.etTitle.text.toString()
        val subtitle = binding.etSubtitle.text.toString()
        val notes = binding.etNotes.text.toString()
        val notesDate = DateFormat.format("MMMM d, yyyy", Date()).toString()
        Log.d("currentDate", notesDate)

        viewModel.addNotes(Notes(null, title = title, subtitle = subtitle,
            notes = notes, date = notesDate, priority = priority))


        Toast.makeText(requireContext(), "Notes created Successfully", Toast.LENGTH_LONG).show()
        findNavController().navigateUp()
    }

}