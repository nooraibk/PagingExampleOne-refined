package com.example.pagingexampleone.data.network.pagingmediator

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.pagingexampleone.core.PREF_LAST_DATA_FETCHED_DATE
import com.example.pagingexampleone.core.STARTING_PAGE_INDEX
import com.example.pagingexampleone.core.calculateAndCheckTimeOf
import com.example.pagingexampleone.core.logIt
import com.example.pagingexampleone.core.mappers.ModelMapper
import com.example.pagingexampleone.domain.models.CatModel
import com.example.pagingexampleone.data.local.db.CatDatabase
import com.example.pagingexampleone.data.local.entities.RemoteKeyEntity
import com.example.pagingexampleone.data.local.entities.cat.CatEntity
import com.example.pagingexampleone.data.local.preferences.TinyDB
import com.example.pagingexampleone.data.network.CatsApi
import com.example.pagingexampleone.data.network.dtos.cat.CatDto
import java.io.IOException

@OptIn(ExperimentalPagingApi::class)
class CatRemoteMediator(
    private val api: CatsApi,
    private val db: CatDatabase,
    private val tinyDb: TinyDB,
    private val entityMapper: ModelMapper<CatEntity, CatModel>,
    private val modelMapper: ModelMapper<CatDto, CatModel>,
) : RemoteMediator<Int, CatEntity>() {

    override suspend fun initialize(): InitializeAction {
        val currentTime = System.currentTimeMillis()
        val savedTime = tinyDb.getLong(PREF_LAST_DATA_FETCHED_DATE,-1)
        "$currentTime $savedTime, ${
            System.currentTimeMillis() calculateAndCheckTimeOf savedTime
        }".logIt("Time:: ")
        return if (System.currentTimeMillis() calculateAndCheckTimeOf savedTime) {
            "Launch Initial".logIt("LaunchInitial:: ")
            tinyDb.putLong(PREF_LAST_DATA_FETCHED_DATE, System.currentTimeMillis())
            InitializeAction.LAUNCH_INITIAL_REFRESH
        } else {
            "Skip Initial".logIt("SkipInitial:: ")
            InitializeAction.SKIP_INITIAL_REFRESH
        }
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, CatEntity>
    ): MediatorResult {
        val pageKeyData = getKeyPageData(
            loadType,
            state
        ) // to determine which page we need to load based on the load type
        val page = when (pageKeyData) { //will be page or page data

            is MediatorResult.Success -> {
                return pageKeyData
            }

            else -> {
                pageKeyData as Int
            }
        }

        try {
            val response = api.getCatImages(page = page, size = state.config.pageSize)
                .map { modelMapper.mapToModel(it) }
            val isEndOfList = response.isEmpty()
            db.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    db.getCatDao().deleteAll()
                    db.getKeysDao().deleteAll()
                }

                val prevKey = if (page == STARTING_PAGE_INDEX) null else page - 1
                val nextKey = if (isEndOfList) null else page + 1
                val keys = response.map {
                    RemoteKeyEntity(it.id, prevKey = prevKey, nextKey = nextKey)
                }
                db.getKeysDao().insertAll(keys)
                db.getCatDao().insertAll(response.map { entityMapper.mapFromDomain(it) })
//                db.getCatDao().insertCatWithLimit(response)
            }
            return MediatorResult.Success(endOfPaginationReached = isEndOfList)
        } catch (e: IOException) {
            return MediatorResult.Error(e)
        }/*catch (e : HttpException){
            return MediatorResult.Error(e)
        }*/
    }

    private suspend fun getKeyPageData(
        loadType: LoadType,
        state: PagingState<Int, CatEntity>
    ): Any { //can return page number or page result as MediatorResult.Success
        return when (loadType) {
            LoadType.REFRESH -> { //when the data is called for first time or invalidated, hence refresh
                val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                remoteKeys?.nextKey?.minus(1) ?: STARTING_PAGE_INDEX
            }

            LoadType.APPEND -> { //to load data at the end of the current data set, i.e, load next data
                val remoteKeys = getLastRemoteKey(state)
                val nextKey = remoteKeys?.nextKey
                return nextKey ?: MediatorResult.Success(endOfPaginationReached = false)
            }

            LoadType.PREPEND -> { //to load data at the start of the current data set, i.q, load previous data
                val remoteKeys = getFirstRemoteKey(state)
                val previousKey = remoteKeys?.prevKey
                return previousKey ?: MediatorResult.Success(endOfPaginationReached = false)
            }
        }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(state: PagingState<Int, CatEntity>): RemoteKeyEntity? { //fetches closest key values (which is remote key entity) from db against $closest item (which is retrieved by anchor position)
        return state.anchorPosition?.let { anchorPos ->
            state.closestItemToPosition(anchorPos)?.id?.let { catId ->
                db.getKeysDao().getKeysByDataId(catId)
            }
        }
    }

    private suspend fun getLastRemoteKey(state: PagingState<Int, CatEntity>): RemoteKeyEntity? {
        val lastPageData = state.pages.lastOrNull { //to retrieve last key and make sure it has data
            it.data.isNotEmpty()
        }
        val lastCat = lastPageData?.data?.lastOrNull()
        return lastCat?.let { catDto ->
            db.getKeysDao().getKeysByDataId(catDto.id)
        }
    }

    private suspend fun getFirstRemoteKey(state: PagingState<Int, CatEntity>): RemoteKeyEntity? {
        val firstPageData =
            state.pages.firstOrNull {//to retrieve first key and make sure it has data
                it.data.isNotEmpty()
            }

        val firstCat = firstPageData?.data?.firstOrNull()
        return firstCat?.let { catDto ->
            db.getKeysDao().getKeysByDataId(catDto.id)
        }
    }
}