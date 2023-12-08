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

/**
 * Módulo responsável por configurar a injeção de dependência para componentes de dados
 * relacionados a usuários e produtos, como repositórios e instâncias do banco de dados.
 */

object DataModule {
    
    
    //Função para carregar os módulos Koin relacionados a usuários e produtos.
    
    fun load() {
        loadKoinModules(userRepositoryModule() + userModule() + productRepositoryModule() + productModule())
    }
    
    
    //Módulo Koin para a injeção de dependência do repositório de usuários.
    
    private fun userRepositoryModule(): Module {
        return module {
            single<UserRepository> {
                UserRepositoryImpl(userDao = get())
            }
        }
    }
    
    //Módulo Koin para a injeção de dependência da instância do banco de dados relacionada a usuários.
    private fun  userModule(): Module {
        return module {
            single { AppDatabase.instance(androidContext()).userDao() }
        }
    }
    
    //Módulo Koin para a injeção de dependência do repositório de produtos.
    private fun productRepositoryModule(): Module {
        return module {
            single<ProductRepository> {
                ProductRepositoryImpl(productDao = get())
            }
        }
    }
    
    //Módulo Koin para a injeção de dependência da instância do banco de dados relacionada a produtos.
    private fun  productModule(): Module {
        return module {
            single { AppDatabase.instance(androidContext()).productDao() }
        }
    }
}