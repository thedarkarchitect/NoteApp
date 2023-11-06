package com.example.noteapp.domain.model

import androidx.compose.ui.graphics.Color
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.noteapp.ui.theme.BabyBlue
import com.example.noteapp.ui.theme.LightGreen
import com.example.noteapp.ui.theme.RedOrange
import com.example.noteapp.ui.theme.RedPink
import com.example.noteapp.ui.theme.Violet

@Entity(tableName = "note")
data class Note(
    val title: String,
    val content: String,
    val timestamp: Long,
    val color: Int,
    @PrimaryKey(autoGenerate = true) val id: Int? = null,
){
    companion object{
        val noteColors = listOf(RedOrange, LightGreen, Violet, BabyBlue, RedPink)
    }
}

class InvalidNoteException(message: String): Exception(message) //returns a message if the fields are not enterd correctly
