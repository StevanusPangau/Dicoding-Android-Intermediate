package com.learn.submissionakhirstoryapp.view.main

import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.learn.submissionakhirstoryapp.R
import com.learn.submissionakhirstoryapp.databinding.ActivityStoryBinding

class StoryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityStoryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityStoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setupData()
    }

    private fun setupData() {
        val photoUrl = intent.getStringExtra(EXTRA_NAME)
        val title = intent.getStringExtra(EXTRA_TITLE)
        val description = intent.getStringExtra(EXTRA_DESCRIPTION)

        Glide.with(applicationContext)
            .load(photoUrl)
            .into(findViewById(binding.ivPhoto.id))

        findViewById<TextView>(binding.tvTitle.id).text = title
        findViewById<TextView>(binding.tvDescription.id).text = description
    }

    companion object {
        const val EXTRA_NAME = "extra_photo"
        const val EXTRA_TITLE = "extra_title"
        const val EXTRA_DESCRIPTION = "extra_description"
    }
}