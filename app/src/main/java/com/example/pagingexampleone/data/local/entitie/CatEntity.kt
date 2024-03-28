package com.example.pagingexampleone.data.local.entitie

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cats")
data class CatEntity(
    @PrimaryKey
    val id: String,
    val url: String
)
