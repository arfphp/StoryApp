package com.example.storyappa.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.storyappa.data.lib.ApiResponse
import com.example.storyappa.data.story.StoryRepositoryImpl
import com.example.storyappa.data.story.StoryResponse
import kotlinx.coroutines.launch

class HomeViewModel(private val storyRepositoryImpl: StoryRepositoryImpl) : ViewModel() {
    private val _storiesResult = MutableLiveData<ApiResponse<StoryResponse>>()
    val storyResult: LiveData<ApiResponse<StoryResponse>> by lazy { _storiesResult }

    fun getAllStories() {
        viewModelScope.launch {
            storyRepositoryImpl.getAllStories().collect {
                _storiesResult.value = it
            }
        }
    }
}