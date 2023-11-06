package com.example.noteapp.presentation.add_edit_note

sealed class UiEvent {
    data class ShowSnackBar(val message: String): UiEvent()
    data object SaveNote: UiEvent()
}
