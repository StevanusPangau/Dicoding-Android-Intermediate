package com.learn.submissionawalstoryapp.view.story

import androidx.lifecycle.ViewModel
import com.learn.submissionawalstoryapp.data.StoryRepository
import okhttp3.MultipartBody
import okhttp3.RequestBody

class StoryViewModel(private val repository: StoryRepository)  : ViewModel() {
    fun uploadStory(file: MultipartBody.Part, description: RequestBody) = repository.uploadStory(file, description)
}