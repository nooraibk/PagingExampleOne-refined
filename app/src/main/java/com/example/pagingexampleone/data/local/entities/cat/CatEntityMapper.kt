package com.example.pagingexampleone.data.local.entities.cat

import com.example.pagingexampleone.core.mappers.ModelMapper
import com.example.pagingexampleone.core.models.CatModel
import javax.inject.Inject

class CatEntityMapper @Inject constructor() : ModelMapper<CatEntity, CatModel> {

    override fun mapToModel(obj: CatEntity) =
        CatModel(
            id = obj.id,
            url = obj.url
        )

    override fun mapFromDomain(domain: CatModel) =
        CatEntity(
            id = domain.id,
            url = domain.url
        )

}
