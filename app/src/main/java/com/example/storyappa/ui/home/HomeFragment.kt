package com.example.storyappa.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.storyappa.R
import com.example.storyappa.base.BaseFragment
import com.example.storyappa.data.lib.ApiResponse
import com.example.storyappa.databinding.FragmentHomeBinding
import com.example.storyappa.utils.Helper
import com.example.storyappa.utils.PreferenceManager
import com.example.storyappa.utils.ext.gone
import com.example.storyappa.utils.ext.show
import org.koin.android.ext.android.inject

class HomeFragment : BaseFragment<FragmentHomeBinding>() {

    private val homeViewModel: HomeViewModel by inject()
    private val preferenceManager: PreferenceManager by inject()

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): FragmentHomeBinding = FragmentHomeBinding.inflate(inflater, container, false)

    override fun initIntent() {
    }

    override fun initUI() {
        binding.apply {
            userLabel.text = preferenceManager.getName?.first().toString().uppercase()
            greetingText.text = getString(R.string.greeting, preferenceManager.getName)
        }
    }

    override fun initAction() {
        binding.addStoryButton.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_addStoryFragment)
        }
    }

    override fun initProcess() {
        homeViewModel.getAllStories()
    }

    override fun initObservers() {
        with(binding) {
            homeViewModel.storyResult.observe(viewLifecycleOwner) { result ->
                when (result) {
                    is ApiResponse.Loading -> {
                        progressBar.show()
                        vErrorFetchData.root.gone()
                    }

                    is ApiResponse.Success -> {
                        progressBar.gone()
                        vErrorFetchData.root.gone()
                        val storyAdapter = StoryAdapter()
                        val linearLayoutManager = LinearLayoutManager(requireActivity())
                        val itemDecoration = DividerItemDecoration(
                            requireActivity(),
                            linearLayoutManager.orientation
                        )
                        storyAdapter.submitList(result.data.listStory)
                        binding.rvStory.apply {
                            adapter = storyAdapter
                            layoutManager = linearLayoutManager
                            addItemDecoration(itemDecoration)
                        }
                    }

                    is ApiResponse.Error -> {
                        progressBar.gone()
                        vErrorFetchData.root.show()
                        Helper.showErrorToast(requireActivity(), result.errorMessage)
                    }

                    else -> {
                        progressBar.gone()
                        vErrorFetchData.root.gone()
                    }
                }
            }
        }
    }
}