package com.example.pagingexampleone.data.repositories

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.pagingexampleone.core.PAGE_SIZE
import com.example.pagingexampleone.core.mappers.ModelMapper
import com.example.pagingexampleone.data.local.db.CatDatabase
import com.example.pagingexampleone.data.local.entities.cat.CatEntity
import com.example.pagingexampleone.data.local.preferences.TinyDB
import com.example.pagingexampleone.data.network.CatsApi
import com.example.pagingexampleone.data.network.dtos.cat.CatDto
import com.example.pagingexampleone.data.network.pagingmediator.CatsRemoteMediator
import com.example.pagingexampleone.data.network.pagingsource.CatsPaging
import com.example.pagingexampleone.domain.models.CatModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CatsRepoImpl @Inject constructor(
    private val catsApi: CatsApi,
    private val catDB: CatDatabase,
    private val tinyDB: TinyDB,
    private val catModelEntityMapper : ModelMapper<CatEntity, CatModel>,
    private val catModelModelMapper : ModelMapper<CatDto, CatModel>
) : CatsRepo {
    override fun getRemoteCats(): Flow<PagingData<CatDto>> {
        return Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE,
                maxSize = PAGE_SIZE + (PAGE_SIZE * 2),
                enablePlaceholders = false
            ),
            pagingSourceFactory = { CatsPaging(catsApi) }
        ).flow
    }

    override fun getLocalCats(): Flow<PagingData<CatEntity>> {
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
    override fun getCatsFromMediator(): Flow<PagingData<CatEntity>> {
        return Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE,
                maxSize = PAGE_SIZE + (PAGE_SIZE * 2),
                enablePlaceholders = false
            ),
            remoteMediator = CatsRemoteMediator(
                catsApi,
                catDB,
                tinyDB,
                catModelEntityMapper,
                catModelModelMapper
            ),
            pagingSourceFactory = { catDB.getCatDao().getAll() }
        ).flow
    }

    override suspend fun insertCats(dummyCats: List<CatEntity>) {
        catDB.getCatDao().deleteAll()
        catDB.getCatDao().insertAll(dummyCats)
    }

    override suspend fun deleteCats() = catDB.getCatDao().deleteAll()
}