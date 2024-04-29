package com.example.pagingexampleone.ui.activities.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.pagingexampleone.core.models.Cat
import com.example.pagingexampleone.core.utils.DataType
import com.example.pagingexampleone.data.local.entitie.CatEntity
import com.example.pagingexampleone.data.repositories.CatsRepo
import com.example.pagingexampleone.domain.usecases.GetCatsFromDatabaseUseCase
import com.example.pagingexampleone.domain.usecases.GetCatsFromMediatorUseCase
import com.example.pagingexampleone.domain.usecases.GetCatsFromNetworkUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getCatsFromNetworkUseCase: GetCatsFromNetworkUseCase,
    private val getCatsFromDatabaseUseCase: GetCatsFromDatabaseUseCase,
    private val getCatsFromMediatorUseCase: GetCatsFromMediatorUseCase,
    private val catsRepo: CatsRepo
) : ViewModel()  {

    fun setCats(catsType: DataType) : Flow<PagingData<Cat>> {
        return when (catsType) {
            DataType.Local -> {
                getCatsFromDatabaseUseCase()
            }

            DataType.Network -> {
                getCatsFromNetworkUseCase()
            }

            DataType.Mediator -> {
                getCatsFromMediatorUseCase()
            }
        }
    }

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

    fun fillWithDummyCats(){
        val dummyCats = mutableListOf<CatEntity>()
        for (i in 0..100){
            dummyCats.add(CatEntity(i.toString(), "https://cdn2.thecatapi.com/images/ja.jpg"))
        }
        viewModelScope.launch {
            catsRepo.fillWithDummyCats(dummyCats)
        }
    }
}