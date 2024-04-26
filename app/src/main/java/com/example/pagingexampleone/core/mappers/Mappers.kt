package com.example.pagingexampleone.core.mappers

import com.example.pagingexampleone.core.models.Cat
import com.example.pagingexampleone.data.local.entitie.CatEntity
import com.example.pagingexampleone.data.network.dtos.CatDto

fun CatDto.toCat() = Cat(
    id = id, url = url
)

fun Cat.toCatEntity() = CatEntity(
    id = id, url = url
)

fun CatEntity.toCat() = Cat(
    id = id, url = url
)