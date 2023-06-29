package com.example.shop111

import android.os.Bundle
import androidx.databinding.DataBindingUtil.setContentView
import com.example.shop111.databinding.SettingsActivityBinding

class SettingsActivity : BaseActivity(){
    private lateinit var binding: SettingsActivityBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = SettingsActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)



    }
}