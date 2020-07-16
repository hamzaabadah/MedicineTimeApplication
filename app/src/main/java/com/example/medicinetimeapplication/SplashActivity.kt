package com.example.medicinetimeapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.example.medicinetimeapplication.apiclasses.Utility

class SplashActivity : AppCompatActivity() {

    private val SPLASH_DISPLAY_LENGTH = 3000
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Handler().postDelayed({

            // Check if user logged in before or not
            val preferences = getSharedPreferences("isLogin", MODE_PRIVATE)
            var logged_in= preferences.getString("token","")

            if (logged_in?.isNotEmpty()!!) {
                Utility.startNewActivity(this,MainActivity::class.java)

            }else{
                Utility.startNewActivity(this,PatientActivity::class.java)
            }

        }, SPLASH_DISPLAY_LENGTH.toLong())
    }
}
