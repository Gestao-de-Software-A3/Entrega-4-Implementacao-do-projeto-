package com.example.basket.data.database

import android.content.Context
import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.basket.data.database.converters.Converters
import com.example.basket.data.database.dao.ProductDao
import com.example.basket.data.database.dao.UserDao
import com.example.basket.domain.model.Product
import com.example.basket.domain.model.User

@Database(
    entities = [Product::class, User::class], version = 1, exportSchema = true
)

@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    
    abstract fun productDao(): ProductDao
    
    abstract fun userDao(): UserDao

    companion object {
        
        @Volatile
        private lateinit var db: AppDatabase
        
        fun instance(context: Context): AppDatabase {
            if (::db.isInitialized) return db
            return Room.databaseBuilder(
                context,
                AppDatabase::class.java,
                "basket-db"
            )
                .build()
                .also {
                    db = it
                }
        }
    }
}