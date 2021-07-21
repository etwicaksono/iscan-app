package com.etwicaksono.iscan.ui.activities.splashscreen

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.etwicaksono.iscan.databinding.ActivitySplashScreenBinding
import com.etwicaksono.iscan.ui.activities.BaseActivity
import com.etwicaksono.iscan.ui.activities.home.HomeActivity
import com.etwicaksono.iscan.utils.UserPref
import org.json.JSONArray

class SplashScreenActivity : BaseActivity() {

    private lateinit var binding: ActivitySplashScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val tester = JSONArray(arrayListOf<Int>(7,8,9,10,11,12,1,2,3,4,5,6)).toString()
        UserPref(this).setValue("recent_store",tester)

       Handler(Looper.getMainLooper()).postDelayed({
           var homeIntent = Intent(this@SplashScreenActivity, HomeActivity::class.java)
           startActivity(homeIntent)
           finish()
       },2000)
    }
}