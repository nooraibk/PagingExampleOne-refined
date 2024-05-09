package com.example.pagingexampleone.ui.fragments.catslist

import androidx.lifecycle.lifecycleScope
import com.example.pagingexampleone.core.Constants.CATS_DATA_TYPE
import com.example.pagingexampleone.core.bases.BaseFragment
import com.example.pagingexampleone.core.utils.DataType
import com.example.pagingexampleone.databinding.FragmentCatsListBinding
import com.example.pagingexampleone.ui.adapters.CatsPagingDataAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CatsListFragment : BaseFragment<FragmentCatsListBinding>(
    FragmentCatsListBinding::inflate
) {

    private val catsAdapter by lazy { CatsPagingDataAdapter() }

    override fun viewInitialized() {
        binding.apply {
            initAdapter(
                recyclerView = recyclerView,
                progressBar = progressBar,
                buttonRetry = buttonRetry,
                catsAdapter = catsAdapter
            )
            lifecycleScope.launch(Dispatchers.IO) {
                viewModel.setCats(arguments?.getSerializable(CATS_DATA_TYPE) as DataType)
                    .collectLatest {
                        catsAdapter.submitData(it)
                    }
            }
        }
    }

}