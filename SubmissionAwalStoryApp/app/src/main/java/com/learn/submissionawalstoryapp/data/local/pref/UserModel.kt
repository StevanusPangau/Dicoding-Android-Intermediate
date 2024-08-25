package com.learn.submissionawalstoryapp.data.local.pref

data class UserModel(
    val name: String,
    val userId: String,
    val token: String,
    val isLogin: Boolean = false,
)