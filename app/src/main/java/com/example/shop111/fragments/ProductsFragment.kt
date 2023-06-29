package com.example.shop111.fragments

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.shop111.*
import com.example.shop111.adapters.Adapter
//import androidx.navigation.fragment.findNavController
import com.example.shop111.databinding.DashboardFragmentBinding
import com.example.shop111.databinding.ProductsFragmentBinding


class ProductsFragment : BaseFragment() {
    private var _binding: ProductsFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = ProductsFragmentBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.add_products, menu)
        super.onCreateOptionsMenu(menu, inflater)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.action_add ->{
                startActivity(Intent(activity, AddProduct::class.java))
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onResume() {
        getProductListFromFirestore()
        super.onResume()
    }


    fun getProductListFromFirestore() {
        showProgressDialog(requireActivity(), getString(R.string.please_wait))
        binding.shimmerViewContainer.startShimmerAnimation()
        FireStoreClass().getProductList(this)
    }


    fun successProductListFromFirestore(productList: ArrayList<Product>) {
        hideProgressDialog()
        binding.shimmerViewContainer.visibility = View.GONE
        binding.shimmerViewContainer.stopShimmerAnimation()
        if (productList.size > 0) {
            binding.rvMyProductsItems.visibility = View.VISIBLE
            binding.tvNoProductsFound.visibility = View.GONE
            binding.rvMyProductsItems.layoutManager = LinearLayoutManager(activity)
            binding.rvMyProductsItems.setHasFixedSize(true)
            val adapter = Adapter(requireContext(), productList, this)
            binding.rvMyProductsItems.adapter = adapter

        } else {
            binding.rvMyProductsItems.visibility = View.GONE
            binding.tvNoProductsFound.visibility = View.VISIBLE
        }

    }

    fun deleteProduct(productId : String) {
//        TODO("not yet что-то там")
    }






}