package com.example.pagingexampleone.core.bases

import androidx.lifecycle.ViewModel
import com.example.pagingexampleone.data.local.preferences.TinyDB
import com.example.pagingexampleone.data.repositories.CatsRepo

abstract class BaseViewModel(private val tinyDB: TinyDB, private val catsRepo: CatsRepo) : ViewModel()