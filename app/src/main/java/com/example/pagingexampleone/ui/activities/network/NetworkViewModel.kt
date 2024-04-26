package com.example.pagingexampleone.ui.activities.network

import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.pagingexampleone.core.bases.BaseViewModel
import com.example.pagingexampleone.data.repositories.CatsRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class NetworkViewModel @Inject constructor(
    catsRepo: CatsRepo
) : BaseViewModel() {
    val catsFromNetwork = catsRepo.getCatsFromNetwork().cachedIn(viewModelScope)
}