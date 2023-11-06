package com.example.noteapp.di

import android.app.Application
import androidx.room.Room
import com.example.noteapp.data.data_source.NoteDao
import com.example.noteapp.data.data_source.NoteDatabase
import com.example.noteapp.data.repository.NoteRepositoryImpl
import com.example.noteapp.domain.repository.NoteRepository
import com.example.noteapp.domain.use_case.AddNote
import com.example.noteapp.domain.use_case.DeleteNote
import com.example.noteapp.domain.use_case.GetNote
import com.example.noteapp.domain.use_case.GetNotes
import com.example.noteapp.domain.use_case.NoteUseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideNoteDatabase(
        application: Application
    ): NoteDatabase {
        return Room.databaseBuilder(
            application,
            NoteDatabase::class.java,
            "note_db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideNoteRepository(
        db: NoteDatabase
    ): NoteRepository {
        return NoteRepositoryImpl(
            dao = db.noteDao
        )
    }

    @Provides
    @Singleton
    fun provideNoteUseCases(
        repository: NoteRepository
    ): NoteUseCases {
        return NoteUseCases(
            getNotes = GetNotes( repository ),
            deleteNote = DeleteNote( repository ),
            addNote = AddNote(repository),
            getNote = GetNote(repository)
        )
    }
}