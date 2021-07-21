package com.etwicaksono.iscan.ui.activities.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.etwicaksono.iscan.R
import com.etwicaksono.iscan.data.TokoEntity
import kotlinx.android.synthetic.main.item_row_toko.view.*

class HomeAdapter(private val onClick: (TokoEntity) -> Unit) :
    RecyclerView.Adapter<HomeAdapter.ViewHolder>() {
    val data = ArrayList<TokoEntity>()

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(toko: TokoEntity) {
            itemView.tv_nama_toko.text = toko.nama
            itemView.tv_alm_toko.text = toko.alamat
            Glide.with(itemView)
                .load(toko.foto)
                .placeholder(R.drawable.store_black_1)
                .into(itemView.img_toko)

            itemView.setOnClickListener {
                onClick(toko)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup , viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_row_toko , parent , false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder , position: Int) = holder.bind(data[position])

    override fun getItemCount(): Int = data.size

    fun setToko(toko: List<TokoEntity>) {
        data.clear()
        data.addAll(toko)
        notifyDataSetChanged()
    }

}
