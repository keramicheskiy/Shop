package com.example.shop111.fragments

import android.content.Intent
import android.graphics.drawable.Drawable.ConstantState
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.example.shop111.*
import com.example.shop111.adapters.Adapter
import com.example.shop111.adapters.DashboardAdapter
import com.example.shop111.databinding.DashboardFragmentBinding
import com.example.shop111.utils.Constants


class DashboardFragment : Fragment() {
    private var _binding: DashboardFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = DashboardFragmentBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onResume() {
        super.onResume()

        FireStoreClass().getProductListFromDashboard(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.tvNoProductsFound.text = "ERRGHJK"

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.item, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_setting -> {
                startActivity(Intent(activity, SettingsActivity::class.java))
                return true
            }
            R.id.action_cart -> {
                startActivity(Intent(activity, CartListActivity::class.java))
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }


    fun successDashBoardItemList(list: ArrayList<Product>) {

        if (list.size > 0) {

            binding.rvMyProductsItems.visibility = View.VISIBLE
            binding.tvNoProductsFound.visibility = View.GONE
            binding.shimmerViewContainer.visibility = View.GONE

            val adapter = DashboardAdapter(requireContext(), list, this)
            binding.rvMyProductsItems.layoutManager = GridLayoutManager(activity, 2)
            binding.rvMyProductsItems.setHasFixedSize(true)
            binding.rvMyProductsItems.adapter = adapter


            adapter.setOnClickListener(object : DashboardAdapter.OnClickListener{
                override fun onClick(position: Int, product: Product) {
                    val intent = Intent(requireActivity(), ProductDetailsActivity::class.java)
                    intent.putExtra(Constants.EXTRA_PRODUCT_ID, product.product_id)
                    intent.putExtra(Constants.EXTRA_PRODUCT_OWNER_ID, product.user_id)
                    startActivity(intent)
                }
            })
        } else {
            binding.rvMyProductsItems.visibility = View.GONE
            binding.shimmerViewContainer.visibility = View.GONE
            binding.tvNoProductsFound.visibility = View.VISIBLE
        }
    }


}