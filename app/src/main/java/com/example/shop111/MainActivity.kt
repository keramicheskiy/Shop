package com.example.shop111

import android.content.Intent
import com.example.shop111.BaseActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.shop111.databinding.ActivityMainBinding

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.ktx.Firebase
import com.example.shop111.R
import com.example.shop111.utils.Constants

class MainActivity : BaseActivity(){
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.loggingIn.setOnClickListener() {
            signUp()
        }

        binding.homeButton.setOnClickListener() {
            val intend = Intent(this@MainActivity, ActivityChooseWayToEnter::class.java)
            startActivity(intend)
        }
        binding.SignIn.setOnClickListener() {
            val intend = Intent(this@MainActivity, ActivitySignIn::class.java)
            startActivity(intend)
        }




    }

    private fun signUp() {
        val email : String = binding.EmailId.text.toString().trim{ it <= ' '}
        val password : String =  binding.password1.text.toString().trim{ it <= ' '}
        if (validateRergisterDetails()) {
            showProgressDialog(getString(R.string.please_wait))
            FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password).addOnCompleteListener {
                if (it.isSuccessful) {
                    hideProgressDialog()

                    val firebaseUser: FirebaseUser = it.result!!.user!!

                    val userId = firebaseUser.uid
                    val user = User(userId,
                    binding.Nameid.text.toString().trim{ it <= ' '},
                    binding.LastNameId.text.toString().trim{ it <= ' '},
                    binding.EmailId.text.toString().trim{ it <= ' '})
                    FireStoreClass().registerUser(this, user)
                    // регистрация в FireStoreClass

//                FireStoreClass().getUserDetails(this, user)
                } else{
                    hideProgressDialog()
//                    Toast.makeText(

//                    )
                }

            }
        }
//    fun userRegistrationSuccess() {
////        hideProgressDialog()
//        Toast.makeText(this@MainActivity, "hsdhjsd", Toast.LENGTH_SHORT)
//            .show()
//    }




    }




    private fun validateRergisterDetails(): Boolean{

        return when {
            TextUtils.isEmpty(binding.Nameid.text.toString().trim {it <= ' '}) -> {
                showErrorSnackBar("Please, enter Name.", true)
                false
            }
            TextUtils.isEmpty(binding.LastNameId.text.toString().trim {it <= ' '}) -> {
                showErrorSnackBar("Please, enter LastName.", true)
                false
            }
            TextUtils.isEmpty(binding.EmailId.text.toString().trim {it <= ' '}) -> {
                showErrorSnackBar("Please, enter EmailId.", true)
                false
            }
            TextUtils.isEmpty(binding.password1.text.toString().trim {it <= ' '}) -> {
                showErrorSnackBar("Please, enter password1.", true)
                false
            }
            TextUtils.isEmpty(binding.password2.text.toString().trim {it <= ' '}) -> {
                showErrorSnackBar("Please, enter password2.", true)
                false
            }
            (!binding.checkBox.isChecked) -> {
                showErrorSnackBar("Please, accept agreement.", true)
                false
            }
            else -> {
                showErrorSnackBar("Валидация выполнена", false)
                true
            }
        }

    }

    fun userRegistrationSuccess(user: User) {
        hideProgressDialog()
        Toast.makeText(this@MainActivity, "hsdhjsd", Toast.LENGTH_SHORT)
            .show()
        val intend = Intent(this@MainActivity, Profile::class.java)
        intend.putExtra(Constants.EXTRA_USER_DETAILS, user)
//        if (intent.hasExtra(Constants.EXTRA_USER_DETAILS)) {
//            mUserDetails = intent.getParcelableExtra(Constants.EXTRA_USER_DETAILS)!!
        startActivity(intend)
        finish()
    }

    fun userLogInSuccess(user: User) {

        hideProgressDialog()
        val intend = Intent(this@MainActivity, Profile::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        intent.putExtra(Constants.EXTRA_USER_DETAILS, user)
        startActivity(intend)
        finish()


    }



}