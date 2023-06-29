package com.example.shop111

import android.os.Bundle
import android.text.TextUtils
import androidx.appcompat.app.AppCompatActivity
import com.example.shop111.databinding.AddressListActivityBinding
import com.example.shop111.databinding.EditAddressActivityBinding
import com.example.shop111.utils.Constants

class EditAddressActivity : BaseActivity() {
    lateinit var binding: EditAddressActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = EditAddressActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUpActionBar()

        binding.btnSubmit.setOnClickListener {
            val FullName = binding.etFullName.text.toString().trim{it<=' '}
            val Number = binding.etNumber.text.toString().trim{it<=' '}
            val Address = binding.etAddress.text.toString().trim{it<=' '}
            val ZipCode = binding.etZipCode.text.toString().trim{it<=' '}
            val AdditionalNote = binding.etAdditionalNote.text.toString().trim{it<=' '}

            if (validate()) {
                val addressType : String = when{
                    binding.rbHome.isChecked -> Constants.HOME
                    binding.rbOffice.isChecked -> Constants.OFFICE
                    else -> Constants.OTHER
                }
            }
        }

    }


    fun validate() : Boolean{
        val FullName = binding.etFullName.text.toString().trim{it<=' '}
        val Number = binding.etNumber.text.toString().trim{it<=' '}
        val Address = binding.etAddress.text.toString().trim{it<=' '}
        val ZipCode = binding.etZipCode.text.toString().trim{it<=' '}
        val AdditionalNote = binding.etAdditionalNote.text.toString().trim{it<=' '}

        return when {
            TextUtils.isEmpty(FullName) -> {
                showErrorSnackBar("Заполните Имя", true)
                false
            }TextUtils.isEmpty(Number) -> {
                showErrorSnackBar("Заполните Номер телефона", true)
                false
            }TextUtils.isEmpty(Address) -> {
                showErrorSnackBar("Заполните Адрес", true)
                false
            }TextUtils.isEmpty(ZipCode) -> {
                showErrorSnackBar("Заполните Zip-code", true)
                false
            }
            else -> {
                showErrorSnackBar("Валидация выполнена", false)
                true
            }
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