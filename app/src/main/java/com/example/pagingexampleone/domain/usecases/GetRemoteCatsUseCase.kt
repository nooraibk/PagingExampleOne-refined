package com.example.pagingexampleone.domain.usecases

import androidx.paging.map
import com.example.pagingexampleone.core.mappers.ModelMapper
import com.example.pagingexampleone.domain.models.CatModel
import com.example.pagingexampleone.data.network.dtos.cat.CatDto
import com.example.pagingexampleone.data.repositories.CatsRepo
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetRemoteCatsUseCase @Inject constructor(
    private val catsRepo: CatsRepo,
    private val catModelModelMapper : ModelMapper<CatDto, CatModel>
) {
    operator fun invoke() =
        catsRepo.getRemoteCats().map {
            it.map { catDto ->
                catModelModelMapper.mapToModel(catDto)
            }
        }
}