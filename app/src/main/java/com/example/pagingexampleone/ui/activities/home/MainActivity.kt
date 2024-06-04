package com.example.pagingexampleone.ui.activities.home

import android.os.Bundle
import com.example.pagingexampleone.R
import com.example.pagingexampleone.core.CATS_DATA_TYPE
import com.example.pagingexampleone.core.bases.BaseActivity
import com.example.pagingexampleone.core.showToast
import com.example.pagingexampleone.core.utils.DataType
import com.example.pagingexampleone.databinding.ActivityMainBinding
import com.example.pagingexampleone.ui.fragments.catslist.CatsListFragment
import com.example.pagingexampleone.ui.fragments.home.HomeFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(ActivityMainBinding::inflate) {

    override fun initializeViews() {
        binding.fragmentsContainer.setOnClickListener {
            this showToast "hehe :)"
        }
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragments_container, HomeFragment())
            addToBackStack(null)
            commit()
        }
    }

    infix fun moveToListFragment(dataType : DataType) {
        val catsList = CatsListFragment()
        val bundle = Bundle()
        bundle.putSerializable(CATS_DATA_TYPE, dataType)
        catsList.arguments = bundle
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragments_container, catsList)
            addToBackStack(null)
            commit()
        }
    }
}