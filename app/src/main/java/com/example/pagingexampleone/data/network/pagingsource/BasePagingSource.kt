package com.example.pagingexampleone.data.network.pagingsource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.pagingexampleone.core.STARTING_PAGE_INDEX
import java.io.IOException

abstract class BasePagingSource<T : Any> : PagingSource<Int, T>(){
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, T> {
        return try {
            val nextPageNumber = params.key ?: STARTING_PAGE_INDEX
            val dataList = dataList(params.loadSize, nextPageNumber)
            val nextKey = if (dataList.size < params.loadSize) null else nextPageNumber + 1
            val prevKey = if (nextPageNumber == STARTING_PAGE_INDEX) null else nextPageNumber - 1

            if (dataList.isEmpty()){
                LoadResult.Error(Exception("No data found"))
            }else{
                LoadResult.Page(dataList, prevKey, nextKey)
            }
        }catch (e: IOException) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, T>): Int? {
        return state.anchorPosition?.let {
            val anchorPage = state.closestPageToPosition(it)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    abstract suspend fun dataList(pageSize : Int, page : Int): List<T>
}