package com.example.notesapp.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.notesapp.model.Notes

@Dao
interface NotesDao {

    @Query("Select * FROM Notes")
    fun getNotes(): LiveData<List<Notes>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertNotes(notes: Notes)

    @Query("DELETE FROM Notes WHERE id=:id")
    fun deleteNotes(id: Int)

    @Update
    fun updateNotes(notes: Notes)

    @Query("Select * FROM Notes WHERE priority = 1")
    fun getLowNotes(): LiveData<List<Notes>>

    @Query("Select * FROM Notes WHERE priority = 2")
    fun getMediumNotes(): LiveData<List<Notes>>

    @Query("Select * FROM Notes WHERE priority = 3")
    fun getHighNotes(): LiveData<List<Notes>>
}