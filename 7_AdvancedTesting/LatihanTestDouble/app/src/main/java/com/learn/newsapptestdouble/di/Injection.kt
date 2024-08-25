package com.learn.newsapptestdouble.di

import android.content.Context
import com.learn.newsapptestdouble.data.NewsRepository
import com.learn.newsapptestdouble.data.local.room.NewsDatabase
import com.learn.newsapptestdouble.data.remote.retrofit.ApiConfig

object Injection {
    fun provideRepository(context: Context): NewsRepository {
        val apiService = ApiConfig.getApiService()
        val database = NewsDatabase.getInstance(context)
        val dao = database.newsDao()
        return NewsRepository.getInstance(apiService, dao)
    }
}