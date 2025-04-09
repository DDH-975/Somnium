package com.project.somnium.recyclerview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.project.somnium.R

class Adapter (val itemList: ArrayList<RecyclerDataModel>) :
    RecyclerView.Adapter<Adapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Adapter.ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.recycler_items, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: Adapter.ViewHolder, position: Int) {
        val item = itemList[position]
        holder.tv_coment.text = item.coment
        holder.img_dreamImg.setImageResource(item.imageUrl)
    }

    override fun getItemCount(): Int =  itemList.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tv_coment = itemView.findViewById<TextView>(R.id.tv_coment)
        val img_dreamImg = itemView.findViewById<ImageView>(R.id.img_dreamImg)
    }
}