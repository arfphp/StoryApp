package com.example.storyappa.data.story

import android.net.Uri
import androidx.core.net.toFile
import com.example.storyappa.data.lib.ApiResponse
import com.example.storyappa.utils.ext.reduceFileImage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody

class StoryRepositoryImpl(private val storyService: StoryService) : StoryRepository {
    override fun getAllStories(): Flow<ApiResponse<StoryResponse>> = flow {
        try {
            emit(ApiResponse.Loading)
            val response = storyService.getAllStories()
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

    override fun addStory(imageUri: Uri, description: String): Flow<ApiResponse<AddStoryResponse>> =
        flow {
            try {
                emit(ApiResponse.Loading)
                val photo = imageUri.toFile().reduceFileImage()
                val requestImageFile = photo.asRequestBody("image/*".toMediaType())
                val requestBody = description.toRequestBody("text/plain".toMediaType())
                val multipartBody =
                    MultipartBody.Part.createFormData("photo", photo.name, requestImageFile)

                val response = storyService.addNewStory(multipartBody, requestBody)

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

    override fun detailStory(id: String): Flow<ApiResponse<DetailStoryResponse>> = flow {
        try {
            emit(ApiResponse.Loading)
            val response = storyService.getDetailStory(id)
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
}