package com.app.cleanarchitecturenoteapp.featue_note.domain.use_cases

import com.app.cleanarchitecturenoteapp.featue_note.FakeNoteRepository
import com.app.cleanarchitecturenoteapp.featue_note.domain.model.InvalidNoteException
import com.app.cleanarchitecturenoteapp.featue_note.domain.model.Note
import com.google.common.truth.Truth.assertThat
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.runBlocking

import org.junit.Before
import org.junit.Test

class AddNoteTest {

    private lateinit var addNote: AddNote
    private lateinit var fakeNoteRepository: FakeNoteRepository

    @Before
    fun setUp() {
        //fakeNoteRepository = FakeNoteRepository()
       // fakeNoteRepository = mock(FakeNoteRepository::class.java)
        fakeNoteRepository = mockk(relaxed = true)// relaxed = true allows methods to be mocked with default behavior

        addNote = AddNote(fakeNoteRepository)
    }

    @Test(expected = InvalidNoteException::class)
    fun `Add Note, throw InvalidNoteException when note title is blank`() = runBlocking {

        val note = Note(
            title = "",
            content = "content",
            timestamp = 1,
            color = 1,
            id = 1
        )
        addNote(note)
    }

    @Test()
    fun `Add Note, throw InvalidNoteException when note content is blank`() = runBlocking {

        val note = Note(
            title = "title",
            content = "",
            timestamp = 1,
            color = 1,
            id = 1
        )


        // When & Then
        try {
            addNote(note)
            // Fail the test if no exception is thrown
            assertThat(false).isTrue()
        } catch (e: InvalidNoteException) {
            // Assert that the exception message is as expected
            assertThat(e.message).isEqualTo("The content of the note can't be empty.")
        }
    }

    @Test
    fun `Add Note, should call insertNote on repository when note is valid`() = runBlocking {

        val note = Note(
            title = "title",
            content = "title",
            timestamp = 1,
            color = 1,
        )
        addNote(note)

       // verify(fakeNoteRepository, times(2)).insertNote(note)

        coVerify  {
            fakeNoteRepository.insertNote(note)
        }
        confirmVerified(fakeNoteRepository)
    }

}