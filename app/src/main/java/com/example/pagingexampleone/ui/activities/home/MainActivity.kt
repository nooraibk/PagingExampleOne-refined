package com.example.pagingexampleone.ui.activities.home

import android.os.Bundle
import com.example.pagingexampleone.R
import com.example.pagingexampleone.core.CATS_DATA_TYPE
import com.example.pagingexampleone.core.bases.BaseActivity
import com.example.pagingexampleone.core.showToastOf
import com.example.pagingexampleone.databinding.ActivityMainBinding
import com.example.pagingexampleone.ui.fragments.catslist.CatsListFragment
import com.example.pagingexampleone.ui.fragments.home.HomeFragment
import dagger.hilt.android.AndroidEntryPoint

class MainActivity : BaseActivity<ActivityMainBinding>(ActivityMainBinding::inflate) {

    override fun initializeViews() {
        binding.fragmentsContainer.setOnClickListener {
            this showToastOf R.string.hehe
        }
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragments_container, HomeFragment())
            addToBackStack(null)
            commit()
        }
    }

    fun moveToListFragment(dataCode : Int) {
        val catsList = CatsListFragment()
        val bundle = Bundle()
        bundle.putInt(CATS_DATA_TYPE, dataCode)
        catsList.arguments = bundle
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragments_container, catsList)
            addToBackStack(null)
            commit()
        }
    }

}