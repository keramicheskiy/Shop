package com.example.shop111

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Review(
    val product_id: String = "",
    val user_id: String = "",
    val reviewText: String = "",
    val stars: Int = 5,
    val date: Long = 0L,
    val id: String = "",
) : Parcelable
