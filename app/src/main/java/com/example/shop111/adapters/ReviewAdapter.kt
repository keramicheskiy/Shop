package com.example.shop111.adapters

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.shop111.FireStoreClass
import com.example.shop111.ProductDetailsActivity
import com.example.shop111.R
import com.example.shop111.Review
import com.example.shop111.User
import com.example.shop111.databinding.ReviewItemBinding
import com.example.shop111.databinding.ShimmerItemDashboardLayoutBinding
import com.example.shop111.utils.GlideLoader
import java.util.Calendar

class ReviewAdapter (
    val context: Context,
    val mUser: User,
    val list: MutableList<Review>,
    val activity: ProductDetailsActivity,
    ) : RecyclerView.Adapter<ReviewAdapter.ViewHolder>() {
    class ViewHolder (val binding: ReviewItemBinding) : RecyclerView.ViewHolder(binding.root);

    lateinit var holder1: ViewHolder;



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.review_item, parent, false);
        return ViewHolder(ReviewItemBinding.bind(view));
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model = list[position];
        holder1 = holder;

        holder.binding.tvDate.text = model.date.toString()

//        FireStoreClass().getUserDetails(this@ReviewAdapter);
        if (model.reviewText.isNotEmpty()) {
            holder.binding.etReview.setText(model.reviewText);
        }

        holder.binding.tvDate.text = model.date.toString();
        holder.binding.stars.numStars = model.stars;
        holder.binding.tvName.text = mUser.firstName + mUser.lastName;
        if (mUser.image != "") {
            GlideLoader(context).loadUserProfile(mUser.image, holder.binding.imageView);
        }


        holder.binding.btnDelete.setOnClickListener {
            FireStoreClass().deleteReview(activity, model)
        }

        holder.binding.btnEdit.setOnClickListener {
            val x = AlertDialog.Builder(context)
                .setTitle("")
                .setMessage("")
                .setIcon(R.drawable.add_comment_vector_asset)
                .setPositiveButton("Добавить") { z,_ ->
                    val review = Review(
                        product_id = model.product_id,
                        user_id = FireStoreClass().getCurrentUserId(),
                        reviewText = holder.binding.etReview.text.toString().trim(),
                        stars= holder.binding.stars.numStars,
                        date= System.currentTimeMillis(),
                    )
                    FireStoreClass().addReview(activity, review)
                }.setNegativeButton("Отмена") { z,_ ->
                    z.dismiss()
                }
            val a : AlertDialog = x.create()
            a.setCancelable(false)
            a.show()
        }


    }

    override fun getItemCount(): Int {
        return list.size;
    }


}