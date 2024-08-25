package com.learn.submissionawalstoryapp.data

import androidx.lifecycle.liveData
import com.dicoding.picodiploma.mycamera.data.api.ApiService
import com.learn.submissionawalstoryapp.data.local.pref.UserModel
import com.learn.submissionawalstoryapp.data.local.pref.UserPreference
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody
import okhttp3.RequestBody

class StoryRepository private constructor(
    private val storyApiService: ApiService, private val userPreference: UserPreference,
) {
    fun getStories() = liveData {
        emit(ResultState.Loading)

        try {
            val successResponse = storyApiService.getStories()
            emit(ResultState.Success(successResponse))
        } catch (e: Exception) {
            emit(ResultState.Error(e.message.toString()))
        }
    }

    fun uploadStory(file: MultipartBody.Part, description: RequestBody) = liveData {
        emit(ResultState.Loading)

        try {
            val successResponse = storyApiService.uploadStory(file, description)
            emit(ResultState.Success(successResponse))
        } catch (e: Exception) {
            emit(ResultState.Error(e.message.toString()))
        }
    }

    fun getSession(): Flow<UserModel> {
        return userPreference.getSession()
    }

    suspend fun logout() {
        userPreference.logout()
    }

    companion object {
        fun getInstance(apiService: ApiService, userPreference: UserPreference) =
            StoryRepository(apiService, userPreference)
    }
}