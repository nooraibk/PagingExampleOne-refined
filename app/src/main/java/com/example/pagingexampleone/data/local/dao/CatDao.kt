package com.example.pagingexampleone.data.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.pagingexampleone.data.network.dtos.CatDto

@Dao
interface CatDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(cats: List<CatDto>)

    @Query("SELECT * FROM cats")
    fun getAll(): PagingSource<Int, CatDto>

    @Query("DELETE FROM cats")
    suspend fun deleteAll()
}