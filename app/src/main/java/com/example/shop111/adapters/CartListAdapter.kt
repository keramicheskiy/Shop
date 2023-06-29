package com.example.shop111.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.shop111.*
import com.example.shop111.databinding.CartItemBinding
import com.example.shop111.CartListActivity

import com.example.shop111.utils.Constants
import com.example.shop111.utils.GlideLoader
import com.musfickjamil.snackify.Snackify

class CartListAdapter (
    private val context: Context,
    private val list: ArrayList<CartItem>,
    private val updateCartItems: Boolean
) : RecyclerView.Adapter<CartListAdapter.ViewHolder>() {
    class ViewHolder (val binding : CartItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType : Int) : ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.cart_item, parent, false)
        return ViewHolder(CartItemBinding.bind(view))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model = list[position]


        holder.binding.tvItemName.text = model.title
        holder.binding.tvItemPrice.text = "$${model.price}"
        holder.binding.itemQuantity.setText(model.cart_quantity)
        GlideLoader(context).loadUserProfile(model.image, holder.binding.image)

        if (model.stock_quantity == "0") {
            holder.binding.btnAdd.visibility = View.GONE
            holder.binding.btnRemove.visibility = View.GONE

            if(updateCartItems) {
                holder.binding.ibDeleteProduct.visibility = View.VISIBLE
            } else {
                holder.binding.ibDeleteProduct.visibility = View.GONE
            }

            holder.binding.itemQuantity.setText("OUT OF STOCK!")

            holder.binding.itemQuantity.setTextColor(
                ContextCompat.getColor(context, R.color.colorSnackBarError)

            )


        } else {
            if (updateCartItems){
                holder.binding.btnAdd.visibility = View.VISIBLE
                holder.binding.btnRemove.visibility = View.VISIBLE
                holder.binding.ibDeleteProduct.visibility = View.VISIBLE
            } else {
                holder.binding.btnAdd.visibility = View.GONE
                holder.binding.btnRemove.visibility = View.GONE
                holder.binding.ibDeleteProduct.visibility = View.GONE
            }

            holder.binding.itemQuantity.setTextColor(
                ContextCompat.getColor(context, R.color.black))
        }

//        323452
        holder.binding.ibDeleteProduct.setOnClickListener {
            if (context is CartListActivity) {
                BaseActivity().showProgressDialog("Please wait...")
                FireStoreClass().removeItemFromCart(context, model.id)
            }
        }


        holder.binding.btnRemove.setOnClickListener {
            if (model.cart_quantity == "1") {
//                BaseActivity().showProgressDialog("Please wait...")
                FireStoreClass().removeItemFromCart(context, model.id)
            } else {
                val cartQuantity: Int = model.cart_quantity.toInt()

                val hashMap = HashMap<String, Any>()

                hashMap[Constants.CART_QUANTITY] = (cartQuantity - 1).toString()
                if (context is CartListActivity) {
                    context.showProgressDialog("Please wait...")
                }

                FireStoreClass().updateMyCart(context, model.id, hashMap)
//                FireStoreClass().removeItemFromCart(context, model.id, hashMap)
            }
        }


        holder.binding.btnAdd.setOnClickListener {
            val cartQuantity: Int = model.cart_quantity.toInt()

            if(cartQuantity < model.stock_quantity.toInt()) {
                val hashMap = HashMap<String, Any>()

                hashMap[Constants.CART_QUANTITY] = (cartQuantity + 1).toString()
                if (context is CartListActivity) {
                    context.showProgressDialog("Please wait...")
                }
                FireStoreClass().updateMyCart(context, model.id, hashMap)

            } else {
                if (context is CartListActivity) {
//                    Snackify.error(
//                        context.findViewById(android.R.id.content),
//                        "Available stock is ${model.stock_quantity.toInt()}. You cannot add more than stock quality",
//                        Snackify.LENGTH_LONG
//                    ).show()
                }
            }

        }



//        holder.itemView.setOnClickListener {
//            val intent = Intent(context, ProductDetailsActivity::class.java)
//
//            intent.putExtra(Constants.EXTRA_PRODUCT_ID, model.id)
//            intent.putExtra(Constants.EXTRA_PRODUCT_OWNER_ID, model.user_id)
//            context.startActivity(intent)
//        }

    }

    override fun getItemCount(): Int {
        return list.size
    }

}