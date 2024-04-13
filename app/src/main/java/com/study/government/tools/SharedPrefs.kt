package com.study.government.tools

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV
import androidx.security.crypto.EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
import androidx.security.crypto.MasterKey
import androidx.security.crypto.MasterKey.KeyScheme

class SharedPrefs(context: Context) {
    
    companion object {
        
        private const val ID_TOKEN = "identity_token"
        const val PREFS_NAME = "shared_preferences"
    }
    
    private lateinit var prefs: SharedPreferences
    
    init {
        try {
            val key = MasterKey.Builder(context)
                .setKeyScheme(KeyScheme.AES256_GCM)
                .build()
            prefs = EncryptedSharedPreferences.create(
                context, PREFS_NAME, key, AES256_SIV, AES256_GCM
            )
        } catch (e: Exception) {
            e.message.logE("Create encrypted shared preferences exception")
        }
    }
    
    fun saveToken(token: String) {
        prefs.edit().putString(ID_TOKEN, token).apply()
    }
    
    fun clearToken() {
        prefs.edit().putString(ID_TOKEN, "").apply()
    }
    
    val authToken: String get() = (prefs.getString(ID_TOKEN, "") ?: "")
    
    val hasToken: Boolean get() = authToken.isNotBlank()
}