package com.example.pagingexampleone.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.pagingexampleone.core.models.DataWrapper

@Entity(tableName = "cats")
data class CatDataEntity(
    @PrimaryKey
    override val id: String,
    val url: String
) : DataWrapper()
