package com.app.cleanarchitecturenoteapp.featue_note.domain.use_cases

import com.app.cleanarchitecturenoteapp.featue_note.data.repository.NoteRepository
import com.app.cleanarchitecturenoteapp.featue_note.domain.model.Note

class DeleteNote(
    private  val repository : NoteRepository
) {

    suspend  operator fun invoke(note: Note){
        repository.deleteNote(note)
    }
}