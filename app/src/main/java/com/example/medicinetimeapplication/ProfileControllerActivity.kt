package com.example.medicinetimeapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class ProfileControllerActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_controller)

        var id:Int = intent.getIntExtra("id_replaced_fragment",0)

        when (id) {
            1 -> {

            }
            2 -> {

            }
            3 -> {

            }
            4 -> {

            }
            5 -> {

            }
            else -> {

            }
        }
    }


}
