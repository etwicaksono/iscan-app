package com.etwicaksono.iscan.ui.activities

import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.etwicaksono.iscan.utils.CustomView
import kotlinx.android.synthetic.main.activity_home.view.*

open class BaseActivity : AppCompatActivity() {
    protected fun hideActionBar() {
        supportActionBar?.hide()
    }

    protected fun showActionBar() {
        supportActionBar?.show()
    }

    protected fun showLoading(view: View? , state: Boolean?) {
        if (state == true) {
            view?.llProgressBar?.visibility = View.VISIBLE
        } else {
            view?.llProgressBar?.visibility = View.GONE
        }
    }

    protected fun showToast(
        message: String? ,
        isSuccess: Boolean? = true ,
        isShort: Boolean? = true
    ) {
        CustomView.customToast(this , message , isSuccess=isSuccess ,isShort =  isShort)
    }

}