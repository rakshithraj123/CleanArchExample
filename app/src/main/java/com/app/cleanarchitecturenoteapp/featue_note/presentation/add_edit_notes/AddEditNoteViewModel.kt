package com.app.cleanarchitecturenoteapp.featue_note.presentation.add_edit_notes

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.State
import androidx.compose.runtime.currentRecomposeScope
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.cleanarchitecturenoteapp.featue_note.domain.model.InvalidNoteException
import com.app.cleanarchitecturenoteapp.featue_note.domain.model.Note
import com.app.cleanarchitecturenoteapp.featue_note.domain.use_cases.NoteUseCases
import com.app.cleanarchitecturenoteapp.featue_note.presentation.notes.NoteState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddEditNoteViewModel @Inject constructor(
    private  val noteUseCases: NoteUseCases,
    saveStateHandle : SavedStateHandle
) : ViewModel(){

    private val _noteTitle = mutableStateOf(NoteTextFieldState(
        hint = "Enter title",

    ))
    val noteTitle: State<NoteTextFieldState> = _noteTitle

    private val _noteContent = mutableStateOf(NoteTextFieldState(
        hint = "Enter Content",
    ))
    val noteContent: State<NoteTextFieldState> = _noteContent

    private val _noteColor= mutableIntStateOf(Note.noteColors.random().toArgb())
    val noteColor: State<Int> = _noteColor

    private  val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow: SharedFlow<UiEvent> = _eventFlow.asSharedFlow()

    private  var currentNoteId : Int? =  null

    init {
        Log.d("**AddEditNoteViewModel**","textData = ${saveStateHandle.get<String>("textData")?: "no textData"}")
        Log.d("**AddEditNoteViewModel**","noteId = ${saveStateHandle.get<Int>("noteId")?: "no noteId"}")

        saveStateHandle.get<Int>("noteId")?.let { noteId ->
            if(noteId != -1){
                viewModelScope.launch {
                    noteUseCases.getNote(noteId)?.also { note ->
                        currentNoteId = note.id
                        _noteTitle.value = noteTitle.value.copy(
                            text = note.title,
                            isHintVisible = false
                        )
                        _noteContent.value = noteContent.value.copy(
                            text = note.content,
                            isHintVisible = false
                        )
                        _noteColor.value =  note.color
                    }
                }
            }
        }
    }

    fun onEvent(event : AddEditNoteEvent){
        when(event){
            is AddEditNoteEvent.EnteredTitle ->{
                _noteTitle.value = noteTitle.value.copy(
                    text = event.value,
                    isHintVisible = event.value.isBlank()
                )
            }
            is AddEditNoteEvent.EnteredContent ->{
                _noteContent.value = noteContent.value.copy(
                    text = event.value,
                    isHintVisible = event.value.isBlank()
                )
            }
            is AddEditNoteEvent.ChangeTitleFocus ->{
//                _noteTitle.value = noteTitle.value.copy(
//                    isHintVisible = !event.focusState.isFocused &&
//                        noteTitle.value.text.isBlank()
//                )
            }
            is AddEditNoteEvent.ChangeContentFocus ->{
//                _noteContent.value = noteContent.value.copy(
//                    isHintVisible = !event.focusState.isFocused &&
//                            noteContent.value.text.isBlank()
//                )
            }
            is AddEditNoteEvent.ChangeColor ->{
                _noteColor.value = event.color
            }
            is AddEditNoteEvent.saveNote ->{
                viewModelScope.launch {
                    try{
                        noteUseCases.addNote(
                            Note(
                                title = noteTitle.value.text,
                                content = noteContent.value.text,
                                timestamp = System.currentTimeMillis(),
                                color = noteColor.value,
                                id = currentNoteId
                            )
                        )
                        _eventFlow.emit(UiEvent.SaveNote)
                    }catch (e : InvalidNoteException){
                        _eventFlow.emit(UiEvent.showSnackbar(e.message?: "Could not save note"))
                    }
                }
            }
        }
    }

    sealed class  UiEvent {
        data class  showSnackbar(val message : String) : UiEvent()
        object SaveNote : UiEvent()

    }

}