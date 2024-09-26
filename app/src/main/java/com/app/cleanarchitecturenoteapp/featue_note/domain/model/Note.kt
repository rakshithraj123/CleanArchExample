package com.app.cleanarchitecturenoteapp.featue_note.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.app.cleanarchitecturenoteapp.ui.theme.BabyBlue
import com.app.cleanarchitecturenoteapp.ui.theme.LightGreen
import com.app.cleanarchitecturenoteapp.ui.theme.RedOrange
import com.app.cleanarchitecturenoteapp.ui.theme.RedPink
import com.app.cleanarchitecturenoteapp.ui.theme.Violet

@Entity
data class Note(
    val title : String,
    val content : String,
    val timestamp : Long,
    val color : Int,
    @PrimaryKey val id : Int? = null
){
    companion object{
        val noteColors = listOf(LightGreen,RedOrange,Violet,RedPink,BabyBlue)
    }
}

class InvalidNoteException(message : String ) : Exception(message)
