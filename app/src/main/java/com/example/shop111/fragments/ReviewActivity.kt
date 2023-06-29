package com.example.shop111.fragments

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import com.example.shop111.BaseActivity
import com.example.shop111.FireStoreClass
import com.example.shop111.ProductDetailsActivity
import com.example.shop111.Review
import com.example.shop111.databinding.ReviewActivityBinding
import java.util.Calendar
import com.example.shop111.utils.Constants

class ReviewActivity : BaseActivity() {
    lateinit var binding: ReviewActivityBinding
    var mProductID : String = ""

    val day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
    val month = Calendar.getInstance().get(Calendar.MONTH) + 1
    val year = Calendar.getInstance().get(Calendar.YEAR)
    val date = "$day.$month.$year"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ReviewActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (intent.hasExtra(Constants.PRODUCT_ID)) {
            mProductID = intent.getStringExtra(Constants.PRODUCT_ID)!!
        }


        binding.btnPublish.setOnClickListener {
            if (validate()) {
                val review = Review(
                    id = mProductID,
                    user_id = FireStoreClass().getCurrentUserId(),
                    reviewText = binding.etReview.text.toString().trim(),
                    stars = binding.stars.numStars,
                    date = System.currentTimeMillis(),
                )
                FireStoreClass().addReview(this, review)
            }
        }
    }



    fun validate() : Boolean {
        return when {
            TextUtils.isEmpty(binding.etReview.text.toString().trim()) -> {
                Toast.makeText(this, "Fill the review", Toast.LENGTH_SHORT).show()
                false
            }
            else -> true
        }
    }

    fun addingReviewSuccess() {
        startActivity(Intent(this@ReviewActivity, ProductDetailsActivity::class.java))
    }


}