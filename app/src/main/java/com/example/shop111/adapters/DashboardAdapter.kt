package com.example.shop111.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View.OnClickListener
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.shop111.BaseActivity
import com.example.shop111.Product
import com.example.shop111.R
import com.example.shop111.databinding.ItemListLayoutBinding
import com.example.shop111.databinding.ShimmerItemDashboardLayoutBinding
import com.example.shop111.fragments.BaseFragment
import com.example.shop111.fragments.DashboardFragment
import com.example.shop111.utils.Constants
import com.example.shop111.utils.GlideLoader

class DashboardAdapter(
    private val context: Context,
    private val list: ArrayList<Product>,
    private val fragment: DashboardFragment
    ): RecyclerView.Adapter<DashboardAdapter.ViewHolder>() {
    class ViewHolder(val binding: ShimmerItemDashboardLayoutBinding) : RecyclerView.ViewHolder(binding.root)
    private var onClickListener: OnClickListener? = null



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.shimmer_item_dashboard_layout, parent, false)
        return ViewHolder(ShimmerItemDashboardLayoutBinding.bind(view))
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model = list[position]

        GlideLoader(context).loadUserProfile(model.prduct_image, holder.binding.ivDashboardItemImage)
        holder.binding.tvDashboardItemName.text = model.product_title
        holder.binding.tvDashboardItemPrice.text = "$. ${model.product_price}"

        holder.itemView.setOnClickListener {
            if (onClickListener != null) {
//                BaseFragment().showProgressDialog(context, Constants.PLEASE_WAIT)
                onClickListener!!.onClick(position, model)
            }
        }

    }

    fun setOnClickListener(onClickListener: OnClickListener) {
        this.onClickListener = onClickListener

    }

    override fun getItemCount(): Int {
        return list.size
    }

    interface OnClickListener {
        fun onClick (position: Int, product: Product)
    }



}