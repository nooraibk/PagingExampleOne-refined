package com.example.pagingexampleone.data.repositories

import androidx.paging.PagingData
import com.example.pagingexampleone.data.local.entities.CatEntity
import com.example.pagingexampleone.data.network.dtos.CatDto
import kotlinx.coroutines.flow.Flow

interface CatsRepo {
    fun getRemoteCats(): Flow<PagingData<CatDto>>
    fun getLocalCats(): Flow<PagingData<CatEntity>>
    fun getCatsFromMediator(): Flow<PagingData<CatEntity>>
    suspend fun insertCats(dummyCats: List<CatEntity>)
    suspend fun deleteCats()
}
