package com.example.pagingexampleone.domain.usecases

import androidx.paging.map
import com.example.pagingexampleone.core.mappers.ModelMapper
import com.example.pagingexampleone.core.models.CatModel
import com.example.pagingexampleone.data.local.entities.cat.CatEntity
import com.example.pagingexampleone.data.repositories.CatsRepo
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetMediatorCatsUseCase @Inject constructor(
    private val catsRepo: CatsRepo,
    private val catModelEntityMapper : ModelMapper<CatEntity, CatModel>
) {
    operator fun invoke() =
        catsRepo.getCatsFromMediator().map {
            it.map { catEntity ->
                catModelEntityMapper.mapToModel(catEntity)
            }
        }
}