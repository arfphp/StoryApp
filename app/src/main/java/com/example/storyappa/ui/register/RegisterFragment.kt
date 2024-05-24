package com.example.storyappa.ui.register

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.storyappa.R
import com.example.storyappa.base.BaseFragment
import com.example.storyappa.data.lib.ApiResponse
import com.example.storyappa.databinding.FragmentRegisterBinding
import com.example.storyappa.utils.ext.gone
import com.example.storyappa.utils.ext.show
import com.shashank.sony.fancytoastlib.FancyToast
import org.koin.android.ext.android.inject

class RegisterFragment : BaseFragment<FragmentRegisterBinding>() {

    private val registerViewModel: RegisterViewModel by inject()

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): FragmentRegisterBinding = FragmentRegisterBinding.inflate(inflater, container, false)

    override fun initIntent() {
    }

    override fun initUI() {
        playAnimation()
    }

    override fun initAction() {
        binding.apply {
            registerButton.setOnClickListener {
                val name = edRegisterName.text.trim().toString()
                val email = edRegisterEmail.text?.trim().toString()
                val password = edRegisterPassword.text?.trim().toString()

                if (name.isEmpty()) {
                    edRegisterName.error = getString(R.string.error_empty_field)
                }
                if (email.isEmpty()) {
                    edRegisterEmail.error = getString(R.string.error_empty_field)
                }
                if (password.isEmpty()) {
                    edRegisterPassword.error = getString(R.string.error_empty_field)
                }

                if (name.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty()) {
                    registerViewModel.register(name, email, password)
                }
            }

            loginButton.setOnClickListener {
                findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
            }
        }
    }

    override fun initProcess() {
    }

    override fun initObservers() {
        binding.apply {
            registerViewModel.registerResult.observe(viewLifecycleOwner) { result ->
                when (result) {
                    is ApiResponse.Loading -> loadingButton.root.show()
                    is ApiResponse.Success -> {
                        FancyToast.makeText(
                            requireContext(),
                            result.data.message,
                            FancyToast.LENGTH_LONG,
                            FancyToast.SUCCESS,
                            false
                        ).show()
                        findNavController().popBackStack()
                    }

                    is ApiResponse.Error -> {
                        loadingButton.root.gone()
                        FancyToast.makeText(
                            requireContext(),
                            result.errorMessage,
                            FancyToast.LENGTH_LONG,
                            FancyToast.ERROR,
                            false
                        ).show()
                    }

                    else -> binding.root.gone()
                }
            }
        }
    }

    private fun playAnimation() {
        binding.apply {
            val icon = ObjectAnimator.ofFloat(appIcon, View.ALPHA, 1f).setDuration(100)
            val title = ObjectAnimator.ofFloat(registerHeadline, View.ALPHA, 1f).setDuration(100)
            val name = ObjectAnimator.ofFloat(edRegisterName, View.ALPHA, 1f).setDuration(100)
            val email = ObjectAnimator.ofFloat(edRegisterEmail, View.ALPHA, 1f).setDuration(100)
            val password =
                ObjectAnimator.ofFloat(edRegisterPassword, View.ALPHA, 1f).setDuration(100)
            val register = ObjectAnimator.ofFloat(registerButton, View.ALPHA, 1f).setDuration(100)
            val login = ObjectAnimator.ofFloat(loginButton, View.ALPHA, 1f).setDuration(100)

            AnimatorSet().apply {
                playSequentially(icon, title, name, email, password, register, login)
                start()
            }
        }
    }

}