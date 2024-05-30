package com.example.pagingexampleone.domain.usecases

import androidx.paging.map
import com.example.pagingexampleone.core.mappers.EntityMapper
import com.example.pagingexampleone.core.models.Cat
import com.example.pagingexampleone.data.local.entities.cat.CatEntity
import com.example.pagingexampleone.data.local.entities.cat.CatEntityMapper
import com.example.pagingexampleone.data.repositories.CatsRepo
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetMediatorCatsUseCase @Inject constructor(
    private val catsRepo: CatsRepo,
    private val catEntityMapper : EntityMapper<CatEntity, Cat>
) {
    operator fun invoke() =
        catsRepo.getCatsFromMediator().map {
            it.map { catEntity ->
                catEntityMapper.mapFromEntity(catEntity)
            }
        }
}