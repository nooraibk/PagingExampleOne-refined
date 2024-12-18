package com.example.pagingexampleone.data.network.pagingmediator

import com.example.pagingexampleone.data.local.entities.cat.CatEntity
import com.example.pagingexampleone.data.local.preferences.TinyDB
import com.example.pagingexampleone.data.repositories.CatsRepo
import com.example.pagingexampleone.data.repositories.KeysRepo
import javax.inject.Inject

class CatsRemoteMediator @Inject constructor(
    tinyDb: TinyDB,
    catsRepo: CatsRepo,
    keysRepo: KeysRepo,
) : BaseRemoteMediator<CatEntity>(
    tinyDb,
    catsRepo,
    keysRepo
)