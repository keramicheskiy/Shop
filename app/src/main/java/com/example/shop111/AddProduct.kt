package com.example.shop111

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import com.example.shop111.databinding.AddProductBinding
import com.example.shop111.utils.Constants
import com.example.shop111.utils.GlideLoader
import java.io.IOException

class AddProduct : BaseActivity(){
    var mUserProfileImageUrl: String = ""
    private lateinit var binding: AddProductBinding
    private var mSelectedProfileImageFileUri: Uri? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = AddProductBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.image.setOnClickListener {
            Constants.showImageChooser(this@AddProduct)
        }

        binding.btnAdd.setOnClickListener {

            FireStoreClass().uploadImageToCloudStorage(this@AddProduct, mSelectedProfileImageFileUri,
                Constants.PRODUCT_IMAGE)
            uploadProductDetails()
        }

    }

    fun uploadProductDetails() {
        val shared = getSharedPreferences(Constants.MY_SHOP_PAL_PREFERENCES, Context.MODE_PRIVATE)
        val userName = shared.getString(Constants.LOGGED_IN_USERNAME, "")

        val productDetails = Product(
            FireStoreClass().getCurrentUserId(),
            userName!!,
            binding.etProductTitle.text.toString().trim{ it<= ' '},
            binding.etProductPrice.text.toString().trim{ it<= ' '},
            binding.etProductDescription.text.toString().trim{ it<= ' '},
            binding.etProductQuantity.text.toString().trim{ it<= ' '},
            mUserProfileImageUrl,
//            product_id =

        )
        Toast.makeText(this@AddProduct, "______________",  Toast.LENGTH_LONG)
        FireStoreClass().uploadProductDetails(this, productDetails)

    }

    fun uploadImageSuccess(link: String) {
        mUserProfileImageUrl = link
        uploadProductDetails()
    }

    fun userProfileUpdateSuccess() {
        Toast.makeText(this, "Данные успешно обновлены", Toast.LENGTH_SHORT).show()
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == Constants.PICK_IMAGE_REQUEST_CODE) {
            if (data!!.data != null) {
                try {
                    mSelectedProfileImageFileUri = data.data
                    GlideLoader(this).loadUserProfile(
                        mSelectedProfileImageFileUri!!,
                        binding.image
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

}