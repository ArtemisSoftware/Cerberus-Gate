package com.artemissoftware.cerberusgate

import android.os.Build
import android.os.Bundle
import android.view.MenuItem
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.bottomnavigation.BottomNavigationView


class MainActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {

    lateinit var bottomNavigation: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bottomNavigation = findViewById(R.id.bottom_navigation);
        bottomNavigation.setOnNavigationItemSelectedListener(this)

        //openFragment(SuccessPopupFragment());
        //openFragment(PasswordFragment());
        openFragment(MatrizPasswordFragment());
    }


    fun openFragment(fragment: Fragment) {
        val transaction: FragmentTransaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    fun updateStatusBarColor(colorId: Int, isStatusBarFontDark: Boolean = true) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val window = window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            window.statusBarColor = ContextCompat.getColor(this, colorId)
            //--setSystemBarTheme(isStatusBarFontDark)
        }
    }


    override fun onNavigationItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.navigation_auth_one -> {
                openFragment(OtpFragment())
                return true
            }
            R.id.navigation_auth_two -> {
                //openFragment(SmsFragment.newInstance("", ""))
                return true
            }
            R.id.navigation_auth_three -> {
                openFragment(PasswordFragment())
                return true
            }
            R.id.navigation_auth_four -> {
                openFragment(MatrizPasswordFragment())
                return true
            }
        }
        return false
    }
}