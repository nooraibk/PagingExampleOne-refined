package com.example.pagingexampleone.data.repositories

import androidx.paging.PagingData
import com.example.pagingexampleone.data.local.entities.cat.CatEntity
import com.example.pagingexampleone.data.network.dtos.cat.CatDto
import com.example.pagingexampleone.domain.models.CatModel
import com.example.pagingexampleone.domain.models.DataModel
import kotlinx.coroutines.flow.Flow

interface CatsRepo {
    fun getRemoteCats(): Flow<PagingData<CatModel>>
    fun getLocalCats(): Flow<PagingData<CatModel>>
    fun getCatsFromMediator(): Flow<PagingData<CatModel>>
    suspend fun insertCats(cats: List<CatModel>)
    suspend fun deleteAll()
    suspend fun getPagedRemoteCats(pageSize: Int, page: Int): List<CatModel>
}
