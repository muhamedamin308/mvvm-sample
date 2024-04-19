package com.example.firstmvvm.data.network.response

import com.example.firstmvvm.data.db.entities.Post

data class PostResponse(
    val limit: Int, val posts: List<Post>, val skip: Int, val total: Int
)