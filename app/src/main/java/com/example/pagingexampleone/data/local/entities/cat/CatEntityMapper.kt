package com.example.pagingexampleone.data.local.entities.cat

import com.example.pagingexampleone.core.mappers.EntityMapper
import com.example.pagingexampleone.core.models.Cat
import javax.inject.Inject

class CatEntityMapper @Inject constructor(): EntityMapper<CatEntity, Cat> {

    override fun mapFromEntity(entity: CatEntity) =
        Cat(
            id = entity.id,
            url = entity.url
        )

    override fun mapToEntity(domain: Cat) =
        CatEntity(
            id = domain.id,
            url = domain.url
        )

}
