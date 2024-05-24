package com.example.storyappa.ui.detail

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import coil.load
import coil.transform.RoundedCornersTransformation
import com.example.storyappa.base.BaseFragment
import com.example.storyappa.data.lib.ApiResponse
import com.example.storyappa.data.story.Story
import com.example.storyappa.databinding.FragmentDetailStoryBinding
import com.example.storyappa.utils.Helper
import com.example.storyappa.utils.ext.formatDate
import com.example.storyappa.utils.ext.gone
import org.koin.android.ext.android.inject

class DetailStoryFragment : BaseFragment<FragmentDetailStoryBinding>() {

    private val detailStoryViewModel: DetailStoryViewModel by inject()

    private var storyId: String? = null

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): FragmentDetailStoryBinding = FragmentDetailStoryBinding.inflate(inflater, container, false)

    override fun initIntent() {
        storyId = arguments?.getString("storyId")
    }

    override fun initUI() {
    }

    override fun initAction() {
        binding.apply {
            backButton.setOnClickListener {
                findNavController().popBackStack()
            }
        }
    }

    override fun initProcess() {
        detailStoryViewModel.detailStory(storyId.toString())
    }

    override fun initObservers() {
        detailStoryViewModel.detailStoryResult.observe(viewLifecycleOwner) { result ->
            binding.apply {
                when (result) {
                    is ApiResponse.Loading -> progressBar.show()
                    is ApiResponse.Success -> {
                        progressBar.gone()

                        setStoryDetail(result.data.story)
                    }

                    is ApiResponse.Error -> {
                        progressBar.gone()
                        Helper.showErrorToast(requireActivity(), result.errorMessage)
                    }

                    else -> progressBar.gone()
                }
            }
        }
    }

    private fun setStoryDetail(story: Story) {
        binding.apply {
            tvItemUserLabel.text = story.name?.first().toString().uppercase()
            tvDetailDescription.text = story.description
            tvDetailName.text = story.name
            tvDetailTimestamp.text = story.createdAt?.formatDate()
            ivDetailPhoto.load(story.photoUrl) {
                placeholder(ColorDrawable(Color.LTGRAY))
                transformations(RoundedCornersTransformation(20f, 20f, 20f, 20f))
            }
        }
    }

}