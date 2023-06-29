package com.example.shop111


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.shop111.databinding.ActivityChooseWayToEnterBinding

class ActivityChooseWayToEnter : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityChooseWayToEnterBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.toSignInButton.setOnClickListener() {
            val intend = Intent(this@ActivityChooseWayToEnter, ActivitySignIn::class.java)
            startActivity(intend)
        }
        binding.toSignUpButton.setOnClickListener() {
            val intend = Intent(this@ActivityChooseWayToEnter, MainActivity::class.java)
            startActivity(intend)
        }

    }
}