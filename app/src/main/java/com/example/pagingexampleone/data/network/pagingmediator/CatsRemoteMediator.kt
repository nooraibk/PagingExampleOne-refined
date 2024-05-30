package com.example.pagingexampleone.data.network.pagingmediator

import com.example.pagingexampleone.core.mappers.DtoMapper
import com.example.pagingexampleone.core.mappers.EntityMapper
import com.example.pagingexampleone.core.models.Cat
import com.example.pagingexampleone.core.models.DataModel
import com.example.pagingexampleone.data.local.db.CatDatabase
import com.example.pagingexampleone.data.local.entities.cat.CatEntity
import com.example.pagingexampleone.data.local.entities.cat.CatEntityMapper
import com.example.pagingexampleone.data.local.preferences.TinyDB
import com.example.pagingexampleone.data.network.CatsApi
import com.example.pagingexampleone.data.network.dtos.cat.CatDto
import com.example.pagingexampleone.data.network.dtos.cat.CatDtoMapper

class CatsRemoteMediator(
    private val api: CatsApi,
    private val db: CatDatabase,
    tinyDb: TinyDB,
    private val catEntityMapper : EntityMapper<CatEntity, Cat>,
    private val catDtoMapper : DtoMapper<CatDto, Cat>
) : BaseRemoteMediator<CatEntity>(db, tinyDb) {

    override suspend fun deleteExistingData() {
        db.getCatDao().deleteAll()
        db.getKeysDao().deleteAll()
    }

    override suspend fun insertData(dataList: List<DataModel>) {
        db.getCatDao().insertAll(
            dataList.map { catEntityMapper.mapToEntity(it as Cat) }
        )
    }

    override suspend fun remoteDataList(pageSize: Int, page: Int) =
        api.getCatImages(page = page, size = pageSize).map { catDtoMapper.mapFromDto(it) }
}