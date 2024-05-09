package com.example.pagingexampleone.domain.usecases

import androidx.paging.map
import com.example.pagingexampleone.core.mappers.toCat
import com.example.pagingexampleone.data.repositories.CatsRepo
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetLocalCatsUseCase @Inject constructor(
    private val catsRepo: CatsRepo
) {
    operator fun invoke() =
        catsRepo.getLocalCats().map {
            it.map { catEntity ->
                catEntity.toCat()
            }
        }
}