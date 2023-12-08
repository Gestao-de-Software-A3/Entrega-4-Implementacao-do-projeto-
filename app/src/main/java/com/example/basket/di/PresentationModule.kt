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

/**
 * Módulo responsável por configurar a injeção de dependência para atividades (activities) da camada de apresentação.
 */
object PresentationModule {
    
    //Função para carregar os módulos Koin relacionados a atividades da camada de apresentação.
    fun load() {
        loadKoinModules(activityModule())
    }
    
    //Módulo Koin para a injeção de dependência de atividades.
    private fun activityModule(): Module {
        return module {
            // Atividades da camada de apresentação
            factory { LoginActivity() }
            factory { BaseUserActivity() }
            factory { UserFormActivity() }
            factory { ProductListActivity() }
            factory { ProductFormActivity() }
            factory { ProductDetailsActivity() }
        }
    }
}