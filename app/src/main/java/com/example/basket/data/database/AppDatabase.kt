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

// Anotação indicando que esta classe é um banco de dados Room que contém as entidades Product e User
@Database(
    entities = [Product::class, User::class], version = 1, exportSchema = true
)

// Indica o uso do conversor de tipo Converters para realizar conversões customizadas
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    
    // Método abstrato que retorna um objeto ProductDao para interagir com a tabela de produtos no banco de dados
    abstract fun productDao(): ProductDao
    
    // Método abstrato que retorna um objeto UserDao para interagir com a tabela de usuários no banco de dados
    abstract fun userDao(): UserDao
    
    // Companion object para criar uma instância única do banco de dados usando o padrão Singleton
    companion object {
    
        // Propriedade marcada com @Volatile para garantir que a instância é sempre lida a partir da memória principal
        @Volatile
        private lateinit var db: AppDatabase
    
        // Método estático que retorna a instância do banco de dados, criando-a se ainda não estiver inicializada
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