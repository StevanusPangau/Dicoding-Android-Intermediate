package com.learn.submissionakhirstoryapp.data.remote.retrofit

import com.learn.submissionakhirstoryapp.data.remote.response.LoginResponse
import com.learn.submissionakhirstoryapp.data.remote.response.RegisterResponse
import com.learn.submissionakhirstoryapp.data.remote.response.StoryResponse
import com.learn.submissionakhirstoryapp.data.remote.response.StoryUploadResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.*

interface ApiService {
    @GET("stories")
    suspend fun getStories(
        @Query("page") page: Int = 1,
        @Query("size") size: Int = 20,
    ): StoryResponse


    @Multipart
    @POST("stories")
    suspend fun uploadStory(
        @Part file: MultipartBody.Part,
        @Part("description") description: RequestBody,
        @Part("lat") lat: RequestBody? = null,
        @Part("lon") lon: RequestBody? = null,
    ): StoryUploadResponse

    @FormUrlEncoded
    @POST("register")
    suspend fun register(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("password") password: String,
    ): RegisterResponse

    @FormUrlEncoded
    @POST("login")
    suspend fun login(
        @Field("email") email: String,
        @Field("password") password: String,
    ): LoginResponse

    @GET("stories")
    suspend fun getStoriesWithLocation(
        @Query("location") location: Int = 1,
    ): StoryResponse
}

