package com.learn.submissionakhirstoryapp.data

import androidx.lifecycle.liveData
import com.learn.submissionakhirstoryapp.data.remote.retrofit.ApiService
import com.learn.submissionakhirstoryapp.data.local.pref.UserModel
import com.learn.submissionakhirstoryapp.data.local.pref.UserPreference

class AuthRepository private constructor(
    private val authApiService: ApiService,
    private val userPreference: UserPreference,
) {
    fun register(name: String, email: String, password: String) = liveData {
        emit(ResultState.Loading)

        try {
            val successResponse = authApiService.register(name, email, password)
            emit(ResultState.Success(successResponse))
        } catch (e: Exception) {
            emit(ResultState.Error(e.message.toString()))
        }
    }

    fun login(email: String, password: String) = liveData {
        emit(ResultState.Loading)

        try {
            val successResponse = authApiService.login(email, password)
            emit(ResultState.Success(successResponse))
        } catch (e: Exception) {
            emit(ResultState.Error(e.message.toString()))
        }
    }

    suspend fun saveSession(user: UserModel) {
        userPreference.saveSession(user)
    }

    companion object {
        fun getInstance(apiService: ApiService, userPreference: UserPreference) =
            AuthRepository(apiService, userPreference)
    }
}