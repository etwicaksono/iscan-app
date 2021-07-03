package com.etwicaksono.iscan

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.etwicaksono.iscan.databinding.ActivitySplashScreenBinding
import com.etwicaksono.iscan.ui.activities.HomeActivity

class SplashScreenActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

       Handler(Looper.getMainLooper()).postDelayed({
           var homeIntent = Intent(this@SplashScreenActivity, HomeActivity::class.java)
           startActivity(homeIntent)
           finish()
       },2000)
    }
}