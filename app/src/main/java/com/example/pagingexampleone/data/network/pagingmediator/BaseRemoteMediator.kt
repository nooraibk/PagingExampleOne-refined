package com.example.pagingexampleone.data.network.pagingmediator

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.pagingexampleone.core.Constants.STARTING_PAGE_INDEX
import com.example.pagingexampleone.core.calculateAndCheckTime
import com.example.pagingexampleone.core.models.DataModel
import com.example.pagingexampleone.data.local.db.CatDatabase
import com.example.pagingexampleone.data.local.entities.RemoteKeyEntity
import com.example.pagingexampleone.data.local.preferences.PreferencesKey
import com.example.pagingexampleone.data.local.preferences.TinyDB

@OptIn(ExperimentalPagingApi::class)
abstract class BaseRemoteMediator<T : DataModel>(
    private val db: CatDatabase,
    private val tinyDb: TinyDB
) : RemoteMediator<Int, T>() {
    override suspend fun initialize(): InitializeAction {
        return if (
            System.currentTimeMillis()
                .calculateAndCheckTime(tinyDb.getLong(PreferencesKey.LAST_DATA_FETCHED_DATE, -1))
        ) {
            tinyDb.putLong(PreferencesKey.LAST_DATA_FETCHED_DATE, System.currentTimeMillis())
            InitializeAction.LAUNCH_INITIAL_REFRESH
        } else {
            InitializeAction.SKIP_INITIAL_REFRESH
        }
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, T>
    ): MediatorResult {
        val page = when (val pageData = getKeyPageData(loadType, state)){
            is MediatorResult.Success -> {
                return pageData
            }
            else -> {
                pageData as Int
            }
        }

        try {
            val remoteResponse = remoteDataList(page = page, pageSize = state.config.pageSize)
            val isEndOfResult = remoteResponse.isEmpty()
            db.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    deleteExistingData()
                }

                val prevKey = if (page == STARTING_PAGE_INDEX) null else page - 1
                val nextKey = if (isEndOfResult) null else page + 1
                val dataKeys = remoteResponse.map {
                    RemoteKeyEntity(it.id, prevKey, nextKey)
                }
                db.getKeysDao().insertAll(dataKeys)
                insertData(remoteResponse)
            }
            return MediatorResult.Success(endOfPaginationReached = isEndOfResult)
        }catch (e: Exception) {
            return MediatorResult.Error(e)
        }
    }

    abstract suspend fun deleteExistingData()

    abstract suspend fun insertData(dataList : List<DataModel>)

    abstract suspend fun remoteDataList(pageSize : Int, page : Int): List<DataModel>

    private suspend fun getKeyPageData(loadType: LoadType, state: PagingState<Int, T>): Any {
        return when (loadType) {
            LoadType.REFRESH -> {
                val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                remoteKeys?.nextKey?.minus(1) ?: STARTING_PAGE_INDEX
            }

            LoadType.APPEND -> {
                val remoteKeys = getLastRemoteKey(state)
                val nextKey = remoteKeys?.nextKey
                return nextKey ?: MediatorResult.Success(endOfPaginationReached = false)
            }

            LoadType.PREPEND -> {
                val remoteKeys = getFirstRemoteKey(state)
                val previousKey = remoteKeys?.prevKey
                return previousKey ?: MediatorResult.Success(endOfPaginationReached = false)
            }
        }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(state: PagingState<Int, T>): RemoteKeyEntity? {
        return state.anchorPosition?.let { anchorPos ->
            state.closestItemToPosition(anchorPos)?.id?.let { dataId ->
                db.getKeysDao().getKeysByDataId(dataId)
            }
        }
    }

    private suspend fun getLastRemoteKey(state: PagingState<Int, T>): RemoteKeyEntity? {
        val lastPageData = state.pages.lastOrNull{
            it.data.isNotEmpty()
        }
        val lastDataItem = lastPageData?.data?.lastOrNull()
        return lastDataItem?.let {  data ->
            db.getKeysDao().getKeysByDataId(data.id)
        }
    }

    private suspend fun getFirstRemoteKey(state: PagingState<Int, T>): RemoteKeyEntity? {
        val firstPageData = state.pages.firstOrNull{
            it.data.isNotEmpty()
        }
        val firstDataItem = firstPageData?.data?.firstOrNull()
        return firstDataItem?.let { data ->
            db.getKeysDao().getKeysByDataId(data.id)
        }
    }
}