package com.example.pagingexampleone.data.repositories

import com.example.pagingexampleone.data.local.entities.RemoteKeyEntity
import com.example.pagingexampleone.domain.models.DataModel

interface KeysRepo {
    suspend fun insertKeys(dataList: List<DataModel>, prevKey: Int?, nextKey: Int?)
    suspend fun getKeysByDataId(id: String): RemoteKeyEntity?
    suspend fun insertAll(remoteKey: List<RemoteKeyEntity>)
    suspend fun deleteAll()
}