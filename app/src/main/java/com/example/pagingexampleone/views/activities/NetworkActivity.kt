package com.example.pagingexampleone.views.activities

import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import com.example.pagingexampleone.core.bases.BaseActivity
import com.example.pagingexampleone.databinding.ActivityNetworkBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class NetworkActivity : BaseActivity() {

    override val baseViewModel: NetworkViewModel by viewModels()
    private lateinit var binding : ActivityNetworkBinding
    private val networkAdapter = NetworkDataAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNetworkBinding.inflate(layoutInflater)
        setContentView(binding.root)

        lifecycleScope.launch(Dispatchers.IO) {
            baseViewModel.catsFromNetwork.collect{
                networkAdapter.submitData(it)
            }
        }

    }

    private suspend fun initAdapter(){
        binding.recyclerView.adapter = networkAdapter
        networkAdapter.loadStateFlow.collectLatest { combinedLoadStates ->
            binding.apply{
                recyclerView.isVisible = combinedLoadStates is LoadState.NotLoading
                
            }
        }
    }
}