package com.learn.submissionakhirstoryapp.view.signup

import androidx.lifecycle.ViewModel
import com.learn.submissionakhirstoryapp.data.AuthRepository

class SignupViewModel(private val repository: AuthRepository) : ViewModel() {
    fun register(name: String, email: String, password: String) =
        repository.register(name, email, password)
}