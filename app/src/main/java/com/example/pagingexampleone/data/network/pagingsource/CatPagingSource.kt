package com.example.pagingexampleone.data.network.pagingsource

import android.net.http.HttpException
import android.os.Build
import androidx.annotation.RequiresExtension
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.pagingexampleone.core.Constants.STARTING_PAGE_INDEX
import com.example.pagingexampleone.data.network.CatApi
import com.example.pagingexampleone.data.network.dtos.CatDto
import kotlinx.coroutines.delay
import java.io.IOException

class CatPagingSource(private val catApi: CatApi) : PagingSource<Int, CatDto>() {
    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, CatDto> {
        val page = params.key ?: STARTING_PAGE_INDEX
        return try {
            val response = catApi.getCatImages(page = page, size = params.loadSize)
            delay(2000L)
            LoadResult.Page(
                data = response,
                prevKey = if (page == STARTING_PAGE_INDEX) null else page - 1,
                nextKey = if (response.isEmpty()) null else page + 1
            )
        } catch (e: IOException) {
            LoadResult.Error(e)
        } catch (e: HttpException) {
            LoadResult.Error(e)
        }

    }

    override fun getRefreshKey(state: PagingState<Int, CatDto>): Int? {
        return state.anchorPosition?.let {
            state.closestPageToPosition(it)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(it)?.nextKey?.minus(1)
        }
    }
}