package com.example.shop111

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.shop111.adapters.CartListAdapter
import com.example.shop111.databinding.CartListActivityBinding
import com.musfickjamil.snackify.Snackify

class CartListActivity : BaseActivity(){
    lateinit var binding: CartListActivityBinding
    lateinit var listOfAllProducts: ArrayList<Product>
    lateinit var cartItemList: ArrayList<CartItem>
    lateinit var productId: String

    override fun onCreate(savedInstanceState : Bundle?) {
        super.onCreate(savedInstanceState)
        binding = CartListActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUpActionBar()

    }

    override fun onResume() {
        super.onResume()

        getProductsList()

        binding.btnCheckout.setOnClickListener {
            val intent = Intent(this@CartListActivity, AddressListActivity::class.java)
            startActivity(intent)
        }

    }




    fun setUpActionBar(){
        setSupportActionBar(binding.toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_back_button_24)

        binding.toolbar.setNavigationOnClickListener(){
            onBackPressed()

        }

    }

    fun getProductsList() {
        FireStoreClass().getAllProductList(this)
    }


    @SuppressLint("SetTextI18n")
    fun successCartItemList(cartList: ArrayList<CartItem>) {
//        hideProgressDialog()
        for (product in listOfAllProducts) {
            for (cartItem in cartList) {
                if (product.product_id == cartItem.product_id)
                    cartItem.stock_quantity = product.product_quantity
                if (cartItem.stock_quantity.toInt() == 0) {
                    cartItem.cart_quantity = product.product_quantity
                }
            }
        }
        cartItemList = cartList

        if (cartItemList.size > 0) {

            binding.rvCartItemList.visibility = View.VISIBLE
            binding.llCheckout.visibility = View.VISIBLE
            binding.textAllert.visibility = View.GONE


            binding.rvCartItemList.layoutManager = LinearLayoutManager(this)
            binding.rvCartItemList.setHasFixedSize(true)
            val adapter = CartListAdapter(this, cartItemList, true)
            binding.rvCartItemList.adapter = adapter



            var subTotal = 0.0
            var price = 0
            for (item in cartItemList) {
                val availableQuantity = item.stock_quantity.toInt()
                if (availableQuantity > 0) {
                    price = when {
                        item.price.contains(",") -> {
                            val index = item.price.indexOf(",")
                            val s1 = item.price.substring(0, index)
                            val s2 = item.price.substring(index+1 , item.price.length)

                            (s1+s2).toInt()
                        }
                        item.price.contains(".") -> {
                            val index = item.price.indexOf(".")
                            val s1 = item.price.substring(0, index)
                            val s2 = item.price.substring(index+1 , item.price.length)

                            (s1+s2).toInt()
                        }
                        else -> {
                            item.price.toInt()
                        }

                    }
                    val quantity = item.cart_quantity.toDouble()
                    subTotal += (price * quantity)
                }

            }
            binding.SubtotalPrice.text = "$ ${subTotal}"
            binding.ShippingChargePrice.text = "$ ${100}"

            if (subTotal > 0) {
                binding.llCheckout.visibility = View.VISIBLE
                val total = subTotal + 100
                binding.TotalAmountPrice.text = "$ ${total}"
            }
            else {
                binding.llCheckout.visibility = View.GONE
            }

        } else {
            binding.textAllert.visibility = View.VISIBLE
            binding.llCheckout.visibility = View.GONE
            binding.rvCartItemList.visibility = View.GONE

//            binding.rvCartItemList.layoutManager = LinearLayoutManager(this)
//            binding.rvCartItemList.setHasFixedSize(true)
//            val adapter = CartListAdapter(this, cartItemList, true)
//            binding.rvCartItemList.adapter = adapter
        }

    }

    fun successProductListFromFireStore(productList: ArrayList<Product>) {
//        hideProgressDialog()
        listOfAllProducts = productList
        FireStoreClass().getCartItemList(this)
    }


    fun failureCartItemList() {
        binding.rvCartItemList.visibility = View.GONE
    }


    fun dataUpdateSuccess(msg: String) {
        val toast = Toast.makeText(this, msg, Toast.LENGTH_LONG)
        toast.show()
    }

    fun dataUpdateFailure(msg: String) {
        var toast = Toast.makeText(this, msg, Toast.LENGTH_LONG)
        toast.show()
    }

    fun removeItemSuccess(){
        //TODO ssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssss
//        Snackify.error(
//            findViewById(android.R.id.content),
//            "Available stock is ${1}. You cannot add more than stock quality",
//            Snackify.LENGTH_LONG
//        ).show()
//        hideProgressDialog()
//        showErrorSnackBar("Удаление произошло успешно", false)
        getProductsList()
    }

    fun removeItemFailure(){
//        hideProgressDialog()
        showErrorSnackBar("Ошибка при удалении предмета из корзины", false)
        getProductsList()
    }

    fun cartUpdateSuccess(){
        hideProgressDialog()
        showErrorSnackBar("Обновлено", false)
        getProductsList()
    }


}