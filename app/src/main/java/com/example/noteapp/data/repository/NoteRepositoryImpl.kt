package com.example.noteapp.data.repository

import com.example.noteapp.data.data_source.NoteDao
import com.example.noteapp.domain.model.Note
import com.example.noteapp.domain.repository.NoteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class NoteRepositoryImpl @Inject constructor(
    private val dao: NoteDao
): NoteRepository {
    override fun getNotes(): Flow<List<Note>> = dao.getNotes()

    override suspend fun getNoteById(id: Int): Note? = dao.getNoteById(id)

    override suspend fun insertNote(note: Note) = dao.insertNote(note)

    override suspend fun deleteNote(note: Note) = dao.deleteNote(note)
}