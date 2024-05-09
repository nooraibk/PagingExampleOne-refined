package com.example.pagingexampleone.data.network.pagingmediator

import com.example.pagingexampleone.core.mappers.toCatData
import com.example.pagingexampleone.core.mappers.toCatDataEntity
import com.example.pagingexampleone.core.models.Cat
import com.example.pagingexampleone.core.models.DataModel
import com.example.pagingexampleone.data.local.db.CatDatabase
import com.example.pagingexampleone.data.local.entities.CatEntity
import com.example.pagingexampleone.data.local.preferences.TinyDB
import com.example.pagingexampleone.data.network.CatsApi

class CatsRemoteMediator(
    private val api: CatsApi,
    private val db: CatDatabase,
    tinyDb: TinyDB
) : BaseRemoteMediator<CatEntity>(db, tinyDb) {

    override suspend fun deleteExistingData() {
        db.getCatDao().deleteAll()
        db.getKeysDao().deleteAll()
    }

    override suspend fun insertData(dataList: List<DataModel>) {
        db.getCatDao().insertAll(
            dataList.map {
                (it as Cat).toCatDataEntity()
            }
        )
    }

    override suspend fun remoteDataList(pageSize: Int, page: Int) =
        api.getCatImages(page = page, size = pageSize).map { it.toCatData() }
}