package com.example.pagingexampleone.data.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.pagingexampleone.data.local.entitie.CatEntity

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

    @Query("DELETE FROM cats WHERE id = :catId")
    suspend fun deleteCatById(catId: String)

    @Query("DELETE FROM cats WHERE id IN (:catIds)")
    suspend fun deleteCatsByIds(catIds: List<String>)

    @Query("SELECT COUNT(*) FROM cats")
    suspend fun getCatsCount(): Int

    @Query("SELECT * FROM cats")
    suspend fun getAllCats(): List<CatEntity>

    @Transaction
    suspend fun insertCatWithLimit(cats: List<CatEntity>) {
        val catsCount = getCatsCount()
        val totalRecords = catsCount + cats.size

        if (totalRecords > 150) { //max page limit is 90 so save at least one page items, better to save three pages
            val recordsToDelete = totalRecords - 150 //the number of records that need to be deleted to maintain the limit.
            val catsToDelete = minOf(recordsToDelete, catsCount) //calculates the number of cats from the existing database records that need to be deleted.
            if (catsToDelete > 0) {
                val catsToDeleteIds = getAllCats().take(catsToDelete).map { it.id }
                deleteCatsByIds(catsToDeleteIds)
            }
        }

        insertAll(cats)
    }


}