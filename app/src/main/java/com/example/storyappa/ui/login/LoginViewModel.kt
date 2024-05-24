package com.example.storyappa.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.storyappa.data.auth.AuthRepositoryImpl
import com.example.storyappa.data.auth.LoginRequest
import com.example.storyappa.data.auth.LoginResponse
import com.example.storyappa.data.lib.ApiResponse
import kotlinx.coroutines.launch

class LoginViewModel(private val authRepositoryImpl: AuthRepositoryImpl) : ViewModel() {
    private val _loginResult = MutableLiveData<ApiResponse<LoginResponse>>()
    val loginResult: LiveData<ApiResponse<LoginResponse>> by lazy { _loginResult }

    fun login(email: String, password: String) {
        viewModelScope.launch {
            authRepositoryImpl.login(LoginRequest(email, password)).collect {
                _loginResult.value = it
            }
        }
    }
}