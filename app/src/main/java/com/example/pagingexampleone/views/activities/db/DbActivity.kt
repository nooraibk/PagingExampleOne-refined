package com.example.pagingexampleone.views.activities.db

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import com.example.pagingexampleone.R
import com.example.pagingexampleone.core.bases.BaseActivity
import com.example.pagingexampleone.databinding.ActivityDbBinding
import com.example.pagingexampleone.databinding.ActivityNetworkBinding
import com.example.pagingexampleone.views.adapters.CatsLoadStateAdapter
import com.example.pagingexampleone.views.adapters.NetworkDataAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DbActivity : BaseActivity() {

    override val baseViewModel: DatabaseViewModel by viewModels()
    private lateinit var binding : ActivityDbBinding
    private val networkAdapter = NetworkDataAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDbBinding.inflate(layoutInflater)
        setContentView(binding.root)

        baseViewModel.fillWithDummyCats()
        lifecycleScope.launch {
            initAdapter()
            baseViewModel.catsFromDatabase.collect{
                networkAdapter.submitData(it)
            }
        }
    }


    private fun initAdapter(){
        binding.recyclerView.adapter = networkAdapter.withLoadStateHeaderAndFooter(
            header = CatsLoadStateAdapter{networkAdapter.retry()},
            footer = CatsLoadStateAdapter{networkAdapter.retry()}
        )
        networkAdapter.addLoadStateListener { combinedLoadStates ->
            binding.apply{
                recyclerView.isVisible = combinedLoadStates.refresh is LoadState.NotLoading
                progressBar.isVisible = combinedLoadStates.refresh is LoadState.Loading
                buttonRetry.isVisible = combinedLoadStates.refresh is LoadState.Error
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
            Toast.makeText(this@DbActivity, "${it?.error}", Toast.LENGTH_SHORT).show()
        }
    }
}