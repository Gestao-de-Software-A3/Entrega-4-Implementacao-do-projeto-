package com.example.basket

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

/**
 * Classe principal da atividade (Activity) que representa a tela principal do aplicativo.
 * Estende AppCompatActivity, fornecendo compatibilidade com recursos mais recentes do Android.
 */
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}