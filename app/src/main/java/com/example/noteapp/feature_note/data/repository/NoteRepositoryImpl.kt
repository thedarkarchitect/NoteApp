package com.example.noteapp.feature_note.data.repository

import com.example.noteapp.feature_note.data.data_source.NoteDao
import com.example.noteapp.feature_note.domain.model.Note
import com.example.noteapp.feature_note.domain.repository.NoteRepository
import kotlinx.coroutines.flow.Flow

class NoteRepositoryImpl(
    private val dao : NoteDao
): NoteRepository {//note repo implementation
    override fun getAllNotes(): Flow<List<Note>> = dao.getAllNotes()

    override suspend fun getNote(id: Int): Note? = dao.getNote(id)

    override suspend fun insertNote(note: Note) = dao.insertNote(note)

    override suspend fun deleteNote(note: Note) = dao.deleteNote(note)

}