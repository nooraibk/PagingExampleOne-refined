package com.example.pagingexampleone.presenter

import com.example.pagingexampleone.core.utils.DataType
import com.example.pagingexampleone.views.HomeView

class HomePresenter(private var homeView : HomeView?) {

    fun moveToList(dataType: DataType){
        homeView?.moveToList(dataType)
    }

    fun onDestroy() {
        homeView = null
    }
}