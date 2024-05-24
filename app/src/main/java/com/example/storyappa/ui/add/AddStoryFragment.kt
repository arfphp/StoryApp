package com.example.storyappa.ui.add

import android.app.Activity
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.navigation.fragment.findNavController
import com.yalantis.ucrop.UCrop
import com.example.storyappa.R
import com.example.storyappa.base.BaseFragment
import com.example.storyappa.data.lib.ApiResponse
import com.example.storyappa.databinding.FragmentAddStoryBinding
import com.example.storyappa.utils.Helper
import com.example.storyappa.utils.PreferenceManager
import com.example.storyappa.utils.ext.gone
import com.example.storyappa.utils.ext.show
import org.koin.android.ext.android.inject
import java.io.File
import java.util.Date

class AddStoryFragment : BaseFragment<FragmentAddStoryBinding>() {
    private val preferenceManager: PreferenceManager by inject()
    private val addStoryViewModel: AddStoryViewModel by inject()

    private var currentImageUri: Uri? = null

    private val launcherGallery = registerForActivityResult(
        ActivityResultContracts.PickVisualMedia()
    ) { uri: Uri? ->
        if (uri != null) {
            launchUCrop(uri)
        }
    }

    private val launcherCamera = registerForActivityResult(
        ActivityResultContracts.TakePicture()
    ) { isSuccess ->
        if (isSuccess) {
            launchUCrop(currentImageUri!!)
        }
    }

    private val launcherUCrop =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val resultUri = UCrop.getOutput(result.data!!)
                if (resultUri != null) {
                    currentImageUri = resultUri
                    setImagePreview()
                }
            }
        }

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): FragmentAddStoryBinding = FragmentAddStoryBinding.inflate(inflater, container, false)

    override fun initIntent() {
    }

    override fun initUI() {
        binding.apply {
            tvUserLabel.text = preferenceManager.getName?.first().toString().uppercase()
        }
    }

    override fun initAction() {
        binding.apply {
            backButton.setOnClickListener {
                findNavController().popBackStack()
            }
            ibCameraButton.setOnClickListener {
                currentImageUri = Helper.getImageUri(requireActivity())
                launcherCamera.launch(currentImageUri)
            }
            ibGalleryButton.setOnClickListener {
                launcherGallery.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
            }
            buttonAdd.setOnClickListener {
                val description = edAddDescription.text.toString()

                if (description.isNotEmpty() && currentImageUri != null) {
                    addStoryViewModel.addStory(currentImageUri!!, description)
                } else {
                    Helper.showErrorToast(requireActivity(), getString(R.string.error_add_story))
                }
            }
        }
    }

    override fun initProcess() {
    }

    override fun initObservers() {
        addStoryViewModel.addStoryResult.observe(viewLifecycleOwner) { result ->
            binding.apply {
                when (result) {
                    is ApiResponse.Loading -> loadingButton.root.show()
                    is ApiResponse.Success -> {
                        loadingButton.root.gone()
                        Helper.showSuccessToast(requireActivity(), result.data.message)
                        findNavController().popBackStack()
                    }

                    is ApiResponse.Error -> {
                        loadingButton.root.gone()
                        Helper.showErrorToast(requireActivity(), result.errorMessage)
                    }

                    else -> loadingButton.root.gone()
                }
            }
        }
    }

    private fun launchUCrop(uri: Uri) {
        val timestamp = Date().time
        val cachedImage = File(requireActivity().cacheDir, "cropped_image_${timestamp}.jpg")

        val destinationUri = Uri.fromFile(cachedImage)

        val uCrop = UCrop.of(uri, destinationUri).withAspectRatio(1f, 1f)

        uCrop.getIntent(requireContext()).apply {
            launcherUCrop.launch(this)
        }
    }

    private fun setImagePreview() {
        currentImageUri?.let {
            binding.ivPreviewStory.setImageURI(it)
        }
    }

}