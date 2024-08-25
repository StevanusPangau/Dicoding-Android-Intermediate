package com.learn.submissionakhirstoryapp.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.learn.submissionakhirstoryapp.data.remote.retrofit.ApiService
import com.learn.submissionakhirstoryapp.data.local.pref.UserModel
import com.learn.submissionakhirstoryapp.data.local.pref.UserPreference
import com.learn.submissionakhirstoryapp.data.local.room.StoryDatabase
import com.learn.submissionakhirstoryapp.data.remote.response.ListStoryItem
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody
import okhttp3.RequestBody

class StoryRepository private constructor(
    private val storyDatabase: StoryDatabase,
    private val storyApiService: ApiService,
    private val userPreference: UserPreference,
) {
    @OptIn(ExperimentalPagingApi::class)
    fun getStories(): LiveData<PagingData<ListStoryItem>> {
        return Pager(
            config = PagingConfig(
                pageSize = 5
            ),
            remoteMediator = StoryRemoteMediator(storyDatabase, storyApiService),
            pagingSourceFactory = {
                storyDatabase.storyDao().getAllStory()
            }
        ).liveData
    }

    fun uploadStory(
        file: MultipartBody.Part,
        description: RequestBody,
        lat: RequestBody?,
        lon: RequestBody?,
    ) = liveData {
        emit(ResultState.Loading)

        try {
            val successResponse = storyApiService.uploadStory(file, description, lat, lon)
            emit(ResultState.Success(successResponse))
        } catch (e: Exception) {
            emit(ResultState.Error(e.message.toString()))
        }
    }

    fun getStoriesWithLocation(location: Int = 1) = liveData {
        emit(ResultState.Loading)

        try {
            val successResponse = storyApiService.getStoriesWithLocation(location)
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
        fun getInstance(
            storyDatabase: StoryDatabase,
            apiService: ApiService,
            userPreference: UserPreference,
        ) =
            StoryRepository(storyDatabase, apiService, userPreference)
    }
}