package com.example.pagingexampleone.views.activities.networkanddb

import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.pagingexampleone.core.bases.BaseViewModel
import com.example.pagingexampleone.data.network.dtos.CatDto
import com.example.pagingexampleone.data.repositories.CatsRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NetworkAndDbViewModel @Inject constructor(
    private val catsRepo: CatsRepo
) : BaseViewModel() {
    init {
        viewModelScope.launch {
//            catsRepo.deleteDummyCats()

//            val dummyCats = mutableListOf<CatDto>()
//            for (i in 1..120){
//                dummyCats.add(CatDto(i.toString(), "https://cdn2.thecatapi.com/images/ja.jpg"))
//            }
//            catsRepo.fillWithDummyCats(dummyCats)
        }
    }
    val catsFromNetwork = catsRepo.getCatsFromMediator().cachedIn(viewModelScope)
}