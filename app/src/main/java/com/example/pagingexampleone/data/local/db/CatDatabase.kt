package com.example.pagingexampleone.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.pagingexampleone.data.local.dao.CatDao
import com.example.pagingexampleone.data.local.dao.RemoteKeyDao
import com.example.pagingexampleone.data.local.entitie.RemoteKeyEntity
import com.example.pagingexampleone.data.network.dtos.CatDto

@Database(version = 1, entities = [CatDto::class, RemoteKeyEntity::class])
abstract class CatDatabase : RoomDatabase() {
    abstract fun getCatDao(): CatDao
    abstract fun getKeysDao(): RemoteKeyDao
}