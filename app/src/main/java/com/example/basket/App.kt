package com.example.basket

import android.app.Application
import com.example.basket.di.DataModule
import com.example.basket.di.DomainModule
import com.example.basket.di.PresentationModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

/**
 * Classe de aplicativo principal que estende Application.
 * Inicializa o Koin e carrega os módulos de dados, domínio e apresentação.
 */
class App : Application() {
    
    /**
     * Método chamado quando o aplicativo é criado.
     * Inicializa o Koin e carrega os módulos de dados, domínio e apresentação.
     */
    override fun onCreate() {
        super.onCreate()
        
        // Inicializa o Koin com o contexto Android
        startKoin {
            androidContext(this@App)
        }
        
        // Carrega os módulos de dados, domínio e apresentação
        DataModule.load()
        DomainModule.load()
        PresentationModule.load()
    }
}