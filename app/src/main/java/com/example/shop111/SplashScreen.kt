package com.example.shop111

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.Window
import android.view.WindowManager
import com.example.shop111.utils.Constants
import com.example.shop111.FireStoreClass


@Suppress("DEPRECATION")
class SplashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.splash_screen)



        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        supportActionBar?.hide()


        Handler(Looper.getMainLooper()).postDelayed({
            if (FireStoreClass().getUserID().isNotEmpty()) {
                val intent = Intent(this@SplashScreen, MainPage::class.java)
                intent.putExtra(Constants.USER, FireStoreClass().getUserEmail())
                startActivity(intent)
            } else {
                val intent = Intent(this@SplashScreen, ActivityChooseWayToEnter::class.java)
                startActivity(intent)
            }

            finish()
        }, 2000)

    }

}