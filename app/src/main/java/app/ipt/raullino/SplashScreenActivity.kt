package app.ipt.raullino

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import app.ipt.raullino.databinding.ActivityMainBinding

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
    }
}
