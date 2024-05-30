package com.example.pagingexampleone.data.local.entities.cat

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.pagingexampleone.core.models.DataModel

@Entity(tableName = "cats")
data class CatEntity(
    @PrimaryKey
    @ColumnInfo("cat_id")
    override val id: String,
    @ColumnInfo("cat_url")
    val url: String
) : DataModel()
