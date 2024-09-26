package com.app.cleanarchitecturenoteapp.featue_note.domain.use_cases

import com.app.cleanarchitecturenoteapp.featue_note.data.repository.NoteRepository
import com.app.cleanarchitecturenoteapp.featue_note.domain.model.InvalidNoteException
import com.app.cleanarchitecturenoteapp.featue_note.domain.model.Note

class AddNote(
    private  val repository : NoteRepository
) {
    @Throws(InvalidNoteException::class)
    suspend  operator fun invoke(note: Note){
        if(note.title.isBlank()){
            throw InvalidNoteException("The title of the note can't be empty.")
        }
        if(note.content.isBlank()){
            throw InvalidNoteException("The content of the note can't be empty.")
        }
        repository.insertNote(note)
    }
}