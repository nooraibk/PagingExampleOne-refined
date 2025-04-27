package com.example.pagingexampleone.data.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "remote_keys")
data class RemoteKeyEntity(
    @PrimaryKey
    @ColumnInfo("cat_id")
    val id: String,
    @ColumnInfo("previous_key")
    val prevKey: Int?,
    @ColumnInfo("next_key")
    val nextKey: Int?
)