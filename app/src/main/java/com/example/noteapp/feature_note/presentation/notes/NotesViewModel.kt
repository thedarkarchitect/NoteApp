package com.example.noteapp.feature_note.presentation.notes


import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.noteapp.feature_note.data.data_source.InvalidNoteException
import com.example.noteapp.feature_note.data.data_source.Note
import com.example.noteapp.feature_note.data.util.NoteOrder
import com.example.noteapp.feature_note.data.util.OrderType
import com.example.noteapp.feature_note.domain.use_case.NoteUseCases
import com.example.noteapp.feature_note.domain.util.NotesUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotesViewModel @Inject constructor(
    private val noteUsesCases: NoteUseCases
) : ViewModel() {

    //state
    private val _noteUiState = mutableStateOf(NotesUiState())
    val noteUistate: State<NotesUiState> = _noteUiState

    //tracks the deleted note
    private var recentlyDeletedNote: Note? = null

    //control the job
    private var getNotesJob: Job? = null

    init {//this is run when the app is started
        getAllNotes(NoteOrder.Date(orderType = OrderType.Descending))
    }

    fun onEvent(event: NotesEvent){
        when(event) {
            is NotesEvent.Order -> {
                if(noteUistate.value.noteOrder::class == event.noteOrder::class &&
                    noteUistate.value.noteOrder.orderType == event.noteOrder.orderType
                    ){
                    return
                }
                getAllNotes(event.noteOrder)
            }
            is NotesEvent.DeleteNote -> {
                viewModelScope.launch {
                    noteUsesCases.deleteNote(event.note)
                    recentlyDeletedNote = event.note
                }
            }
            is NotesEvent.RestoreNote -> {
                viewModelScope.launch {
                    noteUsesCases.addNote(recentlyDeletedNote ?: return@launch)
                    recentlyDeletedNote = null//only after addNOte is done on the recently deleted note
                }
            }
            is NotesEvent.ToggleOrderSelection -> {
                _noteUiState.value = noteUistate.value.copy(
                    isOrderSelectionVisible = !noteUistate.value.isOrderSelectionVisible //toggling the toggleSelectionOrder
                )
            }
        }
    }


    @Throws(InvalidNoteException::class)
    private suspend fun addNote(note: Note){
        if(note.title.isBlank()){
            throw InvalidNoteException("The title of the note can't be empty.")
        }
        if(note.content.isBlank()){
            throw InvalidNoteException("The content of the note can't be empty.")
        }
        noteRepository.insertNote(note)
    }

    private fun getAllNotes(noteOrder: NoteOrder){
        getNotesJob?.cancel()
        noteUsesCases.getAllNotes(noteOrder)
            .onEach {  notes ->
                _noteUiState.value = noteUistate.value.copy(
                    notes = notes,
                    noteOrder = noteOrder
                )
            }.launchIn(viewModelScope)
    }

}


