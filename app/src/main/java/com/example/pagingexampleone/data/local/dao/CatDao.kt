package com.example.pagingexampleone.data.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.pagingexampleone.data.local.entities.cat.CatEntity

@Dao
interface CatDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(cats: List<CatEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(cat: CatEntity)

    @Query("SELECT * FROM cats")
    fun getAll(): PagingSource<Int, CatEntity>

    @Query("DELETE FROM cats")
    suspend fun deleteAll()

    @Query("DELETE FROM cats WHERE cat_id = :catId")
    suspend fun deleteCatById(catId: String)

    @Query("DELETE FROM cats WHERE cat_id IN (:catIds)")
    suspend fun deleteCatsByIds(catIds: List<String>)

    @Query("SELECT COUNT(*) FROM cats")
    suspend fun getCatsCount(): Int

}