package com.example.pagingexampleone.views.activities.networkanddb

import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.pagingexampleone.core.bases.BaseViewModel
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
            catsRepo.deleteDummyCats()
        }
    }
    val catsFromNetwork = catsRepo.getCatsFromMediator().cachedIn(viewModelScope)
}