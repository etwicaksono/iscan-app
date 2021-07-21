package com.etwicaksono.iscan.utils

import android.content.Context
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.etwicaksono.iscan.R

object CustomView {

    fun customToast(
        context: Context? ,
        message: String? ,
        isShort: Boolean? = true ,
        isSuccess: Boolean? = false
    ) {
        val layoutInflater =
            context?.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = layoutInflater.inflate(R.layout.custom_toast , null)
        val textMessage = view.findViewById(R.id.tv_toast) as TextView
        val icon = view.findViewById(R.id.ic_toast) as ImageView

        isSuccess?.let {
            if (!it) {
                icon.setImageResource(R.drawable.ic_warning)
            }
        }

        isShort?.let {
            val toast = Toast(context).apply {
                setGravity(Gravity.CENTER , 0 , 0)
                setView(view)
            }
            if (it) {
                toast.duration = Toast.LENGTH_SHORT
            } else {
                toast.duration = Toast.LENGTH_LONG
            }
            toast.show()
        }
    }

}