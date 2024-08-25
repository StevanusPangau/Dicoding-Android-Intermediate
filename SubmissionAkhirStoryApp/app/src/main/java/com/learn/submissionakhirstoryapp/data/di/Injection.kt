package com.learn.submissionakhirstoryapp.data.di

import android.content.Context
import com.learn.submissionakhirstoryapp.data.remote.retrofit.ApiConfig
import com.learn.submissionakhirstoryapp.data.AuthRepository
import com.learn.submissionakhirstoryapp.data.StoryRepository
import com.learn.submissionakhirstoryapp.data.local.pref.UserPreference
import com.learn.submissionakhirstoryapp.data.local.pref.dataStore
import com.learn.submissionakhirstoryapp.data.local.room.StoryDatabase
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

object Injection {
    fun provideRepository(context: Context): StoryRepository {
        val pref = UserPreference.getInstance(context.dataStore)
        val user = runBlocking { pref.getSession().first() }
        val apiService = ApiConfig.getApiService(user.token)
        val database = StoryDatabase.getDatabase(context)
        return StoryRepository.getInstance(database, apiService, pref)
    }

    fun provideAuthRepository(context: Context): AuthRepository {
        val userPreference = UserPreference.getInstance(context.dataStore)
        val user = runBlocking { userPreference.getSession().first() }
        val apiService = ApiConfig.getApiService(user.token)
        return AuthRepository.getInstance(apiService, userPreference)
    }
}