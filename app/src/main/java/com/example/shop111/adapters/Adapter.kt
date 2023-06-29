package com.example.shop111.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.ViewParent
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.shop111.BaseActivity
import com.example.shop111.Product
import com.example.shop111.ProductDetailsActivity
import com.example.shop111.R
import com.example.shop111.databinding.ItemListLayoutBinding
import com.example.shop111.fragments.BaseFragment
import com.example.shop111.fragments.ProductsFragment
import com.example.shop111.utils.Constants
import com.example.shop111.utils.GlideLoader

open class Adapter (private val context: Context,
                    private val list: ArrayList<Product>,
                    private val fragment: ProductsFragment,
              ) : RecyclerView.Adapter<Adapter.ViewHolder>() {
    class ViewHolder (val binding : ItemListLayoutBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType : Int) : ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_list_layout, parent, false)
        return ViewHolder(ItemListLayoutBinding.bind(view))
    }



    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model = list[position]

        GlideLoader(context).loadUserProfile(model.prduct_image, holder.binding.image)

        holder.binding.tvItemName.text = model.product_title
        holder.binding.tvItemPrice.text = "$${model.product_price}"

        holder.binding.ibDeleteProduct.setOnClickListener {
            fragment.deleteProduct(model.product_id)
        }

        holder.itemView.setOnClickListener {
            val intent = Intent(context, ProductDetailsActivity::class.java)
            intent.putExtra(Constants.EXTRA_PRODUCT_ID, model.product_id)
            intent.putExtra(Constants.EXTRA_PRODUCT_OWNER_ID, model.user_id)
//            BaseFragment().showProgressDialog(context, Constants.PLEASE_WAIT)
            context.startActivity(intent)
        }

    }

    override fun getItemCount(): Int {
        return list.size
    }

}