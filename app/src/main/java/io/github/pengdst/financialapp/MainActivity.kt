package io.github.pengdst.financialapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.fragment.app.Fragment
import io.github.pengdst.financialapp.ui.home.HomeFragment
import io.github.pengdst.financialapp.ui.profile.ProfileFragment
import io.github.pengdst.financialapp.ui.setting.SettingFragment

class MainActivity : AppCompatActivity() {

    private lateinit var btnHome: Button
    private lateinit var btnProfile: Button
    private lateinit var btnSetting: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnHome = findViewById(R.id.btn_home)
        btnProfile = findViewById(R.id.btn_profile)
        btnSetting= findViewById(R.id.btn_setting)

        handleFragment()
    }

    private fun handleFragment() {
        val homeFragment = HomeFragment()
        val profileFragment = ProfileFragment()
        val settingFragment = SettingFragment()

        loadFragment(homeFragment)
        btnHome.setOnClickListener {
            loadFragment(homeFragment)
        }
        btnProfile.setOnClickListener {
            loadFragment(profileFragment)
        }
        btnSetting.setOnClickListener {
            loadFragment(settingFragment)
        }
    }

    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, fragment)
            .commitNow()
    }
}