package io.github.pengdst.financialapp.ui.setting

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import com.google.android.material.switchmaterial.SwitchMaterial
import io.github.pengdst.financialapp.R
import io.github.pengdst.financialapp.data.local.util.PrefUtil

class SettingFragment : Fragment() {

    private lateinit var prefUtil: PrefUtil
    private lateinit var switchDarkMode: SwitchMaterial

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        prefUtil = PrefUtil.newInstance(requireContext())
        return inflater.inflate(R.layout.fragment_setting, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        switchDarkMode = view.findViewById(R.id.switch_darkmode)

        switchDarkMode.isChecked = prefUtil.getBoolean(ENABLE_DARKMODE)
        switchDarkMode.setOnCheckedChangeListener { _, isChecked ->
            prefUtil.saveBoolean(ENABLE_DARKMODE, isChecked)
            val nightMode = if (isChecked) {
                AppCompatDelegate.MODE_NIGHT_YES
            } else {
                AppCompatDelegate.MODE_NIGHT_NO
            }
            AppCompatDelegate.setDefaultNightMode(nightMode)
        }
    }

    companion object {
        const val ENABLE_DARKMODE = "enable_darkmode"
    }

}