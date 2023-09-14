package com.example.noteapp.di

import android.content.Context
import com.example.noteapp.feature_note.data.data_source.NoteDatabase
import com.example.noteapp.feature_note.data.repository.NoteRepositoryImpl
import com.example.noteapp.feature_note.data.repository.NoteRepository
import com.example.noteapp.feature_note.data.use_case.DeleteNote
import com.example.noteapp.feature_note.data.use_case.GetNotes
import com.example.noteapp.feature_note.data.use_case.NoteUseCases

/*
* App container for Dependency injection
* */
interface AppContainer {
    val noteRepository : NoteRepository
}

/*
* [AppContainer] implementation that provides instance of [noteRepository]*/
class AppDataContainer(private val context: Context) : AppContainer {

    override val noteRepository: NoteRepository by lazy {
        NoteRepositoryImpl(NoteDatabase.getDatabase(context).noteDao())
    }

    fun provideNoteUseCases(repository: NoteRepository): NoteUseCases {
        return NoteUseCases(
            getNotes = GetNotes(noteRepository),
            deleteNote = DeleteNote(noteRepository)
        )
    }
}


