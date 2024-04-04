package com.example.pagingexampleone.views.activities.db

import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.pagingexampleone.core.bases.BaseViewModel
import com.example.pagingexampleone.data.network.dtos.CatDto
import com.example.pagingexampleone.data.repositories.CatsRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DatabaseViewModel @Inject constructor(
    private val catsRepo: CatsRepo
) : BaseViewModel() {
    val catsFromDatabase = catsRepo.gatCatsFromDB().cachedIn(viewModelScope)

    fun fillWithDummyCats(){
        val dummyCats = mutableListOf<CatDto>()
        for (i in 0..100){
            dummyCats.add(CatDto(i.toString(), "https://cdn2.thecatapi.com/images/ja.jpg"))
        }
        viewModelScope.launch {
            catsRepo.fillWithDummyCats(dummyCats)
        }
    }
}