package com.example.firstmvvm.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.firstmvvm.data.db.entities.Post

@Dao
interface PostDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveAllPosts(posts: List<Post>)

    @Query("SELECT * FROM Post")
    fun getAllPosts(): LiveData<List<Post>>

}