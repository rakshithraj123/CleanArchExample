package com.app.cleanarchitecturenoteapp.featue_note.presentation.notes

import com.app.cleanarchitecturenoteapp.featue_note.domain.model.Note
import com.app.cleanarchitecturenoteapp.featue_note.domain.util.NoteOrder
import com.app.cleanarchitecturenoteapp.featue_note.domain.util.OrderType

data class NoteState(
    val notes: List<Note> = emptyList(),
    val noteOrder: NoteOrder = NoteOrder.Date(OrderType.Descending),
    val isOrderSectionVisible: Boolean = false
)
