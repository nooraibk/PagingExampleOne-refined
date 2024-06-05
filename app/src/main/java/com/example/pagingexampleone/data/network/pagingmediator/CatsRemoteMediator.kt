package com.example.pagingexampleone.data.network.pagingmediator

import com.example.pagingexampleone.core.mappers.ModelMapper
import com.example.pagingexampleone.core.models.CatModel
import com.example.pagingexampleone.core.models.DataModel
import com.example.pagingexampleone.data.local.db.CatDatabase
import com.example.pagingexampleone.data.local.entities.cat.CatEntity
import com.example.pagingexampleone.data.local.preferences.TinyDB
import com.example.pagingexampleone.data.network.CatsApi
import com.example.pagingexampleone.data.network.dtos.cat.CatDto

class CatsRemoteMediator(
    private val api: CatsApi,
    private val db: CatDatabase,
    tinyDb: TinyDB,
    private val catModelEntityMapper: ModelMapper<CatEntity, CatModel>,
    private val catModelModelMapper: ModelMapper<CatDto, CatModel>
) : BaseRemoteMediator<CatEntity>(db, tinyDb) {

    override suspend fun deleteExistingData() {
        db.getCatDao().deleteAll()
        db.getKeysDao().deleteAll()
    }

    override suspend fun insertData(dataList: List<DataModel>) =
        db.getCatDao().insertAll(
            dataList.map { catModelEntityMapper.mapFromDomain(it as CatModel) }
        )

    override suspend fun remoteDataList(pageSize: Int, page: Int) =
        api.getCatImages(page = page, size = pageSize).map { catModelModelMapper.mapToModel(it) }
}