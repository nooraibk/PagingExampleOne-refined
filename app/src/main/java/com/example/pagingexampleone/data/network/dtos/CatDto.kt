package com.example.pagingexampleone.data.network.dtos

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cats") //map to can entity later
data class CatDto(
    @PrimaryKey
    val id: String,
    val url: String
)
