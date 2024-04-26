package com.example.pagingexampleone.ui.activities.networkanddb

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import com.example.pagingexampleone.core.bases.BaseActivity
import com.example.pagingexampleone.databinding.ActivityNetworkAndDbBinding
import com.example.pagingexampleone.ui.adapters.CatsLoadStateAdapter
import com.example.pagingexampleone.ui.adapters.NetworkDataAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class NetworkAndDbActivity : BaseActivity() {
    override val baseViewModel: NetworkAndDbViewModel by viewModels()
    private lateinit var binding : ActivityNetworkAndDbBinding
    private val networkAdapter = NetworkDataAdapter()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNetworkAndDbBinding.inflate(layoutInflater)
        setContentView(binding.root)

        lifecycleScope.launch {
            initAdapter(true)
            baseViewModel.catsFromNetwork.collect{
                networkAdapter.submitData(it)
            }
        }
    }


    private fun initAdapter(isMediator : Boolean = false){
        binding.recyclerView.adapter = networkAdapter.withLoadStateHeaderAndFooter(
            header = CatsLoadStateAdapter{networkAdapter.retry()},
            footer = CatsLoadStateAdapter{networkAdapter.retry()}
        )
        networkAdapter.addLoadStateListener { combinedLoadStates ->
            val refreshState = if (isMediator) {
                combinedLoadStates.mediator?.refresh
            }else{
                combinedLoadStates.source.refresh
            }
            binding.apply{
                recyclerView.isVisible = refreshState is LoadState.NotLoading
                progressBar.isVisible = refreshState is LoadState.Loading
                buttonRetry.isVisible = refreshState is LoadState.Error
                handleError(combinedLoadStates)
            }
        }
        binding.buttonRetry.setOnClickListener {
            networkAdapter.retry()
        }
    }

    private fun handleError(combinedLoadStates: CombinedLoadStates) {
        val errorState = combinedLoadStates.source.append as? LoadState.Error
            ?: combinedLoadStates.source.prepend as? LoadState.Error

        errorState.let {
            Toast.makeText(this@NetworkAndDbActivity, "${it?.error}", Toast.LENGTH_SHORT).show()
        }
    }
}