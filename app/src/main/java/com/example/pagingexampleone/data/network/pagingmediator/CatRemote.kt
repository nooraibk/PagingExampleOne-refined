package com.example.pagingexampleone.data.network.pagingmediator

import com.example.pagingexampleone.core.mappers.toCatData
import com.example.pagingexampleone.core.mappers.toCatEntity
import com.example.pagingexampleone.core.models.CatData
import com.example.pagingexampleone.core.models.DataWrapper
import com.example.pagingexampleone.data.local.db.CatDatabase
import com.example.pagingexampleone.data.local.entities.CatDataEntity
import com.example.pagingexampleone.data.local.preferences.TinyDB
import com.example.pagingexampleone.data.network.CatApi

class CatRemote(
    private val api: CatApi,
    private val db: CatDatabase,
    tinyDb: TinyDB
) : RemoteMediatorWrapper<CatDataEntity>(db, tinyDb) {

    override suspend fun deleteExistingData() {
        db.getCatDao().deleteAll()
        db.getKeysDao().deleteAll()
    }

    override suspend fun insertData(dataList: List<DataWrapper>) {
        db.getCatDao().insertAll(
            dataList.map {
                (it as CatData).toCatEntity()
            }
        )
    }

    override suspend fun remoteDataList(pageSize: Int, page: Int) =
        api.getCatImages(page = page, size = pageSize).map { it.toCatData() }
}