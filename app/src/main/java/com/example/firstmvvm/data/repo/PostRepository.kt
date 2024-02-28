package com.example.firstmvvm.data.repo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.firstmvvm.data.db.AppDatabase
import com.example.firstmvvm.data.db.entities.Post
import com.example.firstmvvm.data.network.MyApi
import com.example.firstmvvm.data.network.SafeApiRequest
import com.example.firstmvvm.data.preferences.PreferenceProvider
import com.example.firstmvvm.util.Coroutines
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

private const val MINIMUM_DURATION = 6
class PostRepository(
    private val api: MyApi,
    private val db: AppDatabase,
    private val preferenceProvider: PreferenceProvider
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
        val lastSavedAt = preferenceProvider.getLastSavedAt()
        if (lastSavedAt == null || isFetchNeeded(LocalDateTime.parse(lastSavedAt))) {
            val response = apiRequest { api.getPosts() }
            response?.let {
                posts.postValue(it.posts)
            }
        }
    }

    private fun isFetchNeeded(savedAt: LocalDateTime): Boolean =
        ChronoUnit.HOURS.between(savedAt, LocalDateTime.now()) > MINIMUM_DURATION

    private fun savePosts(posts: List<Post>) {
        Coroutines.io {
            preferenceProvider.saveLastSavedAt(LocalDateTime.now().toString())
            db.getPostsDao().saveAllPosts(posts)
        }
    }
}