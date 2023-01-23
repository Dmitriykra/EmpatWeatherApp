package com.dk.empatweatherapp.model.root

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import com.dk.empatweatherapp.R
import com.dk.empatweatherapp.databinding.FragmentRootBinding
import com.google.android.material.tabs.TabLayoutMediator

class FragmentRoot : Fragment() {

    private var _binding: FragmentRootBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_root, container, false)
        _binding = FragmentRootBinding.inflate(inflater, container, false)
        binding.viewPager.adapter = ViewPagerAdapter(context as FragmentActivity)
        //убрать цвет иконок
        binding.tabLayout.tabIconTint = null

        TabLayoutMediator(binding.tabLayout, binding.viewPager){
            tab, pos ->
            when (pos){
                0->{
                    tab.setIcon(R.drawable.baseline_cloud_24)
                }

                1->{
                    tab.setIcon(R.drawable.baseline_location_city_24)
                }
            }
        }.attach()
        return binding.root
    }
}