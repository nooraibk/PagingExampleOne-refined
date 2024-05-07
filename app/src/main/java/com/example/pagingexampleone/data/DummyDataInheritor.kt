package com.example.pagingexampleone.data

data class DummyDataInheritor (
    override val id: String,
    val title : String
) : DataWrapper()