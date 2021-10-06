package io.github.pengdst.financialapp

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import io.github.pengdst.financialapp.data.local.util.PrefUtil
import io.github.pengdst.financialapp.ui.setting.SettingFragment

class FinanceApp : Application() {

    private lateinit var prefUtil: PrefUtil

    override fun onCreate() {
        super.onCreate()

        prefUtil = PrefUtil.newInstance(applicationContext)
        setupDarkMode()
    }

    private fun setupDarkMode() {
        val isDarkMode = prefUtil.getBoolean(SettingFragment.ENABLE_DARKMODE)
        val nightMode = if (isDarkMode) {
            AppCompatDelegate.MODE_NIGHT_YES
        } else {
            AppCompatDelegate.MODE_NIGHT_NO
        }
        AppCompatDelegate.setDefaultNightMode(nightMode)
    }

}