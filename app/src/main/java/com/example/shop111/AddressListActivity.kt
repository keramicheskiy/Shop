package com.example.shop111

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.shop111.databinding.AddressListActivityBinding

class AddressListActivity : AppCompatActivity() {
    lateinit var binding: AddressListActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = AddressListActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUpActionBar()

        binding.btnAddAddress.setOnClickListener {
            startActivity(Intent(this@AddressListActivity, EditAddressActivity::class.java))
        }

    }







    fun setUpActionBar() {
        setSupportActionBar(binding.toolbar3)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_back_button_24)

        binding.toolbar3.setNavigationOnClickListener(){
            onBackPressed()
        }
    }





}