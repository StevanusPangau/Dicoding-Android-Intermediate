package com.learn.submissionakhirstoryapp.view.story

import androidx.lifecycle.ViewModel
import com.learn.submissionakhirstoryapp.data.StoryRepository
import okhttp3.MultipartBody
import okhttp3.RequestBody

class StoryViewModel(private val repository: StoryRepository) : ViewModel() {
    fun uploadStory(
        file: MultipartBody.Part,
        description: RequestBody,
        lat: RequestBody?,
        lon: RequestBody?,
    ) = repository.uploadStory(file, description, lat, lon)

    fun getStoriesWithLocation(location: Int = 1) = repository.getStoriesWithLocation(location)
}