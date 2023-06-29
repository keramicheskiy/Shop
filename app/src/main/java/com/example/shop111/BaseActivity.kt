package com.example.shop111

import android.app.Dialog
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar

open class BaseActivity : AppCompatActivity() {
    private var mProcessDialog: Dialog? = null



    fun showErrorSnackBar(message: String, errorMessage: Boolean) {
        val snackBar = Snackbar.make(findViewById(android.R.id.content),
        message,
        Snackbar.LENGTH_LONG)

        val snackBarView = snackBar.view

        if (errorMessage) {
            snackBarView.setBackgroundColor(
                ContextCompat.getColor(this@BaseActivity, R.color.colorSnackBarError))
        }else{
            snackBarView.setBackgroundColor(
                ContextCompat.getColor(this@BaseActivity, R.color.mygreen)
            )
        }
        snackBar.show()

    }

    fun showProgressDialog(text: String) {
        mProcessDialog = Dialog(this)
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