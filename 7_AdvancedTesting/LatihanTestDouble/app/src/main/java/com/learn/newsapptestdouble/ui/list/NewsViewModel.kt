package com.learn.newsapptestdouble.ui.list

import androidx.lifecycle.ViewModel
import com.learn.newsapptestdouble.data.NewsRepository

class NewsViewModel(private val newsRepository: NewsRepository) : ViewModel() {
    fun getHeadlineNews() = newsRepository.getHeadlineNews()

    fun getBookmarkedNews() = newsRepository.getBookmarkedNews()
}