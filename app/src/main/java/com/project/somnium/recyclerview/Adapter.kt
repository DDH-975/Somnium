package com.project.somnium.recyclerview

import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.project.somnium.R
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStream


class Adapter(val itemList: ArrayList<RecyclerDataModel>) :
    RecyclerView.Adapter<Adapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Adapter.ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.recycler_items, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: Adapter.ViewHolder, position: Int) {
        val item = itemList[position]

        holder.tv_coment.text = item.coment

        //이미지 생성
        Glide.with(holder.itemView.context)
            .load("${item.imageUrl}")   // 이미지 url
            .placeholder(R.drawable.loadingimg2) // 로딩중 사용할 이미지
            .into(holder.img_dreamImg)


        holder.btn_store.setOnClickListener {
            Glide.with(holder.itemView.context)
                .asBitmap()
                .load("${item.imageUrl}")
                .into(object : CustomTarget<Bitmap>() {
                    override fun onResourceReady(
                        resource: Bitmap,
                        transition: Transition<in Bitmap>?
                    ) {
                        saveImageToGallery(holder.itemView.context, resource)
                    }

                    override fun onLoadCleared(placeholder: Drawable?) {
                    }
                })
        }
    }

    override fun getItemCount(): Int = itemList.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tv_coment = itemView.findViewById<TextView>(R.id.tv_coment)
        val img_dreamImg = itemView.findViewById<ImageView>(R.id.img_dreamImg)
        val btn_store = itemView.findViewById<Button>(R.id.btn_store)
    }


    //이미지 갤러리 저장 메서드
    fun saveImageToGallery(context: Context, bitmap: Bitmap) {
        val filename = "image_${System.currentTimeMillis()}.png"
        var fos: OutputStream? = null

        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                val resolver = context.contentResolver
                val contentValues = ContentValues().apply {
                    put(MediaStore.MediaColumns.DISPLAY_NAME, filename)
                    put(MediaStore.MediaColumns.MIME_TYPE, "image/png")
                    put(
                        MediaStore.MediaColumns.RELATIVE_PATH,
                        Environment.DIRECTORY_PICTURES + "/Somnium"
                    )
                }

                val imageUri: Uri? =
                    resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
                fos = imageUri?.let { resolver.openOutputStream(it) }
            } else {
                val imagesDir =
                    Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
                        .toString() + "/MyApp"
                val file = File(imagesDir)
                if (!file.exists()) file.mkdirs()
                val image = File(file, filename)
                fos = FileOutputStream(image)

                // 갤러리 반영
                val intent = Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE)
                intent.data = Uri.fromFile(image)
                context.sendBroadcast(intent)
            }

            fos?.use {
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, it)
            }

            Toast.makeText(context, "이미지가 저장되었습니다!", Toast.LENGTH_SHORT).show()

        } catch (e: IOException) {
            e.printStackTrace()
            Toast.makeText(context, "저장 실패: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }

}