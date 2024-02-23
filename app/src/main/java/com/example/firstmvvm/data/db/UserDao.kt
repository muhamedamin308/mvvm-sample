package com.example.firstmvvm.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.firstmvvm.data.db.entities.CURRENT_USER_ID
import com.example.firstmvvm.data.db.entities.User

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(user: User): Long

    @Query("SELECT * fROM user WHERE uid = $CURRENT_USER_ID")
    fun getUser(): LiveData<User>
}