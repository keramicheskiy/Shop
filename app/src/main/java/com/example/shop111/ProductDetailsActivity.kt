package com.example.shop111

import android.content.Context
import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.View
import android.view.View.OnClickListener
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.os.bundleOf
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.add
import androidx.navigation.NavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.shop111.adapters.DashboardAdapter
import com.example.shop111.adapters.ReviewAdapter
import com.example.shop111.adapters.UsersReviewAdapter
import com.example.shop111.databinding.ProductDetailsActivityBinding
import com.example.shop111.fragments.BaseFragment
import com.example.shop111.fragments.ReviewActivity
import com.example.shop111.utils.Constants
import com.example.shop111.utils.GlideLoader

class ProductDetailsActivity : BaseActivity(), OnClickListener{
    private lateinit var binding: ProductDetailsActivityBinding
    private var mProductID: String = ""
    private var mOwnerID: String = ""
    private lateinit var mProductDetails: Product
    private lateinit var mUser: User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ProductDetailsActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUpActionBar()

        if (intent.hasExtra(Constants.EXTRA_PRODUCT_ID) && intent.hasExtra(Constants.EXTRA_PRODUCT_OWNER_ID)) {
            mProductID = intent.getStringExtra(Constants.EXTRA_PRODUCT_ID)!!
            mOwnerID = intent.getStringExtra(Constants.EXTRA_PRODUCT_OWNER_ID)!!
        }

//        FireStoreClass().checkIfReviewIsAlreadyAdded(this, mProductID)
        FireStoreClass().getReviews(this, mProductID)
        FireStoreClass().getProductDetails(this@ProductDetailsActivity, mProductID)

        if (mOwnerID == FireStoreClass().getCurrentUserId()) {
            binding.btnAddToCart.visibility = View.GONE
            binding.btnPurchaseNow.visibility = View.GONE
        } else {
            binding.btnAddToCart.visibility = View.VISIBLE
            binding.btnPurchaseNow.visibility = View.VISIBLE
        }

//        BaseActivity().hideProgressDialog()



        binding.btnAddToCart.setOnClickListener(this)
        binding.btnPurchaseNow.setOnClickListener(this)
        binding.btnAddReview.setOnClickListener(this)


    }

    override fun onResume() {
        super.onResume()
        FireStoreClass().getUserDetails(this)

    }


    fun setUpActionBar(){

        setSupportActionBar(binding.toolbar4)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_back_button_24)
        binding.toolbar4.setNavigationOnClickListener(){
            onBackPressed()
        }

    }

    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.btnAddToCart -> {
                addToCart()
            }
            R.id.btnPurchaseNow -> {
                startActivity(Intent(this, CartListActivity::class.java))
            }
//            R.id.btnAddReview -> {
//                val intent = Intent(this, ReviewActivity::class.java)
//                intent.putExtra(Constants.PRODUCT_ID, mProductID)
//                startActivity(intent)
//            }
        }
    }



    fun setDetailsInfo(product: Product) {
        mProductDetails = product
        val binding = binding
        binding.tvProductTitle.text = product.product_title
        binding.tvProductPrice.text = product.product_price
        binding.tvProductDescription.text = product.product_description
        GlideLoader(this).loadUserProfile(product.prduct_image, binding.image)

        if (mProductDetails.product_quantity != "0" && mProductDetails.product_quantity != "") {
            binding.tvProductQuantity.text = product.product_quantity
            FireStoreClass().checkIfItemExistsInCart(this@ProductDetailsActivity, mProductID)
        } else {
            binding.tvProductQuantity.text = "Out of stock!"
//            binding.btnAddToCart.visibility = View.GONE
            binding.btnPurchaseNow.visibility = View.GONE
        }

        FireStoreClass().getReviews(this, mProductID)




    }

    fun addToCart() {
        val cartItem = CartItem(
            FireStoreClass().getCurrentUserId(),
            mOwnerID,
            mProductID,
            mProductDetails.product_title,
            mProductDetails.product_price,
            mProductDetails.prduct_image,
            "1",
            mProductDetails.product_quantity
        )
        FireStoreClass().addToCartInFirebase(this, cartItem)
    }

    fun productIsInTheCart() {
        val binding = binding
        binding.btnAddToCart.visibility = View.GONE
        binding.btnPurchaseNow.visibility = View.VISIBLE
    }

    fun productIsNotInTheCart() {
        val binding = binding
        binding.btnAddToCart.visibility = View.VISIBLE
        binding.btnPurchaseNow.visibility = View.VISIBLE
    }

    fun productAddedSuccess() {
        startActivity(Intent(this, CartListActivity::class.java))
    }



    fun gettingReviewsSuccess(list: MutableList<Review>) {
        val adapter = ReviewAdapter(this, this, mUser,  list)
        binding.rvReviews.layoutManager = LinearLayoutManager(this)//GridLayoutManager(this, 1)
        binding.rvReviews.setHasFixedSize(true)
        binding.rvReviews.adapter = adapter

        binding.rvReviews.visibility  = View.VISIBLE
        binding.ll4.visibility  = View.GONE
    }

    fun getDetailsSuccess(user: User) {
        mUser = user
        val adapter = UsersReviewAdapter(this, this@ProductDetailsActivity, mUser, mProductID)
        binding.rvUserReview.layoutManager = LinearLayoutManager(this)
        binding.rvUserReview.setHasFixedSize(true)
        binding.rvUserReview.adapter = adapter
    }

    fun addingReviewSuccess() {
//        FireStoreClass().getReviews(this, mProductID)
        Toast.makeText(this, "Отзыв добавлен.", Toast.LENGTH_SHORT).show()
    }

    fun deletingReviewSuccess() {
//        FireStoreClass().getReviews(this, mProductID)
        Toast.makeText(this, "Отзыв удалён..", Toast.LENGTH_SHORT).show()
    }

    fun hideButtonAddReview() {
        binding.btnAddReview.visibility = View.GONE
    }

}