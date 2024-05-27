package com.example.pagingexampleone.domain.usecases

import androidx.paging.map
import com.example.pagingexampleone.core.mappers.toCatData
import com.example.pagingexampleone.data.repositories.CatsRepo
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetRemoteCatsUseCase @Inject constructor(
    private val catsRepo: CatsRepo
) {
    operator fun invoke() =
        catsRepo.getRemoteCats().map {
            it.map { catEntity ->
                catEntity.mapFromDto()
            }
        }
}