package com.example.firstmvvm.ui.home.posts

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.firstmvvm.data.repo.PostRepository

@Suppress("UNCHECKED_CAST")
class PostsViewModelFactory(
    private val repository: PostRepository
) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        PostsViewModel(repository) as T
}