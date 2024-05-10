package com.example.notesapp.repository

import androidx.lifecycle.LiveData
import com.example.notesapp.dao.NotesDao
import com.example.notesapp.model.Notes

class NotesRepository(private val dao: NotesDao) {

    fun getLowNotes(): LiveData<List<Notes>> {
        return dao.getLowNotes()
    }

    fun getMediumNotes(): LiveData<List<Notes>> {
        return dao.getMediumNotes()
    }

    fun getHighNotes(): LiveData<List<Notes>> {
        return dao.getHighNotes()
    }

    fun getAllNotes(): LiveData<List<Notes>> {
        return dao.getNotes()
    }

    fun insertNotes(notes: Notes) {
        dao.insertNotes(notes)
    }

    fun deleteNotes(id: Int) {
        dao.deleteNotes(id)
    }

    fun updateNotes(notes: Notes) {
        dao.updateNotes(notes)
    }

}