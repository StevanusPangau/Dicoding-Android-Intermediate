package com.dicoding.picodiploma.mycamera.data.api

import com.learn.submissionawalstoryapp.data.remote.response.LoginResponse
import com.learn.submissionawalstoryapp.data.remote.response.RegisterResponse
import com.learn.submissionawalstoryapp.data.remote.response.StoryResponse
import com.learn.submissionawalstoryapp.data.remote.response.StoryUploadResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.*

interface ApiService {
    @GET("stories")
    suspend fun getStories(): StoryResponse

    @Multipart
    @POST("stories")
    suspend fun uploadStory(
        @Part file: MultipartBody.Part,
        @Part("description") description: RequestBody,
    ): StoryUploadResponse

    @FormUrlEncoded
    @POST("register")
    suspend fun register(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("password") password: String
    ): RegisterResponse

    @FormUrlEncoded
    @POST("login")
    suspend fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ): LoginResponse
}

