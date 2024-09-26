package com.app.cleanarchitecturenoteapp.featue_note.domain.use_cases

import com.app.cleanarchitecturenoteapp.featue_note.data.repository.NoteRepository
import com.app.cleanarchitecturenoteapp.featue_note.domain.model.Note

class GetNote(
    private  val repository: NoteRepository
) {
    suspend  operator fun invoke(id: Int) : Note?{
        return repository.getNoteById(id)
    }
}