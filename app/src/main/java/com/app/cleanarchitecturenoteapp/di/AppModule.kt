package com.app.cleanarchitecturenoteapp.di

import android.app.Application
import androidx.room.Room
import com.app.cleanarchitecturenoteapp.featue_note.data.data_source.NoteDatabase
import com.app.cleanarchitecturenoteapp.featue_note.data.repository.NoteRepository
import com.app.cleanarchitecturenoteapp.featue_note.data.repository.NoteRepositoryImpl
import com.app.cleanarchitecturenoteapp.featue_note.domain.use_cases.AddNote
import com.app.cleanarchitecturenoteapp.featue_note.domain.use_cases.DeleteNote
import com.app.cleanarchitecturenoteapp.featue_note.domain.use_cases.GetNote
import com.app.cleanarchitecturenoteapp.featue_note.domain.use_cases.GetNotes
import com.app.cleanarchitecturenoteapp.featue_note.domain.use_cases.NoteUseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideNoteDatabase(app : Application) : NoteDatabase{
        return Room.databaseBuilder(
            app,
            NoteDatabase::class.java,
            NoteDatabase.DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideNoteRepository(db : NoteDatabase) : NoteRepository{
        return NoteRepositoryImpl(db.noteDao)
    }

    @Provides
    @Singleton
    fun provideNoteUseCases(noteRepository: NoteRepository ) : NoteUseCases{
        return NoteUseCases(
            getNotes = GetNotes(repository = noteRepository),
            deleteNote = DeleteNote(repository = noteRepository),
            addNote = AddNote(repository = noteRepository),
            getNote = GetNote(repository = noteRepository),
        )
    }
}