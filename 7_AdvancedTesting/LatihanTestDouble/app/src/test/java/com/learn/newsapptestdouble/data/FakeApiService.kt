package com.learn.newsapptestdouble.data

import com.learn.newsapptestdouble.data.remote.response.NewsResponse
import com.learn.newsapptestdouble.data.remote.retrofit.ApiService
import com.learn.newsapptestdouble.utils.DataDummy

class FakeApiService : ApiService {
    private val dummyResponse = DataDummy.generateDummyNewsResponse()
    override suspend fun getNews(apiKey: String): NewsResponse {
        return dummyResponse
    }
}