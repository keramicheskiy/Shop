package com.example.shop111.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.navArgs
//import androidx.navigation.fragment.navArgs
import com.example.shop111.R
import com.example.shop111.databinding.DashboardFragmentBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [SoldProductsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SoldProductsFragment : Fragment() {
    private var _binding: DashboardFragmentBinding? = null
    private val binding get() = _binding!!
    // получение данных, все в navigation/mobile_navigation
//    private val args: SoldProductsFragmentArgs by navArgs()

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


        // получение данных, все в navigation/mobile_navigation
//        if (args.a != ""){
//            Toast.makeText(activity, "${args.a}", Toast.LENGTH_LONG)
//        }

//        val action = SoldProductsFragmentDirections.actionNavigationProductsToNavigationSoldProducts("usdk", )

        return root
    }

}