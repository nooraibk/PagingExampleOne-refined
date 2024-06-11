package com.example.pagingexampleone.data.network.pagingmediator

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.example.pagingexampleone.core.PREF_LAST_DATA_FETCHED_DATE
import com.example.pagingexampleone.core.STARTING_PAGE_INDEX
import com.example.pagingexampleone.core.calculateAndCheckTimeFor
import com.example.pagingexampleone.core.mappers.ModelMapper
import com.example.pagingexampleone.data.local.entities.RemoteKeyEntity
import com.example.pagingexampleone.data.local.entities.cat.CatEntity
import com.example.pagingexampleone.data.local.preferences.TinyDB
import com.example.pagingexampleone.data.network.dtos.cat.CatDto
import com.example.pagingexampleone.data.repositories.CatsRepo
import com.example.pagingexampleone.data.repositories.KeysRepo
import com.example.pagingexampleone.domain.models.CatModel
import com.example.pagingexampleone.domain.models.DataModel
import retrofit2.HttpException
import java.io.IOException

@OptIn(ExperimentalPagingApi::class)
abstract class BaseRemoteMediator<T : DataModel>(
    private val tinyDb: TinyDB,
    private val catsRepo: CatsRepo,
    private val keysRepo: KeysRepo
) : RemoteMediator<Int, T>() {

    override suspend fun initialize(): InitializeAction {
        return if (shouldFetchNewData()) {
            tinyDb.putLong(PREF_LAST_DATA_FETCHED_DATE, System.currentTimeMillis())
            InitializeAction.LAUNCH_INITIAL_REFRESH
        } else {
            InitializeAction.SKIP_INITIAL_REFRESH
        }
    }

    private fun shouldFetchNewData(): Boolean {
        val currentTime = System.currentTimeMillis()
        val savedTime = tinyDb.getLong(PREF_LAST_DATA_FETCHED_DATE, -1)
        return currentTime calculateAndCheckTimeFor savedTime
    }

    override suspend fun load(loadType: LoadType, state: PagingState<Int, T>): MediatorResult {
        val page = when (val pageData = getKeyPageData(loadType, state)) {
            is MediatorResult.Success -> {
                return pageData
            }

            else -> {
                pageData as Int
            }
        }

        return try {
            val cats =
                catsRepo.getPagedRemoteCats(page = page, pageSize = state.config.pageSize)
            val isEndOfResult = cats.isEmpty()

            if (loadType == LoadType.REFRESH) {
                catsRepo.deleteAll()
                keysRepo.deleteAll()
            }

            val prevKey = if (page == STARTING_PAGE_INDEX) null else page - 1
            val nextKey = if (isEndOfResult) null else page + 1
            keysRepo.insertKeys(cats, prevKey, nextKey)
            catsRepo.insertCats(cats)

            MediatorResult.Success(endOfPaginationReached = isEndOfResult)
        } catch (e: IOException) {
            MediatorResult.Error(e)
        } catch (e: HttpException) {
            MediatorResult.Error(e)
        }
    }

    private suspend fun getKeyPageData(loadType: LoadType, state: PagingState<Int, T>): Any {
        return when (loadType) {
            LoadType.REFRESH -> {
                val remoteKeys = this getRemoteKeyClosestToCurrentPosition state
                remoteKeys?.nextKey?.minus(1) ?: STARTING_PAGE_INDEX
            }

            LoadType.APPEND -> {
                val remoteKeys = this getLastRemoteKey state
                val nextKey = remoteKeys?.nextKey
                return nextKey ?: MediatorResult.Success(endOfPaginationReached = false)
            }

            LoadType.PREPEND -> {
                val remoteKeys = this getFirstRemoteKey state
                val previousKey = remoteKeys?.prevKey
                return previousKey ?: MediatorResult.Success(endOfPaginationReached = false)
            }
        }
    }

    private suspend infix fun getRemoteKeyClosestToCurrentPosition(state: PagingState<Int, T>): RemoteKeyEntity? {
        return state.anchorPosition?.let { anchorPos ->
            state.closestItemToPosition(anchorPos)?.id?.let { dataId ->
                keysRepo.getKeysByDataId(dataId)
            }
        }
    }

    private suspend infix fun getLastRemoteKey(state: PagingState<Int, T>): RemoteKeyEntity? {
        val lastPageData = state.pages.lastOrNull { it.data.isNotEmpty() }
        val lastDataItem = lastPageData?.data?.lastOrNull()
        return lastDataItem?.let { data ->
            keysRepo.getKeysByDataId(data.id)
        }
    }

    private suspend infix fun getFirstRemoteKey(state: PagingState<Int, T>): RemoteKeyEntity? {
        val firstPageData = state.pages.firstOrNull { it.data.isNotEmpty() }
        val firstDataItem = firstPageData?.data?.firstOrNull()
        return firstDataItem?.let { data ->
            keysRepo.getKeysByDataId(data.id)
        }
    }
}
