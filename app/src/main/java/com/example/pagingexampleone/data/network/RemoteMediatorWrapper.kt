package com.example.pagingexampleone.data.network

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.example.pagingexampleone.core.Constants.STARTING_PAGE_INDEX
import com.example.pagingexampleone.core.calculateAndCheckTime
import com.example.pagingexampleone.data.DataWrapper
import com.example.pagingexampleone.data.local.db.CatDatabase
import com.example.pagingexampleone.data.local.entitie.RemoteKeyEntity
import com.example.pagingexampleone.data.local.preferences.PreferencesKey
import com.example.pagingexampleone.data.local.preferences.TinyDB

@OptIn(ExperimentalPagingApi::class)
abstract class RemoteMediatorWrapper<data : DataWrapper>(
    private val api: CatApi,
    private val db: CatDatabase,
    private val tinyDb: TinyDB
) : RemoteMediator<Int, DataWrapper>() {
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
        state: PagingState<Int, DataWrapper>
    ): MediatorResult {
        val pageData = getKeyPageData(loadType, state)
    }

    private suspend fun getKeyPageData(loadType: LoadType, state: PagingState<Int, DataWrapper>): Any {
        return when (loadType) {
            LoadType.REFRESH -> {
                val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                remoteKeys?.nextKey?.minus(1) ?: STARTING_PAGE_INDEX
            }

            LoadType.APPEND -> {
                val remoteKeys = getLastRemoteKey(state)
            }

            LoadType.PREPEND -> {

            }
        }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(state: PagingState<Int, DataWrapper>): RemoteKeyEntity? {
        return state.anchorPosition?.let { anchorPos ->
            state.closestItemToPosition(anchorPos)?.id?.let { dataId ->
                db.getKeysDao().remoteKeysByCatId(dataId)
            }
        }
    }

    private suspend fun getLastRemoteKey(state: PagingState<Int, DataWrapper>): RemoteKeyEntity? {
        val lastPageData = state.pages.lastOrNull{
            it.data.isNotEmpty()
        }
        val lastDataItem = lastPageData?.data?.lastOrNull()
        return lastDataItem?.let {  data ->
            db.getKeysDao().remoteKeysByCatId(data.id)
        }
    }
}