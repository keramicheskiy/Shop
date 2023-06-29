package com.example.shop111.fragments

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.example.shop111.R
import com.google.android.material.snackbar.Snackbar


open class BaseFragment : Fragment() {

    lateinit var mProcessDialog: Dialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.base_fragment, container, false)
    }


    fun showProgressDialog(context: Context, text: String) {
        mProcessDialog = Dialog(context)
        mProcessDialog!!.setContentView(R.layout.process_dialog)
        mProcessDialog!!.findViewById<TextView>(R.id.process_text).text = text
        mProcessDialog!!.setCancelable(false)
        mProcessDialog!!.setCanceledOnTouchOutside(false)
        mProcessDialog!!.show()

    }
    fun hideProgressDialog(){
        mProcessDialog!!.hide()
    }


}