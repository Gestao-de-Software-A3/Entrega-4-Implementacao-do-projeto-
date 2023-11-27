package com.example.basket.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.basket.domain.model.User
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    
    @Insert
    suspend fun addUser(user: User)
    
    @Query("SELECT * FROM User WHERE id = :userId AND password = :password")
    suspend fun userLogin(
        userId: String,
        password: String
    ): User?
    
    @Query("SELECT * FROM User WHERE id = :userId")
    fun searchUserById(userId: String): Flow<User>
    
    @Query("SELECT * FROM User")
    fun searchAllUsers(): Flow<List<User>>
}