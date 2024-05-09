package com.example.pagingexampleone.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.pagingexampleone.data.local.entities.RemoteKeyEntity

@Dao
interface DataKeysDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(remoteKey: List<RemoteKeyEntity>)

    @Query("SELECT * FROM remote_keys WHERE cat_id = :id")
    suspend fun getKeysByDataId(id: String): RemoteKeyEntity?

    @Query("DELETE FROM remote_keys")
    suspend fun deleteAll()
}