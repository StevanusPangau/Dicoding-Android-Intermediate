package com.learn.newsapptestdouble.ui.list

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.learn.newsapptestdouble.data.NewsRepository
import com.learn.newsapptestdouble.data.local.entity.NewsEntity
import com.learn.newsapptestdouble.utils.DataDummy
import org.junit.Assert
import org.junit.Before

import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner
import com.learn.newsapptestdouble.data.Result
import com.learn.newsapptestdouble.utils.getOrAwaitValue
import org.junit.Rule
import org.mockito.Mockito

@RunWith(MockitoJUnitRunner::class)
class NewsViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var newsRepository: NewsRepository
    private lateinit var newsViewModel: NewsViewModel
    private val dummyNews = DataDummy.generateDummyNewsEntity()

    @Before
    fun setUp() {
        newsViewModel = NewsViewModel(newsRepository)
    }

    @Test
    fun `when Get HeadlineNews Should Not Null and Return Success`() {
        val expectedNews = MutableLiveData<Result<List<NewsEntity>>>()
        expectedNews.value = Result.Success(dummyNews)

        `when`(newsRepository.getHeadlineNews()).thenReturn(expectedNews)

        val actualNews = newsViewModel.getHeadlineNews().getOrAwaitValue()
        Mockito.verify(newsRepository).getHeadlineNews()
        Assert.assertNotNull(actualNews)
        Assert.assertTrue(actualNews is Result.Success)
        Assert.assertEquals(dummyNews.size, (actualNews as Result.Success).data.size)
    }

    @Test
    fun `when Network Error Should Return Error`() {
        val headlineNews = MutableLiveData<Result<List<NewsEntity>>>()
        headlineNews.value = Result.Error("Error")
        `when`(newsRepository.getHeadlineNews()).thenReturn(headlineNews)
        val actualNews = newsViewModel.getHeadlineNews().getOrAwaitValue()
        Mockito.verify(newsRepository).getHeadlineNews()
        Assert.assertNotNull(actualNews)
        Assert.assertTrue(actualNews is Result.Error)
    }

    @Test
    fun `when Get Bookmarked News Should Return Success`() {
        val dummyBookmarkedNews = DataDummy.generateDummyNewsEntity()
        `when`(newsRepository.getBookmarkedNews()).thenReturn(MutableLiveData(dummyBookmarkedNews))

        val actualNews = newsViewModel.getBookmarkedNews().getOrAwaitValue()

        Mockito.verify(newsRepository).getBookmarkedNews()
        Assert.assertNotNull(actualNews)
        Assert.assertEquals(dummyBookmarkedNews.size, actualNews.size)
    }
}