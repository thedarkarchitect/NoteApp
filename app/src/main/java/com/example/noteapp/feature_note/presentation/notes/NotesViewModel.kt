package com.example.noteapp.feature_note.presentation.notes


import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.noteapp.feature_note.data.data_source.InvalidNoteException
import com.example.noteapp.feature_note.data.data_source.Note
import com.example.noteapp.feature_note.data.repository.NoteRepository
import com.example.noteapp.feature_note.data.util.NoteOrder
import com.example.noteapp.feature_note.data.util.OrderType
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlin.jvm.Throws


class NotesViewModel(
    private val noteRepository: NoteRepository
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
                    deleteNote(event.note)
                    recentlyDeletedNote = event.note
                }
            }
            is NotesEvent.RestoreNote -> {
                viewModelScope.launch {
                    addNote(recentlyDeletedNote ?: return@launch)
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

    private suspend fun deleteNote(note: Note) = noteRepository.deleteNote(note)

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
        getNotesJob = GetNotes(noteRepository).invoke(noteOrder)
            .onEach {  notes ->
                _noteUiState.value = noteUistate.value.copy(
                    notes = notes,
                    noteOrder = noteOrder
                )
            }.launchIn(viewModelScope)
    }

}

data class NotesUiState(
    val notes: List<Note> = emptyList(),
    val noteOrder: NoteOrder = NoteOrder.Date(OrderType.Descending),
    val isOrderSelectionVisible: Boolean = false
)

class GetNotes( // controls how the notees will be ordered when got from db
    private val repository: NoteRepository
) {
    operator fun invoke(
        noteOrder: NoteOrder = NoteOrder.Date(OrderType.Descending)
    ): Flow<List<Note>> {
        return repository.getAllNotes().map{notes ->
            when(noteOrder.orderType){
                is OrderType.Ascending -> {
                    when(noteOrder){
                        is NoteOrder.Title -> notes.sortedBy{ it.title.lowercase()}
                        is NoteOrder.Date -> notes.sortedBy{ it.timestamp}
                        is NoteOrder.Color -> notes.sortedBy{ it.color}
                    }
                }

                is OrderType.Descending -> {
                    when(noteOrder){
                        is NoteOrder.Title -> notes.sortedByDescending{ it.title.lowercase()}
                        is NoteOrder.Date -> notes.sortedByDescending{ it.timestamp}
                        is NoteOrder.Color -> notes.sortedByDescending{ it.color}
                    }
                }
            }
        }
    }
}