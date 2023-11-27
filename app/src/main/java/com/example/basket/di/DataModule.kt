package com.example.basket.di

import com.example.basket.data.database.AppDatabase
import com.example.basket.data.repository.ProductRepositoryImpl
import com.example.basket.data.repository.UserRepositoryImpl
import com.example.basket.domain.repository.ProductRepository
import com.example.basket.domain.repository.UserRepository
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.loadKoinModules
import org.koin.core.module.Module
import org.koin.dsl.module

object DataModule {
    
    fun load() {
        loadKoinModules(userRepositoryModule() + userModule() + productRepositoryModule() + productModule())
    }
    
    private fun userRepositoryModule(): Module {
        return module {
            single<UserRepository> {
                UserRepositoryImpl(userDao = get())
            }
        }
    }
    
    private fun  userModule(): Module {
        return module {
            single { AppDatabase.instance(androidContext()).userDao() }
        }
    }
    
    private fun productRepositoryModule(): Module {
        return module {
            single<ProductRepository> {
                ProductRepositoryImpl(productDao = get())
            }
        }
    }
    
    private fun  productModule(): Module {
        return module {
            single { AppDatabase.instance(androidContext()).productDao() }
        }
    }
}