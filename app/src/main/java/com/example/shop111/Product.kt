package com.example.shop111

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Product (
    val user_id: String = "",
    val user_name: String = "",
    val product_title: String = "",
    val product_price: String = "",
    val product_description: String = "",
    val product_quantity: String = "",
    val prduct_image: String = "",
    var product_id: String = "",
    var reviews: ArrayList<Review> = arrayListOf()
//    val a: Review

) : Parcelable
