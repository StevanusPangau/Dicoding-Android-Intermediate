package com.learn.submissionakhirstoryapp.view

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.learn.submissionakhirstoryapp.data.AuthRepository
import com.learn.submissionakhirstoryapp.data.di.Injection
import com.learn.submissionakhirstoryapp.view.login.LoginViewModel
import com.learn.submissionakhirstoryapp.view.signup.SignupViewModel

class AuthModelFactory(private val authRepository: AuthRepository) :
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(LoginViewModel::class.java) -> {
                LoginViewModel(authRepository) as T
            }

            modelClass.isAssignableFrom(SignupViewModel::class.java) -> {
                SignupViewModel(authRepository) as T
            }

            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }

    companion object {
        fun getInstance(context: Context) =
            AuthModelFactory(Injection.provideAuthRepository(context))
    }
}