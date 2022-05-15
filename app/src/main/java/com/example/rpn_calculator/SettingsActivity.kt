package com.example.rpn_calculator

import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.PreferenceManager

class SettingsActivity : AppCompatActivity() {
    lateinit var sp: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        loadScheme()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.settings_activity)

        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.settings, SettingsFragment())
                .commit()
        }
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        sp = PreferenceManager.getDefaultSharedPreferences(this)
    }

    class SettingsFragment : PreferenceFragmentCompat() {
        override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey)
        }
    }


    fun loadScheme() {
        val sp = PreferenceManager.getDefaultSharedPreferences(this)
        var cs = sp.getString("color_scheme","")
        if(cs == null) {
            cs = "system"
        }
        when (cs) {
            "system" -> {
                Log.d("LOG","SYS")
                setTheme(R.style.Theme_RPNCalculator)
            }
            "blue" -> {
                Log.d("LOG","BLUE")
                setTheme(R.style.BlueTheme)
            }
        }
    }
}