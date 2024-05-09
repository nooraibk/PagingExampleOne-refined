package com.example.pagingexampleone.core.mappers

import com.example.pagingexampleone.core.models.Cat
import com.example.pagingexampleone.data.local.entities.CatEntity
import com.example.pagingexampleone.data.network.dtos.CatDto

fun CatDto.toCatData() = Cat(
    id = id, url = url
)

fun Cat.toCatDataEntity() = CatEntity(
    id = id, url = url
)

fun CatEntity.toCat() = Cat(
    id = id, url = url
)