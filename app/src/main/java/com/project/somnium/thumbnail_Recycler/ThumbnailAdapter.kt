package com.project.somnium.thumbnail_Recycler

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.project.somnium.R
import com.project.somnium.ReadDiary
import com.project.somnium.diaryDb.DiaryDataClass

class ThumbnailAdapter(val items: MutableList<DiaryDataClass>) :
    RecyclerView.Adapter<ThumbnailAdapter.Viewholder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ThumbnailAdapter.Viewholder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.thumbnail_items,parent,false)
        return Viewholder(view)

    }

    override fun onBindViewHolder(holder: ThumbnailAdapter.Viewholder, position: Int) {
        val gotoReadDiaryIntent = Intent(holder.itemView.context, ReadDiary::class.java)
        val item = items[position]
        val id = item.id

        holder.tv_title.text = "제목 : ${item.title}"
        holder.tv_date.text = "${item.date}"

        Glide.with(holder.itemView.context)
            .load("${item.imgurl}")
            .into(holder.imageView)

        holder.btn_detail.setOnClickListener {
            gotoReadDiaryIntent.putExtra("id", id)
            holder.itemView.context.startActivity(gotoReadDiaryIntent)
        }

    }

    override fun getItemCount(): Int = items.size

    inner class Viewholder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tv_title = itemView.findViewById<TextView>(R.id.tv_title)
        val tv_date = itemView.findViewById<TextView>(R.id.tv_date)
        val imageView = itemView.findViewById<ImageView>(R.id.imageView)
        val btn_detail = itemView.findViewById<Button>(R.id.btn_detail)

    }
}