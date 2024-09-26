package com.app.cleanarchitecturenoteapp.featue_note.domain.util

sealed class OrderType {
    object Ascending : OrderType()
    object Descending  : OrderType()
}