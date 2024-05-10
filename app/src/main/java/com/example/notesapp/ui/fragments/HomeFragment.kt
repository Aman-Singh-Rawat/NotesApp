package com.example.notesapp.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.notesapp.R
import com.example.notesapp.databinding.FragmentHomeBinding
import com.example.notesapp.model.Notes
import com.example.notesapp.ui.adapter.NotesAdapter
import com.example.notesapp.viewmodel.NotesViewModel

class HomeFragment : Fragment(), SearchView.OnQueryTextListener {
    private lateinit var searchView: SearchView
    private val notesAdapter = NotesAdapter()
    private lateinit var binding: FragmentHomeBinding
    private val viewModel by activityViewModels<NotesViewModel>()
    private var searchNote = emptyList<Notes>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(layoutInflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.toolbar.title = resources.getString(R.string.home_page)

        binding.toolbar.inflateMenu(R.menu.search_menu)
        val searchItem = binding.toolbar.menu.findItem(R.id.appBarSearch)
        searchView = searchItem.actionView as SearchView

        searchView.setOnQueryTextListener(this)

        binding.btnAddNotes.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_createNotesFragment)
        }

        val colorApply = ContextCompat.getColor(requireContext(), R.color.white)
        binding.btnAddNotes.setColorFilter(colorApply)

        binding.rvHomeFragment.adapter = notesAdapter
        viewModel.getNotes().observe(viewLifecycleOwner) {
            searchNote = it
            notesAdapter.updateUi(it)
        }

        binding.imgFilter.setOnClickListener {
            viewModel.getNotes().observe(viewLifecycleOwner) {
                searchNote = it
                notesAdapter.updateUi(it)
            }
        }

        binding.btnHigh.setOnClickListener {
            viewModel.getHighNotes().observe(viewLifecycleOwner) {
                searchNote = it
                notesAdapter.updateUi(it)
            }
        }

        binding.btnMedium.setOnClickListener {
            viewModel.getMediumNotes().observe(viewLifecycleOwner) {
                searchNote = it
                notesAdapter.updateUi(it)
            }
        }

        binding.btnLow.setOnClickListener {
            viewModel.getLowNotes().observe(viewLifecycleOwner) {
                searchNote = it
                notesAdapter.updateUi(it)
            }
        }
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        return false
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        val newFilteredList: ArrayList<Notes> = arrayListOf()
        newText?.let {
            for (data in searchNote) {
                if (data.title.contains(newText) || data.subtitle.contains(newText))
                    newFilteredList.add(data)
            }
        }
        notesAdapter.updateUi(newFilteredList.toList())

        return true
    }
}