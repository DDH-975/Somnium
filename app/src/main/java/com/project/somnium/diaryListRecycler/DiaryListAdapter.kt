package com.project.somnium.diaryListRecycler

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.project.somnium.R
import com.project.somnium.ReadDiary
import com.project.somnium.diaryDb.DiaryDataClass
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DiaryListAdapter(
    val items: MutableList<DiaryDataClass>,
    private val onDeleteClicked: suspend (DiaryDataClass) -> Unit
) : RecyclerView.Adapter<DiaryListAdapter.ViewHolder>() {
    private var checkedClick = true

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DiaryListAdapter.ViewHolder {
        val view =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.diary_list_recyler_itmes, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        val context = holder.itemView.context
        val intent = Intent(context, ReadDiary::class.java)

        holder.tv_title.text = "제목 : ${item.title}"
        holder.tv_date.text = "작성일자 : ${item.date}"

        holder.btn_delete.setOnClickListener {
            if (checkedClick) {
                Toast.makeText(context, "삭제하려면 한번 더 터치해주세요.", Toast.LENGTH_SHORT)
                    .show()
                checkedClick = false
            } else {
                CoroutineScope(Dispatchers.Main).launch {
                    onDeleteClicked(item)
                    removeItme(position)
                }
            }
        }


        holder.btn_read.setOnClickListener {
            intent.putExtra("title", item.title)
            intent.putExtra("content", item.content)
            intent.putExtra("imgUrl", item.imgurl)
            intent.putExtra("date", item.date)
            intent.putExtra("id", item.id)
            context.startActivity(intent)
        }


    }

    override fun getItemCount(): Int = items.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tv_title = itemView.findViewById<TextView>(R.id.tv_title)
        val tv_date = itemView.findViewById<TextView>(R.id.tv_date)
        val btn_delete = itemView.findViewById<Button>(R.id.btn_delete)
        val btn_read = itemView.findViewById<Button>(R.id.btn_read)
    }

    fun removeItme(position: Int) {
        items.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeRemoved(position, items.size)
        checkedClick = true
    }


}