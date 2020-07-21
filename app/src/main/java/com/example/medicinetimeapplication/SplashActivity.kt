package com.example.medicinetimeapplication

import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.example.medicinetimeapplication.apiclasses.Utility
import com.example.medicinetimeapplication.helperclasses.SharedPreferencesHelper
import com.example.medicinetimeapplication.model.Doctor


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
