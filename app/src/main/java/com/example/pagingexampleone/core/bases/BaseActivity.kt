package com.example.pagingexampleone.core.bases

import androidx.appcompat.app.AppCompatActivity

abstract class BaseActivity : AppCompatActivity() {
    abstract val baseViewModel: BaseViewModel
}