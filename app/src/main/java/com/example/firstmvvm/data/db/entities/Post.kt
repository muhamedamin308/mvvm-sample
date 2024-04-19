package com.example.firstmvvm.data.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

@Entity
data class Post(
    @PrimaryKey(autoGenerate = false) val id: Int,
    val body: String,
    val reactions: Int,
    val tags: List<String>,
    val title: String,
    val userId: Int
)

class TagsConverter {
    @TypeConverter
    fun fromString(value: String?): List<String> {
        val listType = object : TypeToken<List<String>>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun fromList(list: List<String>?): String = Gson().toJson(list)
}