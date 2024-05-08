package com.example.pagingexampleone.core.mappers

import com.example.pagingexampleone.core.models.Cat
import com.example.pagingexampleone.core.models.CatData
import com.example.pagingexampleone.data.local.entities.CatDataEntity
import com.example.pagingexampleone.data.local.entities.CatEntity
import com.example.pagingexampleone.data.network.dtos.CatDataDto
import com.example.pagingexampleone.data.network.dtos.CatDto

fun CatDto.toCat() = Cat(
    id = id, url = url
)

fun CatDataDto.toCatData() = CatData(
    id = id, url = url
)

fun Cat.toCatEntity() = CatEntity(
    id = id, url = url
)

fun CatData.toCatEntity() = CatEntity(
    id = id, url = url
)

fun CatEntity.toCat() = Cat(
    id = id, url = url
)

fun CatDataEntity.toCat() = Cat(
    id = id, url = url
)