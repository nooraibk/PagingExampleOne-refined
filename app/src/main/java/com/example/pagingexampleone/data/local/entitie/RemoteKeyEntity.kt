package com.example.pagingexampleone.data.local.entitie

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "remote_keys")
data class RemoteKeyEntity(
    @PrimaryKey
    val catId: String,
    val prevKey: Int?,
    val nextKey: Int?
)