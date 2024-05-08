package com.example.pagingexampleone.data.repositories

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.pagingexampleone.core.Constants.PAGE_SIZE
import com.example.pagingexampleone.data.local.db.CatDatabase
import com.example.pagingexampleone.data.local.entities.CatDataEntity
import com.example.pagingexampleone.data.local.entities.CatEntity
import com.example.pagingexampleone.data.local.preferences.TinyDB
import com.example.pagingexampleone.data.network.CatApi
import com.example.pagingexampleone.data.network.pagingsource.CatPaging
import com.example.pagingexampleone.data.network.pagingmediator.CatRemoteMediator
import com.example.pagingexampleone.data.network.dtos.CatDto
import com.example.pagingexampleone.data.network.pagingmediator.CatRemote
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CatsRepo @Inject constructor(
    private val catApi: CatApi,
    private val catDB: CatDatabase,
    private val tinyDB: TinyDB
) {
    fun getNetworkCats(): Flow<PagingData<CatDto>> {
        return Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE,
                maxSize = PAGE_SIZE + (PAGE_SIZE * 2),
                enablePlaceholders = false
            ),
            pagingSourceFactory = { CatPaging(catApi) }
        ).flow
    }

    /*fun getCatsFromNetwork(): Flow<PagingData<CatDto>> {
        return Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE,
                maxSize = PAGE_SIZE + (PAGE_SIZE * 2),
                enablePlaceholders = false
            ),
            pagingSourceFactory = { CatPagingSource(catApi) }
        ).flow
    }*/

    fun gatCatsFromDB(): Flow<PagingData<CatEntity>> {
        return Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE,
                maxSize = PAGE_SIZE + (PAGE_SIZE * 2),
                enablePlaceholders = false
            ),
            pagingSourceFactory = { catDB.getCatDao().getAll() }
        ).flow
    }

    @OptIn(ExperimentalPagingApi::class)
    fun getCatsFromMediator(): Flow<PagingData<CatDataEntity>> {
        return Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE,
                maxSize = PAGE_SIZE + (PAGE_SIZE * 2),
                enablePlaceholders = false
            ),
            remoteMediator = CatRemote(
                catApi,
                catDB,
                tinyDB
            ),
            pagingSourceFactory = { catDB.getCatDao().getAll() }
        ).flow
    }

    suspend fun fillWithDummyCats(dummyCats: List<CatEntity>) {
        catDB.getCatDao().deleteAll()
        catDB.getCatDao().insertAll(dummyCats)
    }

    suspend fun deleteDummyCats() = catDB.getCatDao().deleteAll()
}