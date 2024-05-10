package com.example.notesapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.notesapp.R
import com.example.notesapp.databinding.ItemNotesBinding
import com.example.notesapp.model.Notes
import com.example.notesapp.ui.fragments.HomeFragmentDirections

class NotesAdapter():
    RecyclerView.Adapter<NotesAdapter.NotesViewHolder>() {

    private var list: List<Notes> = emptyList()
    inner class NotesViewHolder(val binding: ItemNotesBinding): RecyclerView.ViewHolder(binding.root) {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesViewHolder {
        val binding = ItemNotesBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)

        return NotesViewHolder(binding)
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: NotesViewHolder, position: Int) {
        holder.binding.apply {
            tvItemTitle.text = list[position].title
            tvSubtitle.text = list[position].subtitle
            tvDate.text = list[position].date

            when(list[position].priority) {
                "1" -> { viewPriority.setBackgroundResource(R.drawable.green_dot) }
                "2" -> { viewPriority.setBackgroundResource(R.drawable.yellow_dot) }
                "3" -> { viewPriority.setBackgroundResource(R.drawable.red_dot) }
            }
            root.setOnClickListener {
                val action = HomeFragmentDirections.actionHomeFragmentToEditNotesFragment(list[position])
                Navigation.findNavController(it).navigate(action)
            }
        }
    }

    fun updateUi(list: List<Notes>) {
        this.list = list
        notifyDataSetChanged()
    }
}