package com.example.pagingexampleone.ui.activities.home

import android.content.Context
import android.preference.PreferenceManager
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.pagingexampleone.data.local.preferences.TestRepository
import com.example.pagingexampleone.data.repositories.CatsRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    catsRepo: CatsRepo
) : ViewModel() {

    val localCats = catsRepo.getLocalCats().cachedIn(viewModelScope).stateIn(
        scope = viewModelScope,
        started = SharingStarted.Lazily,
        initialValue = PagingData.empty()
    )
    val remoteCats = catsRepo.getRemoteCats().cachedIn(viewModelScope).stateIn(
        scope = viewModelScope,
        started = SharingStarted.Lazily,
        initialValue = PagingData.empty()
    )
    val mediatorCats = catsRepo.getCatsFromMediator().cachedIn(viewModelScope).stateIn(
        scope = viewModelScope,
        started = SharingStarted.Lazily,
        initialValue = PagingData.empty()
    )

    fun mainVm(context: Context) {
        val obj = TestRepository(
            PreferenceManager.getDefaultSharedPreferences(context)
        )

        obj.updateDark()
    }
}