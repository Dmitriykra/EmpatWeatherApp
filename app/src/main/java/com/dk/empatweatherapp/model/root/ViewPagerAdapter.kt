package com.dk.empatweatherapp.model.root

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.dk.empatweatherapp.screens.cities.CitiFragment
import com.dk.empatweatherapp.screens.main.FragmentMain

class ViewPagerAdapter(fragmentActivity: FragmentActivity):FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0 -> FragmentMain()
            else -> {
                CitiFragment()
            }
        }
    }
}