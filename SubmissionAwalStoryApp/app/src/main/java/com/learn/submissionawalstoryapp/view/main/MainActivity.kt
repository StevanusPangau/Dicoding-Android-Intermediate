package com.learn.submissionawalstoryapp.view.main

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.learn.submissionawalstoryapp.R
import com.learn.submissionawalstoryapp.data.ResultState
import com.learn.submissionawalstoryapp.data.remote.response.ListStoryItem
import com.learn.submissionawalstoryapp.databinding.ActivityMainBinding
import com.learn.submissionawalstoryapp.view.ViewModelFactory
import com.learn.submissionawalstoryapp.view.story.AddStoryActivity
import com.learn.submissionawalstoryapp.view.welcome.WelcomeActivity

class MainActivity : AppCompatActivity() {

    private val viewModel by viewModels<MainViewModel> {
        ViewModelFactory.getInstance(this)
    }
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val layoutManager = LinearLayoutManager(this)
        binding.rvStories.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding.rvStories.addItemDecoration(itemDecoration)

        viewModel.getSession().observe(this) { user ->
            if (!user.isLogin) {
                startActivity(Intent(this, WelcomeActivity::class.java))
                finish()
            }
        }

        setupView()

        binding.toolBar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.menu_lang -> {
                    startActivity(Intent(Settings.ACTION_LOCALE_SETTINGS))
                    true
                }

                R.id.menu_logout -> {
                    viewModel.logout()
                    true
                }

                else -> false
            }
        }

        binding.addStoryButton.setOnClickListener {
            val intent = Intent(this, AddStoryActivity::class.java)
            startActivity(intent)
        }

    }

    override fun onResume() {
        super.onResume()
        loadStories()
    }

    private fun setupView() {
        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
        supportActionBar?.hide()
    }

    private fun loadStories(){
        viewModel.getStories().observe(this) { result ->
            when (result) {
                is ResultState.Loading -> {
                    showLoading(true)
                }

                is ResultState.Success -> {
                    showLoading(false)
                    val stories = result.data.listStory
                    setStoriesData(stories)
                }

                is ResultState.Error -> {
                    showLoading(false)
                    val message = result.error
                    showToast(message)
                }
            }
        }
    }

    private fun setStoriesData(stories: List<ListStoryItem?>?) {
        val adapter = StoryAdapter()
        adapter.submitList(stories)
        binding.rvStories.adapter = adapter

        adapter.setOnItemClickCallback(object : StoryAdapter.OnItemClickCallback {
            override fun onItemClicked(data: ListStoryItem) {
                showSelectedHero(
                    data,
                    binding.rvStories.findViewHolderForAdapterPosition(
                        adapter.currentList.indexOf(data)
                    )!!
                )
            }
        })
    }

    private fun showSelectedHero(story: ListStoryItem, viewHolder: RecyclerView.ViewHolder) {
        val storyIntent = Intent(this@MainActivity, StoryActivity::class.java)
        storyIntent.putExtra(StoryActivity.EXTRA_NAME, story.photoUrl)
        storyIntent.putExtra(StoryActivity.EXTRA_TITLE, story.name)
        storyIntent.putExtra(StoryActivity.EXTRA_DESCRIPTION, story.description)

        val optionsCompat: ActivityOptionsCompat =
            ActivityOptionsCompat.makeSceneTransitionAnimation(
                this@MainActivity as Activity,
                Pair(viewHolder.itemView.findViewById(R.id.iv_photo), "photo"),
                Pair(viewHolder.itemView.findViewById(R.id.tv_title), "title"),
                Pair(viewHolder.itemView.findViewById(R.id.tv_description), "description"),
            )

        startActivity(storyIntent, optionsCompat.toBundle())
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}