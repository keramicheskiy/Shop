//package com.example.shop111.fragments
//
//import android.content.Intent
//import android.os.Bundle
//import android.view.LayoutInflater
//import android.view.View
//import android.view.View.OnClickListener
//import android.view.ViewGroup
//import androidx.fragment.app.Fragment
//import androidx.fragment.app.FragmentTransaction
//import androidx.fragment.app.activityViewModels
//import androidx.navigation.NavController
//import androidx.navigation.Navigation
//import androidx.recyclerview.widget.GridLayoutManager
//import com.example.shop111.CartItem
//import com.example.shop111.CartListActivity
//import com.example.shop111.FireStoreClass
//import com.example.shop111.Product
//import com.example.shop111.R
//import com.example.shop111.Review
//import com.example.shop111.adapters.ReviewAdapter
//import com.example.shop111.databinding.ProductDetailsActivityBinding
//import com.example.shop111.databinding.ProductDetailsFragmentBinding
//import com.example.shop111.utils.Constants
//import com.example.shop111.utils.GlideLoader
//
//class ProductDetailsFragment : Fragment(), OnClickListener {
//    var _binding: ProductDetailsFragmentBinding? = null
//    val binding get() = _binding!!
//    var mProductID: String = ""
//    var mOwnerID: String = ""
//    private lateinit var navController: NavController
//    private lateinit var mProductDetails: Product
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        mProductID = arguments?.getString("mProductID")!!
//        mOwnerID = arguments?.getString("mOwnerID")!!
//    }
//
//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        _binding = ProductDetailsFragmentBinding.inflate(inflater, container, false)
//
//
//
//        return binding.root
//    }
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//        navController = Navigation.findNavController(view)
//
//        FireStoreClass().getReviews(this)
//
//
//
//
//        if (mOwnerID == FireStoreClass().getCurrentUserId()) {
//            binding.btnAddToCart.visibility = View.GONE
//            binding.btnPurchaseNow.visibility = View.GONE
//        } else {
//            binding.btnAddToCart.visibility = View.VISIBLE
//            binding.btnPurchaseNow.visibility = View.VISIBLE
//        }
//
//
//        FireStoreClass().getProductDetails(this@ProductDetailsFragment, mProductID)
//
//        binding.btnAddToCart.setOnClickListener(this)
//        binding.btnPurchaseNow.setOnClickListener(this)
//        binding.btnAddReview.setOnClickListener(this)
//
//    }
//
//    override fun onClick(v: View?) {
//        when(v?.id) {
//            R.id.btnAddToCart -> {
//                addToCart()
//            }
//            R.id.btnPurchaseNow -> {
//                startActivity(Intent(requireContext(), CartListActivity::class.java))
//            }
//            R.id.btnAddReview -> {
//                navController.navigate(R.id.action_productDetailsFragment_to_reviewFragment)
//            }
//        }
//    }
//
//
//
//    fun setDetailsInfo(product: Product) {
//        mProductDetails = product
//        val binding = binding
//
//        binding.tvProductTitle.text = product.product_title
//        binding.tvProductPrice.text = product.product_price
//        binding.tvProductDescription.text = product.product_description
//        GlideLoader(requireContext()).loadUserProfile(product.prduct_image, binding.image)
//
//        if (mProductDetails.product_quantity != "0" && mProductDetails.product_quantity != "") {
//            binding.tvProductQuantity.text = product.product_quantity
//            FireStoreClass().checkIfItemExistsInCart(this@ProductDetailsFragment, mProductID)
//        } else {
//            binding.tvProductQuantity.text = "Out of stock!"
////            binding.btnAddToCart.visibility = View.GONE
//            binding.btnPurchaseNow.visibility = View.GONE
//        }
//
//        FireStoreClass().getReviews(this)
//
//
//
//
//    }
//
//    fun addToCart() {
//        val cartItem = CartItem(
//            FireStoreClass().getCurrentUserId(),
//            mOwnerID,
//            mProductID,
//            mProductDetails.product_title,
//            mProductDetails.product_price,
//            mProductDetails.prduct_image,
//            "1",
//            mProductDetails.product_quantity
//        )
//        FireStoreClass().addToCartInFirebase(this, cartItem)
//    }
//
//    fun productIsInTheCart() {
//        val binding = binding
//        binding.btnAddToCart.visibility = View.GONE
//        binding.btnPurchaseNow.visibility = View.VISIBLE
//    }
//
//    fun productIsNotInTheCart() {
//        val binding = binding
//        binding.btnAddToCart.visibility = View.VISIBLE
//        binding.btnPurchaseNow.visibility = View.VISIBLE
//    }
//
//    fun productAddedSuccess() {
//        startActivity(Intent(activity, CartListActivity::class.java))
//    }
//
//
//
//    fun gettingReviewsSuccess(list: MutableList<Review>) {
//        val adapter = ReviewAdapter(requireContext(), list, this)
//        binding.rvReviews.layoutManager = GridLayoutManager(requireContext(), 2)
//        binding.rvReviews.setHasFixedSize(true)
//        binding.rvReviews.adapter = adapter
//
//        binding.rvReviews.visibility  = View.VISIBLE
//    }
//
//    fun addingReviewSuccess() {
//        FireStoreClass().getReviews(this)
//    }
//
//}