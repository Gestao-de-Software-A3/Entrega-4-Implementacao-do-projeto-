package com.example.basket.data.preferences

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore

// Extensão para a propriedade `dataStore` em Context, usando DataStore para armazenar preferências de usuário.
// O nome "user_session" é usado como identificador para o DataStore.
val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user_session")

// Chave para armazenar e recuperar a informação sobre o usuário logado nas preferências do DataStore.
val userPreferences = stringPreferencesKey("userLogged")