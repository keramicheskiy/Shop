package com.example.shop111


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import com.example.shop111.databinding.ActivitySignInBinding
import com.example.shop111.utils.Constants
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class ActivitySignIn : BaseActivity() {
    private lateinit var binding: ActivitySignInBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.btnRegister.setOnClickListener() {
            val intend = Intent(this@ActivitySignIn, MainActivity::class.java)
            startActivity(intend)
        }

        binding.homeButton.setOnClickListener() {
            val intend = Intent(this@ActivitySignIn, ActivityChooseWayToEnter::class.java)
            startActivity(intend)
        }

        binding.signInButton.setOnClickListener() {
            logIn()
        }

    }

    private fun logIn() {
        val mBinding = binding
        if (validateRergisterDetails()) {
            showProgressDialog("Please wait...")
            val email = mBinding.enterEmail.text.toString().trim{ it <= ' '}
            val password = mBinding.enterPassword.text.toString().trim{ it <= ' '}
            val x = mBinding.enterEmail.text.toString()
            FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password).addOnCompleteListener{
                if (it.isSuccessful) {
                    hideProgressDialog()
                    FireStoreClass().getUserDetails(this@ActivitySignIn)

                } else {
                    hideProgressDialog()
                }


            }


        }


    }





    private fun validateRergisterDetails(): Boolean {
        val TextInEnterEmail: String = binding.enterEmail.text.toString().trim { it <= ' ' }
        val TextInEnterPassword: String = binding.enterPassword.text.toString().trim { it <= ' ' }

        return when {
            TextUtils.isEmpty(TextInEnterEmail) -> {
                showErrorSnackBar("Please, enter e-mail.", true)
                false
            }
            TextUtils.isEmpty(TextInEnterPassword) -> {
                showErrorSnackBar("Please, enter password.", true)
                false
            }
            else -> {
                showErrorSnackBar("Plese, wait", true)
                true
            }
        }
    }

    fun loggedInSuccess(user: User) {


        if (user.profileCompleted == 0) {
            val intent = Intent(this@ActivitySignIn, Profile::class.java)
            intent.putExtra(Constants.EXTRA_USER_DETAILS, user)
            startActivity(intent)
        } else {
            val intent = Intent(this@ActivitySignIn, MainPage::class.java)
            startActivity(intent)
        }


//        val intent = Intent(this@ActivitySignIn, Profile::class.java)
//        intent.putExtra(Constants.EXTRA_USER_DETAILS, user)
//        startActivity(intent)
    }



}

