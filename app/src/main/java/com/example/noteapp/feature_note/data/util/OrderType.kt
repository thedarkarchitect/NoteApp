package com.example.noteapp.feature_note.data.util

sealed class OrderType{
    object Ascending: OrderType()
    object Descending: OrderType()
}
