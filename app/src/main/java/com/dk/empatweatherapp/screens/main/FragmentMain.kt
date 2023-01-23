package com.dk.empatweatherapp.screens.main

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.dk.empatweatherapp.R
import com.dk.empatweatherapp.model.days.DayModel


class FragmentMain : Fragment() {

    private lateinit var recyclerView: RecyclerView
    lateinit var adapter: MainAdapter;

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        val view = inflater.inflate(R.layout.fragment_main, container, false)
        recyclerView = view.findViewById(R.id.main_recyclerview)

        recyclerView.adapter = adapter
        viewModel.getWeatherData()
        viewModel.myWeatherList.observe(viewLifecycleOwner) { list ->
            list.body()?.let { adapter.setList(it) }
        }
        return view
    }
}