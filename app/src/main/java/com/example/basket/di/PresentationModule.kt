package com.example.basket.di

import com.example.basket.presentation.activity.BaseUserActivity
import com.example.basket.presentation.activity.LoginActivity
import com.example.basket.presentation.activity.ProductDetailsActivity
import com.example.basket.presentation.activity.ProductFormActivity
import com.example.basket.presentation.activity.ProductListActivity
import com.example.basket.presentation.activity.UserFormActivity
import org.koin.core.context.loadKoinModules
import org.koin.core.module.Module
import org.koin.dsl.module

object PresentationModule {
    
    fun load() {
        loadKoinModules(activityModule())
    }
    
    private fun activityModule(): Module {
        return module {
            factory { LoginActivity() }
            factory { BaseUserActivity() }
            factory { UserFormActivity() }
            factory { ProductListActivity() }
            factory { ProductFormActivity() }
            factory { ProductDetailsActivity() }
        }
    }
}