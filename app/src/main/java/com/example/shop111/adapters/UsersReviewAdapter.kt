package com.example.shop111.adapters

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.shop111.FireStoreClass
import com.example.shop111.R
import com.example.shop111.Review
import com.example.shop111.User
import com.example.shop111.databinding.ReviewItemBinding
import com.example.shop111.utils.GlideLoader
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale


class UsersReviewAdapter (
    val context: Context,
    val activity: Activity,
    val user: User,
    val product_id: String
    ) : RecyclerView.Adapter<UsersReviewAdapter.ViewHolder>() {
    class ViewHolder ( val binding: ReviewItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.review_item, parent, false)
        return ViewHolder(ReviewItemBinding.bind(view))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model = user
        val dateFormat = SimpleDateFormat("dd.MM.yyyy ", Locale.getDefault())
        val currentTimeMills = System.currentTimeMillis()
        val time = dateFormat.format(currentTimeMills)

        holder.binding.tvName.text = model.firstName + model.lastName
        holder.binding.tvDate.text = time.toString()
        GlideLoader(context).loadUserProfile(user.image, holder.binding.imageView)

        holder.binding.btnEdit.setOnClickListener {
            val x = AlertDialog.Builder(context)
                .setTitle("")
                .setMessage("")
                .setIcon(R.drawable.add_comment_vector_asset)
                .setPositiveButton("Добавить") { z,_ ->
                    val review = Review(
                        product_id = product_id,
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

        holder.binding.btnDelete.setOnClickListener {
            FireStoreClass().deleteReview(activity, product_id)
        }


    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }
}







