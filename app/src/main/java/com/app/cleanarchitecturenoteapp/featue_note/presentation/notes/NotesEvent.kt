package com.app.cleanarchitecturenoteapp.featue_note.presentation.notes

import com.app.cleanarchitecturenoteapp.featue_note.domain.model.Note
import com.app.cleanarchitecturenoteapp.featue_note.domain.util.NoteOrder

sealed class NotesEvent {
    data class Order(val noteOrder : NoteOrder) : NotesEvent()
    data class DeleteNote(val note : Note) : NotesEvent()
    object RestoreNote : NotesEvent()
    object ToggleOrderSection : NotesEvent()
}