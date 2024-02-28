package com.example.firstmvvm.ui.home.posts

import androidx.lifecycle.ViewModel
import com.example.firstmvvm.data.repo.PostRepository
import com.example.firstmvvm.util.lazyDeferred

class PostsViewModel(
    private val repository: PostRepository
): ViewModel() {
    val posts by lazyDeferred {
        repository.getPosts()
    }
}