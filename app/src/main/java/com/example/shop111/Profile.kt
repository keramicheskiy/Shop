package com.example.shop111

import android.Manifest
import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import com.example.shop111.BaseActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.shop111.databinding.ProfileBinding

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.ktx.Firebase
import com.example.shop111.R
import com.example.shop111.fragments.DashboardFragment
import com.example.shop111.utils.Constants
import com.example.shop111.utils.GlideLoader
import com.google.firebase.auth.ktx.auth
import java.io.IOException

class Profile : BaseActivity(), View.OnClickListener {
    private lateinit var binding: ProfileBinding
    private var mProgressDialog: Dialog? = null
    private lateinit var mUserDetails: User
    private var mSelectedProfileImageFileUri: Uri? = null
    private var mUserProfileImageUrl: String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (intent.hasExtra(Constants.EXTRA_USER_DETAILS)) {
            mUserDetails = intent.getParcelableExtra(Constants.EXTRA_USER_DETAILS)!!

            binding.etFirstName.setText(mUserDetails.firstName)
            binding.etLastName.setText(mUserDetails.lastName)
            binding.email.text = mUserDetails.email


            if (mUserDetails.profileCompleted == 0) {
                binding.toolbar3.title = "Complete profile"

                binding.etFirstName.isEnabled = false
                binding.etLastName.isEnabled = false

            } else {
                setUpActionBar()
                binding.toolbar3.title = "Edit profile"

                GlideLoader(this).loadUserProfile(mUserDetails.image, binding.imageView2)

                if (mUserDetails.mobile != 0L) {
                    binding.etMobileNumber.setText(mUserDetails.mobile.toString())
                }

            }
        }
        binding.imageView2.setOnClickListener(this)
        binding.btnSubmit.setOnClickListener(this)






    }

    fun setUpActionBar(){
        setSupportActionBar(binding.toolbar3)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_back_button_24)

        binding.toolbar3.setNavigationOnClickListener(){
            onBackPressed()

        }

    }


    override fun onClick(v: View?){
        when(v?.id) {
            R.id.imageView2 -> {
                if (ContextCompat.checkSelfPermission(
                        this,
                        Manifest.permission.READ_EXTERNAL_STORAGE
                    ) == PackageManager.PERMISSION_GRANTED
                ) {
                    Constants.showImageChooser(this)
                } else {
                    ActivityCompat.requestPermissions(
                        this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                        Constants.READ_STORAGE_PERMISSION_CODE
                    )
                }

            }
            R.id.btnSubmit -> {
                mUserProfileImageUrl = mSelectedProfileImageFileUri.toString()
                if (mSelectedProfileImageFileUri != null) {
                    FireStoreClass().uploadImageToCloudStorage (
                        this,
                        mSelectedProfileImageFileUri,
                        Constants.USER_PROFILE_IMAGE
                    )

                } else {
                    updateProfileData()
                }
//                val intent = Intent(this@Profile, DashboardFragment::class.java)
//                startActivity(intent)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == Constants.PICK_IMAGE_REQUEST_CODE) {
            if (data!!.data != null) {
                try {
                    mSelectedProfileImageFileUri = data.data
                    GlideLoader(this).loadUserProfile(
                        mSelectedProfileImageFileUri!!,
                        binding.imageView2
                    )
                } catch (e: IOException) {
                    e.printStackTrace()
                    Toast.makeText(
                        this,
                        "Something went wrong during image selection",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (
            requestCode == Constants.READ_STORAGE_PERMISSION_CODE &&
            grantResults.isNotEmpty() &&
            grantResults[0] == PackageManager.PERMISSION_GRANTED
        ) {
            Constants.showImageChooser(this)
        } else {
            Toast.makeText(
                this,
                "You denied the permission needed",
                Toast.LENGTH_LONG
            ).show()
        }



    }


    fun updateProfileData() {
        val userHashMap: HashMap<String, Any> = HashMap()


        if (mUserDetails.firstName != binding.etFirstName.text.toString().trim { it <= ' ' }) {
            userHashMap["firstName"] = binding.etFirstName.text.toString().trim { it <= ' ' }
        }
        if (mUserDetails.lastName != binding.etLastName.text.toString().trim { it <= ' ' }) {
            userHashMap["lastName"] = binding.etLastName.text.toString().trim { it <= ' ' }
        }
        if (mUserDetails.mobile != binding.etMobileNumber.text.toString().trim { it <= ' ' }.toLong()
            && binding.etMobileNumber.text.toString().trim { it <= ' ' }.length > 10) {
            userHashMap["mobile"] = binding.etMobileNumber.text.toString().trim { it <= ' ' }.toLong()
            userHashMap["profileCompleted"] = 1
        }
        if (mUserProfileImageUrl.isNotEmpty()) {
            userHashMap["image"] = mUserProfileImageUrl
        }


        FireStoreClass().updateUserDetails(this@Profile, userHashMap, mUserDetails)

    }

    fun userProfileUpdateSuccess() {
        Toast.makeText(this, "Данные успешно обновлены", Toast.LENGTH_SHORT).show()
    }

    fun uploadImageSuccess(link: String) {
        mUserProfileImageUrl = link
        updateProfileData()
    }


}