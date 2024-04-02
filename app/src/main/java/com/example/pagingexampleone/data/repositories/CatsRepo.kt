package com.example.pagingexampleone.data.repositories

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.pagingexampleone.data.network.CatApi
import com.example.pagingexampleone.data.network.CatPagingSource
import com.example.pagingexampleone.data.network.dtos.CatDto
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

private const val PAGE_SIZE = 30
class CatsRepo @Inject constructor(private val catApi: CatApi) {
    fun getCatsFromNetwork() : Flow<PagingData<CatDto>> {
        return Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE,
                maxSize = PAGE_SIZE + (PAGE_SIZE * 2),
                enablePlaceholders = false
            ),
            pagingSourceFactory = {CatPagingSource(catApi)}
        ).flow
    }
}