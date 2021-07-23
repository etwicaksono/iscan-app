package com.etwicaksono.iscan.ui.activities.detail

import android.content.Context
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.viewpager.widget.PagerAdapter
import com.bumptech.glide.Glide
import com.etwicaksono.iscan.R

class SliderAdapter(context: Context , private val foto: List<String?>?) : PagerAdapter() {

    //    init
    private val inflater: LayoutInflater = LayoutInflater.from(context)

    override fun destroyItem(container: ViewGroup , position: Int , `object`: Any) {
        container.removeView(`object` as View)
    }

    override fun instantiateItem(view: ViewGroup , position: Int): Any {
        val imageLayout = inflater.inflate(R.layout.image_slider , view , false)!!
        val imageView = imageLayout.findViewById<ImageView>(R.id.iv_header)
        Glide.with(view)
            .load(foto?.get(position))
            .placeholder(R.drawable.ic_banner_placeholder)
            .error(R.drawable.ic_banner_placeholder)
            .into(imageView)

        view.addView(imageLayout , 0)
        return imageLayout
    }

    override fun restoreState(state: Parcelable? , loader: ClassLoader?) {}

    override fun saveState(): Parcelable?=null

    override fun isViewFromObject(view: View , `object`: Any) = view == `object`

    override fun getCount(): Int = foto?.size ?: 0


}
