package com.example.pagingexampleone.ui.fragments.home

import com.example.pagingexampleone.core.bases.BaseFragment
import com.example.pagingexampleone.core.utils.DataType
import com.example.pagingexampleone.databinding.FragmentHomeBinding
import com.example.pagingexampleone.presenter.HomePresenter
import com.example.pagingexampleone.ui.activities.home.MainActivity
import com.example.pagingexampleone.views.HomeView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(
    FragmentHomeBinding::inflate
), HomeView {

    private val homePresenter = HomePresenter(this)

    override fun viewInitialized() {
        binding.apply {
            localCats.setOnClickListener { homePresenter.moveToList(DataType.Local) }
            remoteCats.setOnClickListener { homePresenter.moveToList(DataType.Network) }
            localAndRemoteCats.setOnClickListener { homePresenter.moveToList(DataType.Mediator) }
        }
    }

    override fun moveToList(dataType: DataType) {
        (requireActivity() as MainActivity).moveToListFragment(dataType)
    }

    override fun onDestroy() {
        homePresenter.onDestroy()
        super.onDestroy()
    }
}