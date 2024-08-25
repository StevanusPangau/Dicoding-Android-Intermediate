package com.learn.submissionakhirstoryapp

import com.learn.submissionakhirstoryapp.data.remote.response.ListStoryItem

object DataDummy {
    fun generateDummyStoryModel(): List<ListStoryItem> {
        val items: MutableList<ListStoryItem> = arrayListOf()
        for (i in 0..100) {
            val quote = ListStoryItem(
                "photoUrl + $i",
                "createdAt + $i",
                "name + $i",
                "description + $i",
                0.0,
                i.toString(),
                0.0
            )
            items.add(quote)
        }
        return items
    }
}