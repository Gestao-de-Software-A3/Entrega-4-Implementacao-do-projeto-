package com.example.basket.data.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.basket.domain.model.Product
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductDao {
    
    @Query("SELECT * FROM Product")
    fun searchAll(): Flow<List<Product>>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun add(product: Product)
    
    @Delete
    suspend fun delete(product: Product)
    
    @Query("SELECT * FROM Product WHERE id = :id")
    fun findProductById(id: Long) : Flow<Product?>
    
    @Query("SELECT * FROM Product WHERE userId = :userId")
    fun findUserProducts(userId: String) : Flow<List<Product>>
    
}