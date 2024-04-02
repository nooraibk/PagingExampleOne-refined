package com.example.pagingexampleone.views.activities

import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.pagingexampleone.core.bases.BaseActivity
import com.example.pagingexampleone.databinding.ActivityNetworkBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class NetworkActivity : BaseActivity() {

    override val baseViewModel: NetworkViewModel by viewModels()
    private lateinit var binding : ActivityNetworkBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNetworkBinding.inflate(layoutInflater)
        setContentView(binding.root)

        lifecycleScope.launch(Dispatchers.IO) {
            baseViewModel.catsFromNetwork.collect{

            }
        }

    }
}