package com.example.storyappa.ui.map

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.storyappa.databinding.FragmentMapBinding
import com.example.storyappa.base.BaseFragment

class MapFragment : BaseFragment<FragmentMapBinding>() {
    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): FragmentMapBinding = FragmentMapBinding.inflate(inflater, container, false)

    override fun initIntent() {
    }

    override fun initUI() {
    }

    override fun initAction() {
    }

    override fun initProcess() {
    }

    override fun initObservers() {
    }
}