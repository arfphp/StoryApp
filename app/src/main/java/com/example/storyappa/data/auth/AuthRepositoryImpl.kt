package com.example.storyappa.data.auth

import com.example.storyappa.data.lib.ApiResponse
import com.example.storyappa.utils.ConstVal
import com.example.storyappa.utils.PreferenceManager
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.koin.core.context.loadKoinModules
import org.koin.core.context.unloadKoinModules
import java.lang.Exception

class AuthRepositoryImpl(
    private val authService: AuthService,
    private val preferenceManager: PreferenceManager
) : AuthRepository {
    override fun login(dto: LoginRequest): Flow<ApiResponse<LoginResponse>> = flow {
        try {
            emit(ApiResponse.Loading)
            val response = authService.login(dto.email, dto.password)
            if (response.error) {
                emit(ApiResponse.Error(response.message))
            } else {
                val loginResult = response.loginResult
                preferenceManager.setLoginPrefs(loginResult)
                reloadKoinModules()
                emit(ApiResponse.Success(response))
            }
        } catch (e: Exception) {
            e.printStackTrace()
            emit(ApiResponse.Error(e.message.toString()))
        }
    }

    override fun register(dto: RegisterRequest): Flow<ApiResponse<RegisterResponse>> = flow {
        try {
            emit(ApiResponse.Loading)
            val response = authService.register(dto.name, dto.email, dto.password)
            if (response.error) {
                emit(ApiResponse.Error(response.message))
            } else {
                emit(ApiResponse.Success(response))
            }
        } catch (e: Exception) {
            e.printStackTrace()
            emit(ApiResponse.Error(e.message.toString()))
        }
    }

    override fun logout(): Boolean {
        return try {
            preferenceManager.clearAllPreferences()
            reloadKoinModules()
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    private fun reloadKoinModules() {
        unloadKoinModules(ConstVal.koinModules)
        loadKoinModules(ConstVal.koinModules)
    }
}