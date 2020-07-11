package com.example.medicinetimeapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.medicinetimeapplication.fragments.DashboardFragment
import com.example.medicinetimeapplication.fragments.HomeFragment
import com.example.medicinetimeapplication.fragments.NotificationsFragment
import com.example.medicinetimeapplication.fragments.ProfileFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val mOnNavigationItemSelectedListener =
        BottomNavigationView.OnNavigationItemSelectedListener { item ->
            when (item.itemId) {

                R.id.navigation_home -> {
                    replaceFragment(HomeFragment())
                    return@OnNavigationItemSelectedListener true
                }
                R.id.navigation_dashboard -> {
                    replaceFragment(DashboardFragment())
                    return@OnNavigationItemSelectedListener true
                }
                R.id.navigation_notifications -> {
                    replaceFragment(NotificationsFragment())
                    return@OnNavigationItemSelectedListener true
                }
                R.id.navigation_profile ->{
                    replaceFragment(ProfileFragment())
                    return@OnNavigationItemSelectedListener true
                }
            }
            false
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        nav_view.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        nav_view.selectedItemId =R.id.navigation_home
    }

    private fun replaceFragment(fragment: Fragment){
        supportFragmentManager.beginTransaction().replace(R.id.mainContainer,
            fragment).commit()
    }
}
