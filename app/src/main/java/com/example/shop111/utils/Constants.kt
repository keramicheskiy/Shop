package com.example.shop111.utils

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.provider.MediaStore
import android.webkit.MimeTypeMap
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import io.grpc.Context.Storage

object Constants {


    const val USERS: String = "users"
    const val USER: String = "user"
    const val PRODUCTS: String = "products"
    const val REVIEWS: String = "reviews"


    const val EXTRA_USER_DETAILS: String = "extra_user_details"
    const val USER_PROFILE_IMAGE: String = "user_profile_image"
    const val MY_SHOP_PAL_PREFERENCES: String = "MY_SHOP_PAL_PREFERENCES"
    const val LOGGED_IN_USERNAME: String = "LOGGED_IN_USERNAME"
    const val USER_ID: String = "user_id"
    const val EXTRA_PRODUCT_ID: String = "EXTRA_PRODUCT_ID"
    const val EXTRA_PRODUCT_OWNER_ID: String = "EXTRA_PRODUCT_OWNER_ID"
    const val PICK_IMAGE_REQUEST_CODE: Int = 1
    const val READ_STORAGE_PERMISSION_CODE: Int = 2
    const val CART_ITEMS: String = "cart_items"
    const val PRODUCT_ID: String = "product_id"
    const val CART_QUANTITY: String = "cart_quantity"
    const val PRODUCT_IMAGE: String = "PRODUCT_IMAGE"
    const val HOME: String = "home"
    const val OFFICE: String = "office"
    const val OTHER: String = "other"
    const val ID: String = "id"


    const val PLEASE_WAIT: String = "Please wait.."


    fun showImageChooser(activity: Activity){
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        activity.startActivityForResult(intent, PICK_IMAGE_REQUEST_CODE)


    }

    fun getFileExtension(activity: Activity, uri: Uri?) : String? {

        return MimeTypeMap.getSingleton().getMimeTypeFromExtension(activity.contentResolver.getType(uri!!))

    }

//    fun uploadImageToCloudStorage(activity: Activity, imageFileUti: Uri?, imageType: String) {
//        val shrf: StorageReference = FirebaseStorage.getInstance().reference.child(
//            imageType + System.currentTimeMillis() + "."
//                    + Constants.getFileExtension(activity, imageFileUti)
//        )
//        shrf.putFile(imageFileUti!!)
//    }


}