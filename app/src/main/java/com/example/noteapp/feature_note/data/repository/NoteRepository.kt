package com.example.noteapp.feature_note.data.repository

import com.example.noteapp.feature_note.data.data_source.Note
import kotlinx.coroutines.flow.Flow

interface NoteRepository { //note repo interface
    fun getAllNotes(): Flow<List<Note>>

    suspend fun getNote(id: Int): Note?

    suspend fun insertNote(note: Note)

    suspend fun deleteNote(note: Note)

}