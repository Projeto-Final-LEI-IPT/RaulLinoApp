package com.example.raullino

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.preference.PreferenceManager
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.raullino.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import org.osmdroid.config.Configuration

class SplashScreenActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.splash_screen)

        supportActionBar?.hide()

        val btnPT= findViewById<ImageView>(R.id.btn_PT)
        val btnEN= findViewById<ImageView>(R.id.btn_EN)


        btnPT.setOnClickListener{
            Flags.selectedFlag = "PT"
           startInit();
        }
        btnEN.setOnClickListener{
            Flags.selectedFlag = "EN"
            startInit();
        }
        }

    private fun startInit() {
        val intent = Intent(this@SplashScreenActivity, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}
