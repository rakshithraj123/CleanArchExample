package com.app.cleanarchitecturenoteapp.featue_note.presentation.add_edit_notes

data class NoteTextFieldState(
    val text : String = "",
    val hint : String  = "",
    val isHintVisible : Boolean = true
)
