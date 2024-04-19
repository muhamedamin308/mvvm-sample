package com.example.firstmvvm.data.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey


const val CURRENT_USER_ID = 0

@Entity
data class User(
    var id: Int? = null,
    val email: String? = null,
    val firstName: String? = null,
    val gender: String? = null,
    val lastName: String? = null,
    val image: String? = null,
    val token: String? = null,
    val username: String? = null,
    val message: String? = null
) {
    @PrimaryKey(autoGenerate = false)
    var uid: Int = CURRENT_USER_ID
}