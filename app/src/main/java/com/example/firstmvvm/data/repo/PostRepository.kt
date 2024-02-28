package com.example.firstmvvm.data.repo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.firstmvvm.data.db.AppDatabase
import com.example.firstmvvm.data.db.entities.Post
import com.example.firstmvvm.data.network.MyApi
import com.example.firstmvvm.data.network.SafeApiRequest
import com.example.firstmvvm.util.Coroutines
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class PostRepository(
    private val api: MyApi,
    private val db: AppDatabase
): SafeApiRequest() {
    private val posts = MutableLiveData<List<Post>>()
    init {
        posts.observeForever {
            savePosts(it)
        }
    }

    suspend fun getPosts(): LiveData<List<Post>> =
        withContext(Dispatchers.IO) {
            fetchPosts()
            db.getPostsDao().getAllPosts()
        }

    private suspend fun fetchPosts() {
        if (isFetchNeeded()) {
            val response = apiRequest { api.getPosts() }
            response?.let {
                posts.postValue(it.posts)
            }
        }
    }

    private fun isFetchNeeded(): Boolean = true

    private fun savePosts(posts: List<Post>) {
        Coroutines.io {
            db.getPostsDao().saveAllPosts(posts)
        }
    }
}