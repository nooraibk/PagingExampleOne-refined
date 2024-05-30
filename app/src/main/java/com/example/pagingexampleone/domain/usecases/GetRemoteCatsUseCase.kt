package com.example.pagingexampleone.domain.usecases

import androidx.paging.map
import com.example.pagingexampleone.core.mappers.DtoMapper
import com.example.pagingexampleone.core.models.Cat
import com.example.pagingexampleone.data.network.dtos.cat.CatDto
import com.example.pagingexampleone.data.network.dtos.cat.CatDtoMapper
import com.example.pagingexampleone.data.repositories.CatsRepo
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetRemoteCatsUseCase @Inject constructor(
    private val catsRepo: CatsRepo,
    private val catDtoMapper : DtoMapper<CatDto, Cat>
) {
    operator fun invoke() =
        catsRepo.getRemoteCats().map {
            it.map { catDto ->
                catDtoMapper.mapFromDto(catDto)
            }
        }
}