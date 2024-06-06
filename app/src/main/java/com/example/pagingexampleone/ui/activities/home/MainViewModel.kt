package com.example.pagingexampleone.ui.activities.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.pagingexampleone.domain.usecases.GetLocalCatsUseCase
import com.example.pagingexampleone.domain.usecases.GetMediatorCatsUseCase
import com.example.pagingexampleone.domain.usecases.GetRemoteCatsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    getRemoteCatsUseCase: GetRemoteCatsUseCase,
    getLocalCatsUseCase: GetLocalCatsUseCase,
    getMediatorCatsUseCase: GetMediatorCatsUseCase
) : ViewModel() {

    val localCats = getLocalCatsUseCase().cachedIn(viewModelScope).stateIn(
        scope = viewModelScope,
        started = SharingStarted.Lazily,
        initialValue = PagingData.empty()
    )
    val remoteCats = getRemoteCatsUseCase().cachedIn(viewModelScope).stateIn(
        scope = viewModelScope,
        started = SharingStarted.Lazily,
        initialValue = PagingData.empty()
    )
    val mediatorCats = getMediatorCatsUseCase().cachedIn(viewModelScope).stateIn(
        scope = viewModelScope,
        started = SharingStarted.Lazily,
        initialValue = PagingData.empty()
    )

}