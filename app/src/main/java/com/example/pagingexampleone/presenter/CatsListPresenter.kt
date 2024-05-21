package com.example.pagingexampleone.presenter

import com.example.pagingexampleone.views.CatsListView

class CatsListPresenter(private val catsListView : CatsListView) {

    fun onResume() {
        catsListView.showLoader()
    }
}