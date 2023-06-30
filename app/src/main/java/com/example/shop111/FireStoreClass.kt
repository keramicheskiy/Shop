package com.example.shop111

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.widget.Adapter
import android.widget.Toast
import androidx.constraintlayout.widget.Constraints
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.example.shop111.adapters.ReviewAdapter
import com.example.shop111.fragments.DashboardFragment
import com.example.shop111.fragments.ProductsFragment
import com.example.shop111.fragments.ReviewActivity
import com.example.shop111.utils.Constants
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

class FireStoreClass {

    private val mFireStore = FirebaseFirestore.getInstance()
    val Auth = FirebaseAuth.getInstance()

    fun registerUser(activity: MainActivity, user: User) {
        mFireStore.collection("users")
            .document(getCurrentUserId())
            .set(user, SetOptions.merge())
            .addOnSuccessListener {
                activity.userRegistrationSuccess(user)

            }.addOnFailureListener { exception ->
                activity.hideProgressDialog()
                Log.e(activity.javaClass.simpleName, exception.message.toString())
            }
    }

    fun uploadImageToCloudStorage(activity: Activity, imageFileUri: Uri?, imageType: String) {
        val shrf: StorageReference = FirebaseStorage.getInstance().reference.child(
            imageType + System.currentTimeMillis() + "."
                    + Constants.getFileExtension(activity, imageFileUri)
        )
        shrf.putFile(imageFileUri!!)
            .addOnSuccessListener { snapShot ->
                snapShot.metadata!!.reference!!.downloadUrl
                    .addOnSuccessListener { uri ->
                        when (activity) {
                            is Profile -> activity.uploadImageSuccess(uri.toString())
                            is AddProduct -> activity.uploadImageSuccess(uri.toString())
                        }
                    }
            }
    }

    fun getCurrentUserId() : String {
        val currentUser = FirebaseAuth.getInstance().currentUser
        var currentUserId = ""
        if (currentUser != null) {
            currentUserId = currentUser.uid
        }
        return currentUserId
    }


    fun updateUserDetails(activity: Activity, hashMap: HashMap<String, Any>, user: User) {
        mFireStore.collection("users")
            .document(getCurrentUserId())
            .update(hashMap)
            .addOnSuccessListener {
                when (activity) {
                    is Profile -> activity.userProfileUpdateSuccess()
                }
            }.addOnFailureListener {
                Toast.makeText(activity, "Не удалось обновить данные", Toast.LENGTH_SHORT).show()
                Toast.makeText(activity, it.toString(), Toast.LENGTH_SHORT).show()
            }

    }

    fun getUserDetails(activity: Activity) {

        mFireStore.collection("users")
            .document(getCurrentUserId())
            .get().addOnSuccessListener {
                val user: User? = it.toObject(User::class.java)
                if (user != null) {
                    when (activity) {
                        is ActivitySignIn -> activity.loggedInSuccess(user)
                        is ProductDetailsActivity -> activity.getDetailsSuccess(user)
                    }
                }

            }

    }

    fun uploadProductDetails(activity: Activity, productDetails: Product) {
        mFireStore.collection(Constants.PRODUCTS)
            .document()
            .set(productDetails, SetOptions.merge())
            .addOnSuccessListener {
                when (activity) {
                    is AddProduct -> activity.userProfileUpdateSuccess()
                }
            }.addOnFailureListener {
                Toast.makeText(activity, "Не удалось обновить данные", Toast.LENGTH_SHORT).show()
                Toast.makeText(activity, it.toString(), Toast.LENGTH_SHORT).show()
            }

    }

    fun getProductList(fragment: Fragment) {
        mFireStore.collection(Constants.PRODUCTS)
            .whereEqualTo(Constants.USER_ID, getCurrentUserId())
            .get()
            .addOnSuccessListener { document ->
                val productList: ArrayList<Product> = ArrayList()

                for (i in document.documents) {
                    val product = i.toObject(Product::class.java)
                    product!!.product_id = i.id
                    productList.add(product)
                }
                when(fragment) {
                    is ProductsFragment -> fragment.successProductListFromFirestore(productList)
                }
            }
            .addOnFailureListener {
                when (fragment) {
                    is ProductsFragment -> BaseActivity().hideProgressDialog()
                }
            }

    }

    fun getProductDetails(activity: Activity, mProductID: String) {
        mFireStore.collection(Constants.PRODUCTS)
            .document(mProductID)
            .get()
            .addOnSuccessListener {
                val product = it.toObject(Product::class.java)

                if (product != null) {
                    when (activity) {
                        is ProductDetailsActivity -> activity.setDetailsInfo(product)

                    }
                }
            }

    }

    fun checkIfItemExistsInCart (activity: Activity, productId : String) {
        mFireStore.collection(Constants.CART_ITEMS)
            .whereEqualTo(Constants.USER_ID, getCurrentUserId())
            .whereEqualTo(Constants.PRODUCT_ID, productId)
            .get()
            .addOnSuccessListener {
                if (it.documents.size > 0) {
                    when(activity) {
                        is ProductDetailsActivity -> activity.productIsInTheCart()
                    }
                } else {
                    when(activity) {
                        is ProductDetailsActivity -> activity.productIsNotInTheCart()
                    }
                }
            }


    }

    fun addToCartInFirebase(activity: Activity, cartItem: CartItem) {
        mFireStore.collection(Constants.CART_ITEMS)
            .document()
            .set(cartItem)
            .addOnSuccessListener {
                when(activity) {
                    is ProductDetailsActivity -> activity.productAddedSuccess()

                }

            }. addOnFailureListener {
                Toast.makeText(activity, "Ошибка! ${it.message}", Toast.LENGTH_LONG).show()///---------------------
            }
    }


    fun removeItemFromCart(context: Context, cardId: String) {
        mFireStore.collection(Constants.CART_ITEMS)
            .document(cardId)
            .delete()
            .addOnSuccessListener {
                when(context) {
                    is CartListActivity -> {
                        context.removeItemSuccess()
                    }
                }
            }
            .addOnFailureListener {
                when(context) {
                    is CartListActivity -> {
                        context.removeItemFailure()
                    }
                }
            }


    }


    fun updateMyCart(context: Context, cardId: String, hashMap: HashMap<String, Any>) {
        mFireStore.collection(Constants.CART_ITEMS)
            .document(cardId)
            .update(hashMap)
            .addOnSuccessListener {
                when(context) {
                    is CartListActivity -> context.cartUpdateSuccess()
                }
            }


    }


    fun getAllProductList(activity: Activity) {
        mFireStore.collection(Constants.PRODUCTS)
            .get()
            .addOnSuccessListener {

                val productList: ArrayList<Product> = ArrayList()

                for (i in it.documents) {
                    val product = i.toObject(Product::class.java)!!
                    product.product_id = i.id
                    productList.add(product)
                }

                when(activity) {
                    is CartListActivity -> activity.successProductListFromFireStore(productList)
                }
            }



    }


    fun getCartItemList(activity: Activity) {
        mFireStore.collection(Constants.CART_ITEMS)
            .whereEqualTo(Constants.USER_ID, getCurrentUserId())
            .get()
            .addOnSuccessListener {
                var cartList: ArrayList<CartItem> = ArrayList()

                if (it.documents.size != 0) {
                    for (i in it.documents) {
                        val cartItem = i.toObject(CartItem::class.java)
                        if (cartItem != null) {
                            cartItem.id = i.id
                            cartList.add(cartItem)
                        }
                        when (activity) {
                            is CartListActivity -> activity.successCartItemList(cartList)
                        }
                    }
                } else {
                    when (activity) {
                        is CartListActivity -> activity.successCartItemList(cartList)
                    }
                }

            }.addOnFailureListener {
                when (activity) {
                    is CartListActivity -> activity.failureCartItemList()
                }
            }
    }


    fun getProductListFromDashboard(fragment: Fragment) {
        mFireStore.collection(Constants.PRODUCTS)
            .get()
            .addOnSuccessListener {
                var productList: ArrayList<Product> = ArrayList()

                for (i in it.documents) {
                    val product = i.toObject(Product::class.java)
                    product!!.product_id = i.id
                    productList.add(product)
                }
                when (fragment) {
                    is DashboardFragment -> fragment.successDashBoardItemList(productList)
                }
            }
    }


    fun getUserEmail() : String? {
        val currentUser = Auth.currentUser
        var currentUserEmail = ""
        if (currentUser != null) {
            currentUserEmail = currentUser.email!!
        }
        return currentUserEmail
    }

    fun getUserID() : String {
        val currentUser = FirebaseAuth.getInstance().currentUser
        var currentUserId = ""
        if (currentUser != null) {
            currentUserId = currentUser.uid
        }
        return currentUserId
    }


    fun addReview1(activity: Activity, review: Review) {
        mFireStore.collection(Constants.REVIEWS)
            .document(getCurrentUserId())
            .set(review)
            .addOnSuccessListener {
                when (activity) {
                    is ProductDetailsActivity -> activity.addingReviewSuccess()
                    is ReviewActivity -> activity.addingReviewSuccess()
                }
            }
//        mFireStore.collection(Constants.PRODUCTS)
//            .document(review.id)
//            .collection(Constants.REVIEWS)
//            .document(getCurrentUserId())
//            .set(review)
//            .addOnSuccessListener {
//                when(activity) {
//                    is ProductDetailsActivity -> activity.addingReviewSuccess()
//                    is ReviewActivity -> activity.addingReviewSuccess()
//                }
//            }
    }

    fun deleteReview1(activity: Activity, productId: String) {
        mFireStore.collection(Constants.REVIEWS)
            .document(getCurrentUserId())
            .delete()
            .addOnSuccessListener{
//                getReviews1(activity, productId)

            }
    }

    fun getReviews1(activity: Activity, productId: String) {
        mFireStore.collection(Constants.REVIEWS)
            .whereEqualTo(Constants.ID, productId)
            .get()
            .addOnSuccessListener{
                var list = mutableListOf<Review>()
                for (i in it.documents) {
                    list.add(i.toObject<Review>()!!)
                }
                when(activity) {
                    is ProductDetailsActivity -> activity.gettingReviewsSuccess(list)
                }
            }
    }

    fun checkIfReviewIsAlreadyAdded1(activity: Activity, productId: String) {
        mFireStore.collection(Constants.REVIEWS)
            .whereEqualTo(Constants.USER_ID, getCurrentUserId())
            .whereEqualTo(Constants.ID, productId)
            .get()
            .addOnSuccessListener {
                val a = it.documents.size
                if(it.documents.size != 0) {
                    when(activity) {
                        is ProductDetailsActivity -> activity.hideButtonAddReview()
                    }
                }
            }
    }


    fun addReview(activity: Activity, review: Review) {
        mFireStore.collection(Constants.PRODUCTS)
            .document(review.product_id)
            .collection(Constants.REVIEWS)
            .document(review.user_id)
            .set(review)
            .addOnSuccessListener {
                when(activity) {
                    is ProductDetailsActivity -> activity.addingReviewSuccess()
                }
            }
    }

    fun deleteReview(activity: Activity, productId: String) {
        mFireStore.collection(Constants.PRODUCTS)
            .document(productId)
            .collection(Constants.REVIEWS)
            .document(getCurrentUserId())
            .delete()
            .addOnSuccessListener {
                when(activity) {
                    is ProductDetailsActivity -> activity
                }
            }

    }

    fun getReviews(activity: Activity, productId: String) {
        mFireStore.collection(Constants.PRODUCTS)
            .document(productId)
            .collection(Constants.REVIEWS)
            .get()
            .addOnSuccessListener {
                var list = mutableListOf<Review>()
                for (i in it.documents) {
                    list.add(i.toObject<Review>()!!)
                }
                when(activity) {
                    is ProductDetailsActivity -> activity.gettingReviewsSuccess(list)
                }
            }
    }






}