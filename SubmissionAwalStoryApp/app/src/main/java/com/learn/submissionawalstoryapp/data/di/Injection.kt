package com.learn.submissionawalstoryapp.data.di

import android.content.Context
import com.dicoding.picodiploma.mycamera.data.api.ApiConfig
import com.learn.submissionawalstoryapp.data.AuthRepository
import com.learn.submissionawalstoryapp.data.StoryRepository
import com.learn.submissionawalstoryapp.data.local.pref.UserPreference
import com.learn.submissionawalstoryapp.data.local.pref.dataStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

object Injection {
    fun provideRepository(context: Context): StoryRepository {
        val pref = UserPreference.getInstance(context.dataStore)
        val user = runBlocking { pref.getSession().first() }
        val apiService = ApiConfig.getApiService(user.token)
        return StoryRepository.getInstance(apiService, pref)
    }

    fun provideAuthRepository(context: Context): AuthRepository {
        val userPreference = UserPreference.getInstance(context.dataStore)
        val user = runBlocking { userPreference.getSession().first() }
        val apiService = ApiConfig.getApiService(user.token)
        return AuthRepository.getInstance(apiService, userPreference)
    }
}