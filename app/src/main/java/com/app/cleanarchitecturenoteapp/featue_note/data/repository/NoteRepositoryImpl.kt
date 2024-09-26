package com.app.cleanarchitecturenoteapp.featue_note.data.repository

import com.app.cleanarchitecturenoteapp.featue_note.data.data_source.NoteDao
import com.app.cleanarchitecturenoteapp.featue_note.domain.model.Note
import kotlinx.coroutines.flow.Flow

class NoteRepositoryImpl(
    private val dao : NoteDao
) : NoteRepository{

    override fun getNotes(): Flow<List<Note>> {
        return dao.getNotes()
    }

    override suspend fun getNoteById(id: Int): Note? {
        return dao.getNotesById(id)
    }

    override suspend fun insertNote(note: Note) {
        return dao.insertNote(note)
    }

    override suspend fun deleteNote(note: Note) {
        return dao.deleteNote(note)
    }

}