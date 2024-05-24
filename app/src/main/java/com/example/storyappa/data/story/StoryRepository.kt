package com.example.storyappa.data.story

import android.net.Uri
import com.example.storyappa.data.lib.ApiResponse
import kotlinx.coroutines.flow.Flow

interface StoryRepository {
    fun getAllStories(): Flow<ApiResponse<StoryResponse>>

    fun addStory(imageUri: Uri, description: String): Flow<ApiResponse<AddStoryResponse>>

    fun detailStory(id: String): Flow<ApiResponse<DetailStoryResponse>>
}