package io.github.pengdst.financialapp.data.local.util

import android.content.Context
import androidx.preference.PreferenceManager

class PrefUtil(context: Context) {
    private var pref = PreferenceManager.getDefaultSharedPreferences(context)

    fun saveString(key: String, value: String) = apply {
        pref.edit().putString(key, value).apply()
    }

    fun saveBoolean(key: String, value: Boolean) = apply {
        pref.edit().putBoolean(key, value).apply()
    }

    fun saveInt(key: String, value: Int) = apply {
        pref.edit().putInt(key, value).apply()
    }

    fun getString(key: String) = pref.getString(key, "")
    fun getBoolean(key: String) = pref.getBoolean(key, false)
    fun getInt(key: String) = pref.getInt(key, 0)

    companion object {

        private var instance: PrefUtil? = null

        fun newInstance(context: Context) = instance ?: PrefUtil(context).also {
            instance = it
        }
    }
}