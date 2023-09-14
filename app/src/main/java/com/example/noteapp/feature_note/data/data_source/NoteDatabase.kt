package com.example.noteapp.feature_note.data.data_source

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [Note::class],
    version = 1
)
abstract class NoteDatabase : RoomDatabase() {
    abstract fun noteDao(): NoteDao

    companion object{
        @Volatile
        private var Instance : NoteDatabase? = null

        fun getDatabase(context: Context): NoteDatabase{
            return Instance ?: synchronized(this){
                Room.databaseBuilder(context, NoteDatabase::class.java, "note_db")
                    .build()
                    .also{Instance = it}
            }
        }
    }
}